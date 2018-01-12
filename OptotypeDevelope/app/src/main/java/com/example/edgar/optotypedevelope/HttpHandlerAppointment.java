package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edgar on 05/01/2018.
 */

public class HttpHandlerAppointment {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerAppointment(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public boolean sendRequestDelete (Patient patient, int option){

        URL url = null;
        boolean value = false;
        int responseCode;
        StringBuilder result = null;
        DataOutputStream printout;
        InputStream inputStreamResponse = null;
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ this.request;


        try{
            url = new URL (path);
            Log.d("message: ", path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/JSON");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonData(listParam, patient.getIdPatient(), option);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            Log.d("message: ", listParam.toString() );

            responseCode = connection.getResponseCode();
            if (responseCode <= 0 )
                Log.d("message: ", "menor");

            Log.d("message: ", String.valueOf(responseCode));

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();
                value = true;
                Log.d("message code:", String.valueOf(responseCode));
            }else
                Log.d("message: ", "Como que no conecto");

            if (inputStreamResponse != null){
                try{
                    inputStreamResponse.close();
                }
                catch(Exception ex){
                    Log.d(this.getClass().toString(), "Error cerrando InputStream", ex);
                }
            }


        }catch (IOException e){
            e.printStackTrace();
            Log.d("message: ", "Error no estoy haciendo conexion");
        }

        return value;

    }


    public void connectToResource (final CrudDeleteAppointmentActivity ctx, final Patient patient, final int option){

        Thread tr = new Thread(){

            @Override
            public void run() {

                if(sendRequestDelete(patient,option))
                    delePatientToday(patient);
            }
        };
        tr.start();
    }

    public void connectToResource (final CrudModifyAppointmentActivity ctx, final Patient patient, final int option){

        Thread tr = new Thread(){

            @Override
            public void run() {

                if(sendRequestDelete(patient,option))
                    delePatientToday(patient);
            }
        };
        tr.start();
    }

    public void connectToResource (final CrudSaveAppointmentActivity ctx, final Patient patient, final int option){

        Thread tr = new Thread(){
            @Override
            public void run() {
                if (sendRequestDelete(patient,option))
                    //ctx.answer = true;
                ;
            }
        };
        tr.start();

    }


    public void getJsonData (JSONArray  listParam, String idPatient, int option ){

        JSONObject jsonParam = new JSONObject();

        try {
            jsonParam.put("idPatient", idPatient);
            jsonParam.put("status", "N");
            jsonParam.put("action",option);
            if (option == 1 || option == 0)
                jsonParam.put("appointmentDate","01/03/2018");
            listParam.put(jsonParam);
        }catch (Exception e){
            e.printStackTrace();
        }finally{

        }

    }

    public void delePatientToday(Patient patient){

        PatientDbHelper patientDbHelper = new PatientDbHelper(context);
        SQLiteDatabase db = patientDbHelper.getWritableDatabase();

        String query = " DELETE FROM " + PatientDbContract.PatientEntry.TABLE_NAME + " WHERE idPatient = ";
        query = query + patient.getIdPatient();

        db.execSQL(query);

    }


}
