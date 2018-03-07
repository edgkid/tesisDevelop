package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Edgar on 06/03/2018.
 */

public class RequestAvResult {

    private String request;
    private Context context;

    public RequestAvResult(Context context) {
        this.context = context;
    }

    public RequestAvResult(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void findAvResultToday(){

        AvLastResultTodayDbHelper avLastResultTodayDbHelper = new AvLastResultTodayDbHelper(this.context);
        SQLiteDatabase db = avLastResultTodayDbHelper.getReadableDatabase();
        Cursor cursor =null;

        try{
            Log.d("explorar:","ejecutando consulta");
            cursor = db.rawQuery("SELECT idAvResult FROM " + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()){
                ;
            }else{
                HttpHandlerAvResult httpHandlerAvResult = new HttpHandlerAvResult(request, context);
                httpHandlerAvResult.connectToResource((DashBoardActivity) context);
            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");

        }finally {

            cursor.close();
            db.close();

        }

    }

    public void getAvResultByPatient (String idPatient, AvLastResultToDay avResult){

        AvLastResultTodayDbHelper avLastResultTodayDbHelper = new AvLastResultTodayDbHelper(this.context);
        SQLiteDatabase db = avLastResultTodayDbHelper.getReadableDatabase();
        Cursor cursor = null;

        try{
            Log.d("mensaje: ", idPatient);
            Log.d("mensaje: ","ejecutando consulta sobre Av");
            cursor = db.rawQuery("SELECT lastAppointmentDate, avRight, avLeft, description, center, sustain, maintain FROM " + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.TABLE_NAME + " WHERE idPatient = " + idPatient, null);

            if (cursor.moveToFirst()) {
                avResult.setIdPatient(idPatient);
                avResult.setLastAppointmentDate(cursor.getString(0));
                avResult.setAvRight(cursor.getString(1));
                avResult.setAvLeft(cursor.getString(2));
                avResult.setDescription(cursor.getString(3));
                avResult.setCenter(cursor.getString(4));
                avResult.setSustain(cursor.getString(5));
                avResult.setMaintain(cursor.getString(6));
            }

        }catch (Exception e){
            Log.d("mensaje: ", "SQLite");

        }finally {
            if(cursor != null)
                cursor.close();
            db.close();

        }

    }
}
