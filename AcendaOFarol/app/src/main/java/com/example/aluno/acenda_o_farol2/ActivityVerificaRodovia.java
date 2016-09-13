package com.example.aluno.acenda_o_farol2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class ActivityVerificaRodovia extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private TextView txvAcendeFarol;
    private ImageView imagemFarol;

    private Localizacao localizacao = new Localizacao(this);
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private Rodovias rodovias = new Rodovias();

    public final static int MILISEGUNDOS_POR_SEGUNDOS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_verifica_rodovia);

        txvAcendeFarol = (TextView) findViewById(R.id.statusFarol);
        imagemFarol = (ImageView) findViewById(R.id.imagemFarol);

        //Conectando com o Google API Services
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        //Inicializa a Requisicao de Location com a precisão e a frequencia a qual queremos atualizar a Location no GPS
        locationRequest = new LocationRequest();

        locationRequest.setInterval(5 * MILISEGUNDOS_POR_SEGUNDOS);
        locationRequest.setFastestInterval(5 * MILISEGUNDOS_POR_SEGUNDOS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()){
            configurarUsoDoLocationServices();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    /**
     * Método que atualiza a localizacao atual do motorista
     */
    private void configurarUsoDoLocationServices() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    /**
     * Método que vai para o botão de configurações do aplicativo
     */
    public void btnConfiguracoes(View view){
        Intent myIntent = new Intent(ActivityVerificaRodovia.this, ActivityConfiguracoes.class);
        ActivityVerificaRodovia.this.startActivity(myIntent);
    }

    // MÉTODOS SOBRESCRITOS DA CLASSE GoogleApiClient
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        configurarUsoDoLocationServices();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // MÉTODOS SOBRESCRITOS DA CLASSE LocationListener
    @Override
    public void onLocationChanged(Location location) {
        if (localizacao.verificaGPS()) {
            if (this.localizacao.getEndereco().getRua() == null){
                this.localizacao.atualizaEnderecoAtual(location);
            }

            String cidadeAnterior = this.localizacao.getEndereco().getCidade();
            this.localizacao.atualizaEnderecoAtual(location);

            if (this.localizacao.verificaSeEstaEmOutraCidade(cidadeAnterior)){
                this.rodovias.atualizarListaDeRodovias(this.localizacao.getEndereco().getCidade());
            }

            if (localizacao.verificaSeEstaEmUmaRodovia(this.rodovias.getRodoviasDaCidadeAtual(), this.localizacao.getEndereco().getRua())) {
                this.txvAcendeFarol.setText("ACENDA O FAROL");
                this.imagemFarol.setBackground(getResources().getDrawable(R.drawable.aceso));
            } else {
                this.txvAcendeFarol.setText("APAGUE O FAROL");
                this.imagemFarol.setBackground(getResources().getDrawable(R.drawable.apagado));
            }

        } else {
            Toast.makeText(getApplicationContext(), "Problema de conexão com o GPS", Toast.LENGTH_SHORT).show();
        }
    }
}
