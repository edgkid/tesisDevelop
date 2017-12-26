package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Edgar on 25/12/2017.
 */

public class HttpHandlerInteraction {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerInteraction(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void sendRequestPOST (){

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
            //responseCode = connection.getResponseCode();// en caso de que halla respuesta el valor es 200;

            //Log.d("code paciente: ", Integer.toString(responseCode));
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/JSON");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonData(listParam);

            //DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            //dataOutputStream.write(Integer.parseInt(listParam.toString()));
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

    public void connectToResource (final InteractionActivity ctx){

        Log.d("message: ", "Genera solicitud de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {
                sendRequestPOST();
            }
        };
        tr.start();

    }

    public void getJsonData (JSONArray  listParam ){

        JSONObject jsonParam = new JSONObject();

        try{
            jsonParam.put("idPatient", "1");
            jsonParam.put("idOptotype", "1");
            jsonParam.put("testCode", "JL25112017L1");
            jsonParam.put("eye", "L");
            listParam.put(jsonParam);

            JSONObject jsonParam2 = new JSONObject();
            jsonParam2.put("idPatient", "1");
            jsonParam2.put("idOptotype", "11");
            jsonParam2.put("testCode", "JL25112017L1");
            jsonParam2.put("eye", "L");
            listParam.put(jsonParam2);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
