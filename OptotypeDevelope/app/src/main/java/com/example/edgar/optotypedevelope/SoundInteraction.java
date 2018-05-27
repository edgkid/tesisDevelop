package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by Edgar on 23/05/2018.
 */

public class SoundInteraction {


    public void playSound(Context context, String value){

        MediaPlayer mediaPlayer = null;

        switch (value){
            case "arbol":
                mediaPlayer = MediaPlayer.create(context, R.raw.arbol);
                break;
            case "avion":
                mediaPlayer = MediaPlayer.create(context, R.raw.avion);
                break;
            case "bandera":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "barco":
                mediaPlayer = MediaPlayer.create(context, R.raw.barco);
                break;
            case "bombillo":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "botella":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "camara":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "cambur":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "camion":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "carro":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "casa":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "circulo":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "corazon":
                mediaPlayer = MediaPlayer.create(context, R.raw.corazon);
                break;
            case "cuadrado":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "estrella":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "flor":
                mediaPlayer = MediaPlayer.create(context, R.raw.flor);
                break;
            case "helado":
                mediaPlayer = MediaPlayer.create(context, R.raw.helado);
                break;
            case "hueso":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "lapiz":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "mariposa":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "pelota":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "pez":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "snellen":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "sol":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "telefono":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "televisor":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;
            case "tetero":
                mediaPlayer = MediaPlayer.create(context, R.raw.thisiscorrect);
                break;

        }

        if (mediaPlayer != null)
            mediaPlayer.start();
    }


}
