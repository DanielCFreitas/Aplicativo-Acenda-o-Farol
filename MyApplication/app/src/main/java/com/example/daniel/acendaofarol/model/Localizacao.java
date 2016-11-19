package com.example.daniel.acendaofarol.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Classe que representa a localizacao atual do motorista
 */
public class Localizacao{

    private final Context context;
    private LocationManager locationManager;
    private LatitudeLongitude latitudeLongitude;
    private Endereco endereco;

    // Getters e Setters
    public Endereco getEndereco(){
        return this.endereco;
    }

    /**
     * Método Construtor
     * @param context Contexto que sera utilizado esta classe
     */
    public Localizacao(Context context){
        this.context = context;
        this.latitudeLongitude = new LatitudeLongitude();
        this.endereco = new Endereco();
    }

    /**
     * Método que verifica se o GPS está ativo
     */
    public boolean verificaGPS(){
        this.locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        return this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Verifica se o motorista esta em outra cidade
     * @param cidadeAtual cidade atual que o motorista se encontra
     * @return retorna verdadeiro se esta na mesma cidade e false se estiver em outra
     */
    public boolean verificaSeEstaEmOutraCidade(String cidadeAtual){
        return cidadeAtual.equals(this.endereco.getCidade());
    }

    /**
     * Método que verifica se o motorista esta em uma rodovia
     * @param rodovias Lista com as rodovias
     * @param enderecoAtual Rua atual do motorista
     * @return Retorna true se o motorista estiver em uma rodovia caso contrario retorna false
     */
    public boolean verificaSeEstaEmUmaRodovia(ArrayList<String> rodovias, String enderecoAtual){
        for (String rodovia : rodovias){
            if (rodovia.equals(enderecoAtual)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método que atualiza a rua e a cidade atual do motorista
     * @param location envia um location que contem as informacoes de latitude e longitude
     */
    public void atualizaEnderecoAtual(Location location, TextView txvEndereco){
        Geocoder geocoder;
        this.latitudeLongitude.setLongitude(location.getLongitude());
        this.latitudeLongitude.setLatitude(location.getLatitude());

        geocoder = new Geocoder(this.context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(this.latitudeLongitude.getLatitude(),
                    this.latitudeLongitude.getLongitude(),
                    1); // Latitude, Longitude, Quantidade de ruas que devem ser retornadas
            if (addresses.size()!=0) {
                this.endereco.setRua(addresses.get(0).getAddressLine(0));
                this.endereco.setCidade(addresses.get(0).getAddressLine(1));

                this.endereco.setRua(this.endereco.getRua().substring(0, this.endereco.getRua().indexOf(",")).trim());
                this.endereco.setCidade(this.endereco.getCidade().substring(0,this.endereco.getCidade().indexOf("-")).trim());

                StringBuilder sb = new StringBuilder();
                sb.append(this.endereco.getRua());
                sb.append("\n");
                sb.append(this.endereco.getCidade());

                txvEndereco.setText(sb.toString());
            } else {
                Toast.makeText(this.context, "Localização Indisponível", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex){
            Toast.makeText(this.context, "Problema de conexão com a Internet",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean comparaEnderecos(String enderecoAntigo, String enderecoAtual){
        return enderecoAntigo.equals(enderecoAtual);
    }

}