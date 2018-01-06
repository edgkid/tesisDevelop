package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Edgar on 05/01/2018.
 */

public class RequestAppointment {

    private String request;
    private Context context;

    public RequestAppointment() {
    }

    public RequestAppointment(String request, Context context) {
        this.request = request;
        this.context = context;
    }


    public void requestDeleteActualAppointment (Patient patient){

        HttpHandlerAppointment httpHandlerAppointment = new HttpHandlerAppointment(request,context);
        httpHandlerAppointment.connectToResource((CrudDeleteAppointmentActivity) context, patient);

    }

}
