package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edgar on 12/01/2018.
 */

public class HttpHandlerMedicalTest {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerMedicalTest(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void sendRequestPost (PatientsToday patient, int distance, int action){

        URL url = null;
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

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonData(listParam, patient.getIdPatient(), distance, action);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            Log.d("message: ", listParam.toString() );

            responseCode = connection.getResponseCode();

            Log.d("message: ", String.valueOf(responseCode));

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();
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

    }

    public boolean verifyRespondeServer (String result){

        boolean value = false;

        try{

            JSONArray json = new JSONArray(result);
            if (json.length() > 0)
                value = true;

        }catch (Exception e){}

        return value;
    }

    public void connectToResource (final CrudRequestTestActivity ctx, final PatientsToday patient, final int distance, final int action){

        Log.d("message: ", "Genera solicitud de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                //final String result= sendRequestPost(patient, distance, action);
                sendRequestPost(patient, distance, action);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       /* if (verifyRespondeServer(result)){
                            Toast.makeText(ctx.getApplicationContext(),"se solicito test", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"No conecta", Toast.LENGTH_SHORT).show();*/
                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }

    public void getJsonData (JSONArray  listParam, int idPatient, int distance, int action){

        JSONObject jsonParam = null;

        try{

            jsonParam = new JSONObject();
            jsonParam.put("distance", distance);
            jsonParam.put("patientId", idPatient);
            jsonParam.put("action", action);
            listParam.put(jsonParam);


        }catch (Exception e){

            e.printStackTrace();
            Log.d("message: ", "Exception cursor o DB");
        }
    }


}
