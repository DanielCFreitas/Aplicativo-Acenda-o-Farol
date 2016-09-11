package com.example.aluno.acenda_o_farol2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class ActivityPrincipal extends AppCompatActivity {
    private Button btnAtivar;
    private Localizacao localizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_activity_principal);

        this.btnAtivar = (Button)findViewById(R.id.btnAtivar);

        this.localizacao = new Localizacao(this);

        if (localizacao.verificaGPS()){
            btnAtivar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onRestart()
    {
        if (localizacao.verificaGPS()){
            btnAtivar.setVisibility(View.INVISIBLE);
        }
        super.onRestart();
    }

    /**
     * Verifica se existe conexão com a Internet
     * @return retorna verdadeiro se existir conexao com a internet
     */
    private boolean verificaInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Método do botão CONTINUAR, faz a verificação se o GPS está ativo
     * caso esteja ativo o método chama outra activity
     */
    public void eventoBtnContinuar(View view){
        if (!localizacao.verificaGPS()){
            Toast.makeText(getApplicationContext(),"Ative o GPS do dispositivo para saber se estamos em uma rodovia",Toast.LENGTH_LONG).show();
            btnAtivar.setVisibility(View.VISIBLE);
            return;
        } else if (!verificaInternet()){
            Toast.makeText(getApplicationContext(),"Ative a internet do dispositivo para melhor precisão do GPS",Toast.LENGTH_LONG).show();
            return;
        }
        Intent myIntent = new Intent(ActivityPrincipal.this, ActivityVerificaRodovia.class);
        ActivityPrincipal.this.startActivity(myIntent);
    }

    /**
     * Método do botão ATIVAR, faz a verificação se o GPS está ativo
     * caso esteja ativo desativa o botao
     */
    public void eventoBtnAtivar(View view){
        if (!localizacao.verificaGPS()){
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } else {
            btnAtivar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"O GPS está ativo clique em Continuar", Toast.LENGTH_LONG).show();
        }
    }
}
