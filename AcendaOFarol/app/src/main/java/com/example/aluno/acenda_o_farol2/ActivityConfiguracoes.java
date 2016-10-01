package com.example.aluno.acenda_o_farol2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class ActivityConfiguracoes extends AppCompatActivity {
    boolean som, vibra;
    private BancoController bancoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_configuracoes);

        bancoController = new BancoController(getApplicationContext());
        bancoController.iniciaBanco();
        Cursor cursor = bancoController.carregaDados();

        final Switch switch_sound = (Switch) findViewById(R.id.switchAlertaSonoro);
        final Switch switch_vibra = (Switch) findViewById(R.id.switchAlertaVibra);

        som = cursor.getInt(cursor.getColumnIndex(CriaBanco.CAMPO_SOM)) > 0;
        vibra = cursor.getInt(cursor.getColumnIndex(CriaBanco.CAMPO_VIBRA)) > 0;

        switch_sound.setChecked(som);
        switch_vibra.setChecked(vibra);


        // QUANDO MUDAR O STATUS DO SOM DO APLICATIVO ALTERAR NO BANCO
        switch_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bancoController.alteraDadoSom(switch_sound.isChecked());
            }
        });

        // QUANDO MUDAR O STATUS DO VIBRA DO APLICATIVO ALTERAR NO BANCO
        switch_vibra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bancoController.alteraDadoVibra(switch_vibra.isChecked());
            }
        });
    }
}
