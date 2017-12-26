package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

    public void sendRequestPOST (Patient patient){

        URL url = null;
        int responseCode;
        StringBuilder result = null;
        DataOutputStream printout;
        InputStream inputStreamResponse = null;
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ this.request;

        try{
            url = new URL (path);
            Log.d("message: ", path);
            Log.d("messafe: ", patient.getIdPatient());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/JSON");
            connection.setRequestProperty("charset", "utf-8");
            connection.setDoOutput(true);

            //Create JSONObject here
            JSONArray listParam = new JSONArray();
            getJsonData(listParam, patient.getIdPatient());

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

    public void connectToResource (final InteractionActivity ctx, final Patient patient){

        Log.d("message: ", "Genera solicitud de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {
                sendRequestPOST(patient);
            }
        };
        tr.start();

    }

    public void getJsonData (JSONArray  listParam, String idPatient ){

        JSONObject jsonParam = null;
        Cursor cursor = null;
        String query = "";

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(this.context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        query = "SELECT idOptotype, idPatient, testCode, eye FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;
        query = query + " WHERE idPatient = " + idPatient;

        try{
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {

                    jsonParam = new JSONObject();
                    jsonParam.put("idPatient", cursor.getString(1));
                    jsonParam.put("idOptotype", cursor.getString(0));
                    jsonParam.put("testCode", cursor.getString(2));
                    jsonParam.put("eye", cursor.getString(3));
                    listParam.put(jsonParam);

                } while (cursor.moveToNext());
            }
        }catch (Exception e){

            e.printStackTrace();
            Log.d("message: ", "Exception cursor o DB");
        }finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }

    }
}
