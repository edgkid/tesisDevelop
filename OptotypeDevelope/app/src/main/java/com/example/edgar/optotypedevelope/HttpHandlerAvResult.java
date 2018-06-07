package com.example.edgar.optotypedevelope;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Edgar on 06/03/2018.
 */

public class HttpHandlerAvResult {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerAvResult(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public String sendRequestGet (){

        URL url = null;
        String line = "";
        int responseCode;
        StringBuilder result = null;
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ this.request;
        String retunrValue = "";

        try{
            Log.d("explorar:", path);

            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();// en caso de que halla respuesta el valor es 200

            Log.d("explorar: ", Integer.toString(responseCode));
            // equivalente a preguntar si la respuesta es igual a 200
            if (responseCode == HttpURLConnection.HTTP_OK){

                result = new StringBuilder();
                InputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                while ((line = reader.readLine()) !=null ){
                    result.append(line);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if (result == null)
            retunrValue = "[{}]";
        else
            retunrValue = result.toString();

        Log.d("explorar:: ",retunrValue);

        //return result.toString();
        return retunrValue;
    }

    public boolean verifyRespondeServer (String result){

        boolean value = false;
        Log.d("explorar: ", "Metodo para verificar");

        try{

            JSONArray json = new JSONArray(result);
            if (json.length() > 0)
                value = true;

        }catch (Exception e){}

        return value;
    }

    public void connectToResource (final DashBoardActivity ctx){


        Log.d("explorar:","Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            Toast.makeText(ctx.getApplicationContext(),"Conexion con result", Toast.LENGTH_SHORT).show();
                            procesingJson(result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No result", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }

    public void procesingJson (String result){

        JSONArray array = null;
        String sql = "";
        ContentValues values = new ContentValues();

        Log.d("explorar: ", "Metodo para procesar JSON");
        Log.d("JSON: ", result.toString());

        AvLastResultTodayDbHelper avResult = new AvLastResultTodayDbHelper (this.context);
        SQLiteDatabase db = avResult.getWritableDatabase();

        try {

            Log.d("explorar: ", "SQLite");

            array = new JSONArray(result);

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);

                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry._ID, Integer.parseInt(jsonObj.getString("idAvResult")));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.ID, jsonObj.getString("idAvResult"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.IDPATIENT, jsonObj.getString("idPatient"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.LASTAPPOINTMENTDATE, jsonObj.getString("lastDate"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.AVRIGHT, jsonObj.getString("avRight"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.AVLEFT, jsonObj.getString("avLeft"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.DESCRIPTION, jsonObj.getString("description"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.CENTER, jsonObj.getString("center"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.SUSTAIN, jsonObj.getString("sustain"));
                values.put(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.MAINTAIN, jsonObj.getString("maintain"));

                db.insert(AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.TABLE_NAME, null, values);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("json: ", "No hay valor para procesar");
        }finally{
            db.close();
        }

    }
}
