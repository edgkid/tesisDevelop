package com.example.edgar.optotypedevelope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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
 * Created by Edgar on 10/12/2017.
 */

public class HttpHandlerPatient {

    private String request;
    private Context context;
    ServerPath serverPath = new ServerPath();

    public HttpHandlerPatient(String request, Context context) {
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
            Log.d("path: ", path);

            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();// en caso de que halla respuesta el valor es 200

            Log.d("code paciente: ", Integer.toString(responseCode));
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

        Log.d("result : ",retunrValue);

        return result.toString();
    }

    public String sendRequestPost (Patient patient, int action){

        String line = "";
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
            getJsonData(listParam, patient.getName(), action);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(listParam.toString());
            wr.flush();
            wr.close();

            Log.d("message: ", listParam.toString() );

            responseCode = connection.getResponseCode();

            if( responseCode == HttpURLConnection.HTTP_OK){
                inputStreamResponse = connection.getInputStream();
                Log.d("message code:", String.valueOf(responseCode));

                result = new StringBuilder();
                InputStream input = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                while ((line = reader.readLine()) !=null ){
                    result.append(line);
                }
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
        }

        return result.toString();
    }

    public void getJsonData (JSONArray listParam, String value, int action){

        JSONObject jsonParam = null;

        try{

            jsonParam = new JSONObject();
            jsonParam.put("action", action);
            jsonParam.put("patient", value);
            listParam.put(jsonParam);

        }catch (Exception e){
            e.printStackTrace();
            Log.d("message: ", "Exception cursor o DB");
        }

    }

    public boolean verifyRespondeServer (String result){

        boolean value = false;
        Log.d("message: ", "Metodo para verificar");

        try{

            JSONArray json = new JSONArray(result);
            if (json.length() > 0)
                value = true;

        }catch (Exception e){}

        return value;
    }

    public void connectToResource (final DashBoardActivity ctx){

        Log.d("message: ", "Entra en la solicitu de conexion");
        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestGet();
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            Toast.makeText(ctx.getApplicationContext(),"Conexion con patients", Toast.LENGTH_SHORT).show();
                            procesingJson(result);
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"Conexion No patients", Toast.LENGTH_SHORT).show();
                        interrupt();
                    }
                });

            }
        };
        tr.start();

    }

    public void connectToResource (final CrudSaveAppointmentActivity ctx, final ListView list, final Patient patient, final int action){

        Thread tr = new Thread(){
            @Override
            public void run() {

                final String result= sendRequestPost(patient, action);
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (verifyRespondeServer(result)){
                            fillList(list, result);
                            Log.d("message: ", "datos");
                        } else
                            Toast.makeText(ctx.getApplicationContext(),"problema para cargar lista", Toast.LENGTH_SHORT).show();
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

        Log.d("message: ", "Metodo para procesar JSON");
        Log.d("JSON: ", result.toString());

        PatientDbHelper PatientDb = new PatientDbHelper(this.context);
        SQLiteDatabase db = PatientDb.getWritableDatabase();
        try {

            Log.d("trabajando: ", "SQLite");

            array = new JSONArray(result);

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);
                Log.d("cuenta: ", ("Insert " + Integer.toString(i)+ " " + jsonObj.getString("idPatient")));

                values.put(PatientDbContract.PatientEntry._ID, Integer.parseInt(jsonObj.getString("idPatient")));
                values.put(PatientDbContract.PatientEntry.ID, jsonObj.getString("idPatient"));
                values.put(PatientDbContract.PatientEntry.NAME, jsonObj.getString("firstName"));
                values.put(PatientDbContract.PatientEntry.MIDDLENAME, jsonObj.getString("middleName"));
                values.put(PatientDbContract.PatientEntry.LASTNAME, jsonObj.getString("lastName"));
                values.put(PatientDbContract.PatientEntry.MAIDENNAME, jsonObj.getString("maidenName"));
                values.put(PatientDbContract.PatientEntry.YEARSOLD, jsonObj.getString("yearsOld"));
                values.put(PatientDbContract.PatientEntry.PHOTO, jsonObj.getString("image"));
                values.put(PatientDbContract.PatientEntry.NEXTAPPOINTMENT,jsonObj.getString("nextAppointmentDate"));

                db.insert(PatientDbContract.PatientEntry.TABLE_NAME, null, values);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("json: ", "No hay valor para procesar");
        }finally{
            db.close();
        }

    }

    public void fillList(ListView list, String result){

        Log.d("message:", result);

        JSONArray array = null;

        String namePatient;

        try{

            array = new JSONArray(result);

            PatientsToday patientsData[] = new PatientsToday[array.length()];

            for(int i=0; i<array.length(); i++){

                JSONObject jsonObj  = array.getJSONObject(i);
                Patient patient = new Patient ( jsonObj.getString("idPatient"),
                                                jsonObj.getString("firstName"),
                                                jsonObj.getString("lastName"),
                                                jsonObj.getString("middleName"),
                                                jsonObj.getString("maidenName"),
                                                "Edad: " + jsonObj.getString("yearsOld"),
                                                jsonObj.getString("image"),null);

                namePatient = patient.getName() + " " + patient.getMiddleName() + " " + patient.getLastName() + " " + patient.getMaidenName();

                byte[] byteCode = Base64.decode(patient.getPhoto(), Base64.DEFAULT);
                Bitmap image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
                patientsData[i] = new PatientsToday(namePatient, patient.getYearsOld(),image, Integer.parseInt(patient.getIdPatient()));
            }

            PatientsTodayAdapter patientsAdapter = new PatientsTodayAdapter(context,R.layout.listview_item_patients_today_row, patientsData);
            list.setAdapter(patientsAdapter);

        }catch(JSONException e){
            e.printStackTrace();
        }

    }



}
