package com.example.aluno.acenda_o_farol2;

import android.content.Context;
import android.content.Intent;
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
     * Método do botão CONTINUAR, faz a verificação se o GPS está ativo
     * caso esteja ativo o método chama outra activity
     */
    public void eventoBtnContinuar(View view){
        if (localizacao.verificaGPS()){
            Intent myIntent = new Intent(ActivityPrincipal.this, ActivityVerificaRodovia.class);
            ActivityPrincipal.this.startActivity(myIntent);

        } else {
            btnAtivar.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Ative o GPS para usar o aplicativo em seguida clique em CONTINUAR",Toast.LENGTH_LONG).show();
        }
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
