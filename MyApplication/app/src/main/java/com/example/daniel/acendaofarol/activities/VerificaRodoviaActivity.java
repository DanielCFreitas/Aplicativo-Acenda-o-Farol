package com.example.daniel.acendaofarol.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daniel.acendaofarol.R;
import com.example.daniel.acendaofarol.database.BancoController;
import com.example.daniel.acendaofarol.model.Alerta;
import com.example.daniel.acendaofarol.model.Farol;
import com.example.daniel.acendaofarol.model.Localizacao;
import com.example.daniel.acendaofarol.model.Rodovias;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class VerificaRodoviaActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private TextView txvAcendeFarol;
    private TextView txvEndereco;
    private ImageView imagemFarol;

    private Localizacao localizacao = new Localizacao(this);
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private Rodovias rodovias = new Rodovias();
    private Farol farol = new Farol();
    private Alerta alerta;

    private BancoController bancoController;

    public final static int MILISEGUNDOS_POR_SEGUNDOS = 1000;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton fabConfiguracoes;
    FloatingActionButton fabContato;
    FloatingActionButton fabInformacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifica_rodovia);

        txvAcendeFarol = (TextView) findViewById(R.id.statusFarol);
        txvEndereco = (TextView) findViewById(R.id.txvEndereco);
        imagemFarol = (ImageView) findViewById(R.id.imagemFarol);

        // seta informações sobre a configuracoes de alerta do aplicativo (vibra e som)
        bancoController = new BancoController(getApplicationContext());
        alerta = new Alerta(getApplicationContext());
        alerta = bancoController.carregaDados();

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

        // Instancias dos botões de menu
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        fabConfiguracoes = (FloatingActionButton) findViewById(R.id.fabConfiguracoes);
        fabContato = (FloatingActionButton) findViewById(R.id.fabContato);
        fabInformacoes = (FloatingActionButton) findViewById(R.id.fabInformacoes);


        //Botões do menu superior
        fabConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(VerificaRodoviaActivity.this, ConfiguracoesActivity.class);
                VerificaRodoviaActivity.this.startActivity(myIntent);
            }
        });

        fabInformacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(VerificaRodoviaActivity.this, InfoLeiActivity.class);
                VerificaRodoviaActivity.this.startActivity(myIntent);
            }
        });

        fabContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(VerificaRodoviaActivity.this, ContatoActivity.class);
                VerificaRodoviaActivity.this.startActivity(myIntent);
            }
        });
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

        alerta = bancoController.carregaDados();
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
                this.localizacao.atualizaEnderecoAtual(location, txvEndereco);
                this.farol.setAceso(false);
            } else {
                String enderecoAnterior = this.localizacao.getEndereco().getRua();
            }

            String cidadeAnterior = this.localizacao.getEndereco().getCidade();
            this.localizacao.atualizaEnderecoAtual(location, txvEndereco);

            if (this.localizacao.verificaSeEstaEmOutraCidade(cidadeAnterior)){
                this.rodovias.atualizarListaDeRodovias(this.localizacao.getEndereco().getCidade());
            }

            boolean estaEmUmaRodovia = localizacao.verificaSeEstaEmUmaRodovia(this.rodovias.getRodoviasDaCidadeAtual(), this.localizacao.getEndereco().getRua());

            if ( estaEmUmaRodovia && !farol.isAceso()) {
                farol.setAceso(true);
                this.txvAcendeFarol.setText("ACENDA O FAROL");
                this.imagemFarol.setBackground(getResources().getDrawable(R.drawable.aceso));
                this.alerta.alertarComVibra();
                this.alerta.alertarComSom();
            } else if ( !estaEmUmaRodovia && farol.isAceso()) {
                farol.setAceso(false);
                this.txvAcendeFarol.setText("APAGUE O FAROL");
                this.imagemFarol.setBackground(getResources().getDrawable(R.drawable.apagado));
                this.alerta.alertarComVibra();
                this.alerta.alertarComSom();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Problema de conexão com o GPS", Toast.LENGTH_SHORT).show();
        }
    }


}
