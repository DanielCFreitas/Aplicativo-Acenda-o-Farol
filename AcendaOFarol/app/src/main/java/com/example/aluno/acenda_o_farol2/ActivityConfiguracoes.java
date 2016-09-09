package com.example.aluno.acenda_o_farol2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityConfiguracoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_configuracoes);
    }

    /**
     * Método que volta para a Activity anterior
     */
    public void btnVoltar(View view){
        Intent myIntent = new Intent(ActivityConfiguracoes.this, ActivityVerificaRodovia.class);
        ActivityConfiguracoes.this.startActivity(myIntent);
    }
}
