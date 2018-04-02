package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.util.Log;

/**
 * Created by Edgar on 02/04/2018.
 */

public class RequestDiagnostic {

    private String request;
    private Context context;

    public RequestDiagnostic() {
    }

    public RequestDiagnostic (Context context) {
        this.context = context;
    }

    public void sendDataDiagnostic(Diagnostic diagnostic, int action){

        Log.d("message:", "llmando a hhtpHandler");
        /*HttpHandlerInteraction httpHandlerInteraction = new HttpHandlerInteraction("test",this.context);
        httpHandlerInteraction.connectToResource((InteractionActivity) context, patient, action);*/
        HttpHandlerDiagnostic httpHandlerDiagnostic = new HttpHandlerDiagnostic("", this.context);
        httpHandlerDiagnostic.connectToResource((TestFormActivity) context, diagnostic, action);

    }

}
