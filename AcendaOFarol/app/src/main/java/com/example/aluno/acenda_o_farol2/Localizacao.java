package com.example.aluno.acenda_o_farol2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Classe que representa a localizacao atual do motorista
 */
public class Localizacao{

    private final Context context;
    private LocationManager locationManager;
    private LatitudeLongitude latitudeLongitude;
    private Endereco ruaAtual;

    /**
     * Método Construtor
     * @param context Contexto que sera utilizado esta classe
     */
    public Localizacao(Context context){
        this.context = context;
        this.latitudeLongitude = new LatitudeLongitude();
        this.ruaAtual = new Endereco();
    }

    /**
     * Método que verifica se o GPS está ativo
     */
    public boolean verificaGPS(){
        this.locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        return this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Método que atraves da latitude e longitude verifica o endereco atual e se é necessário acender o Farol
     */
    public boolean verificaAcenderFarol(LinkedList<Endereco> rodovias, Location location){
        Geocoder geocoder;
        String ruaDoGPS = "";
        this.latitudeLongitude.setLongitude(location.getLongitude());
        this.latitudeLongitude.setLatitude(location.getLatitude());

        geocoder = new Geocoder(this.context, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(this.latitudeLongitude.getLatitude(),
                                                 this.latitudeLongitude.getLongitude(),
                                                  1); // Latitude, Longitude, Quantidade de ruas que devem ser retornadas
            ruaDoGPS = addresses.get(0).getAddressLine(0);
            ruaDoGPS = ruaDoGPS.substring(0,ruaDoGPS.indexOf(","));
        } catch (IOException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this.context);
            dlg.setMessage("Problemas para conseguir o nome da rua" + ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
        this.ruaAtual.setEndereco(ruaDoGPS);

        if (this.ruaAtual.verificaRodovia(rodovias)){
            return true;
        } else{
            return false;
        }
    }

}
