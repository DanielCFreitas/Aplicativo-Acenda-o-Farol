package com.example.daniel.acendaofarol.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.daniel.acendaofarol.R;
import com.example.daniel.acendaofarol.database.BancoController;
import com.example.daniel.acendaofarol.model.Alerta;

public class ConfiguracoesActivity extends AppCompatActivity {
    boolean som, vibra;
    private BancoController bancoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        bancoController = new BancoController(getApplicationContext());
        bancoController.iniciaBanco();
        Alerta alerta = bancoController.carregaDados();

        final Switch switch_sound = (Switch) findViewById(R.id.switchAlertaSonoro);
        final Switch switch_vibra = (Switch) findViewById(R.id.switchAlertaVibra);

        switch_sound.setChecked(alerta.isSom());
        switch_vibra.setChecked(alerta.isVibra());


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
