package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.util.Log;

/**
 * Created by Edgar on 21/12/2017.
 */

public class RequestMedicalTest {

    private String request;
    private Context context;

    public RequestMedicalTest() {
    }

    public RequestMedicalTest(Context context) {
        this.context = context;
    }

    public RequestMedicalTest(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void saveTest (VisualTest visualTest){

        Log.d("message: ", "llego al metodo que salvara cada test");

    }

}
