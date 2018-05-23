package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Edgar on 23/05/2018.
 */

public class SoundInteraction {

    private String value;
    private MediaPlayer mediaPlayer;
    private Context context;

    public SoundInteraction(String value, Context context) {
        this.value = value;
        this.context = context;
        mediaPlayer = null;
    }


    public void playSound(){

        switch (value){
            case "arbol":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "avion":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "bandera":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "barco":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "bonbillo":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "botella":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "camara":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "cambur":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "camion":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "carro":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "casa":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "circulo":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "corazon":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "cuadrado":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "estrella":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "flor":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "helado":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "hueso":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "lapiz":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "mariposa":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "pelota":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "pez":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "snellen":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "sol":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "telefono":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "televisor":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;
            case "tetero":
                mediaPlayer = MediaPlayer.create(this.context, R.raw.thisiscorrect);
                break;

        }

        mediaPlayer.start();

    }


}
