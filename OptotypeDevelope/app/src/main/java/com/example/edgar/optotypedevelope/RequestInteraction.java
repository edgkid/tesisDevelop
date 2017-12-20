package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.util.Log;

import java.util.Iterator;

/**
 * Created by Edgar on 20/12/2017.
 */

public class RequestInteraction {

    private String request;
    private Context context;

    public RequestInteraction() {
    }

    public RequestInteraction(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void saveInteraction (Interaction newData, Patient patient){

        Log.d("message: ", "metodo para salvar datos de interacción");

        Log.d("message: ",String.valueOf(patient.getIdPatient()));
        Log.d("message: ",patient.getName());
        Log.d("message: ",patient.getMiddleName());
        Log.d("message: ",patient.getLastName());
        Log.d("message: ", patient.getMaidenName());

        Iterator<Optotype> iterator = newData.getOptotypes().iterator();

        while (iterator.hasNext()){
            Log.d("message: ", iterator.next().getOptotypeCode().toString());
        }

    }
}
