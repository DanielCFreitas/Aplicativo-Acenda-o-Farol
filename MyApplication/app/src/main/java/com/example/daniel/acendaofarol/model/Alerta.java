package com.example.daniel.acendaofarol.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.example.daniel.acendaofarol.R;

/**
 * Created by Daniel on 19/11/2016.
 */

public class Alerta {

    private boolean som;
    private boolean vibra;
    private Context context;
    private MediaPlayer mediaPlayer;

    public Alerta(Context context){
        this.context = context;
        mediaPlayer = MediaPlayer.create(context, R.raw.alert);
    }

    // Getters e Setters
    public boolean isVibra() {
        return vibra;
    }

    public void setVibra(boolean vibra) {
        this.vibra = vibra;
    }

    public boolean isSom() {
        return som;
    }

    public void setSom(boolean som) {
        this.som = som;
    }

    // Alerta com vibra
    public void alertarComVibra(){
        if (this.vibra){
            Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
        }
    }

    // Alerta com som
    public void alertarComSom(){
        if (this.som) {
            this.mediaPlayer.start();
        }
    }
}
