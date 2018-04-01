package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 31/03/2018.
 */

public class RequestSignalDefect {

    private String request;
    private Context context;

    public RequestSignalDefect(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public RequestSignalDefect(Context context) {
        this.context = context;
    }

    public void findSignalDefect(){

        SignalDbHelper signalDbHelper = new SignalDbHelper(this.context);
        SQLiteDatabase db = signalDbHelper.getReadableDatabase();

        Cursor cursor =null;

        try{
            cursor = db.rawQuery("SELECT idSignal FROM " + SignalDbContract.SignalDbContractEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()){
                ;
            }else{
                HttpHelperSignal httpHelperSignal = new HttpHelperSignal(this.request, this.context);
                httpHelperSignal.connectToResource((DashBoardActivity) this.context);
            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");

        }finally {

            cursor.close();
            db.close();

        }


    }

    public void getSignalDefect (ArrayList arraySignal){

        Cursor cursor =null;
        String where = " ORDER BY signalName asc ";
        SignalDbHelper signalDbHelper = new SignalDbHelper(this.context);
        SQLiteDatabase db = signalDbHelper.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT signalName FROM " + SignalDbContract.SignalDbContractEntry.TABLE_NAME + where, null);

            if (cursor.moveToFirst()) {
                do {
                    arraySignal.add(cursor.getString(0));
                } while(cursor.moveToNext());
            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");

        }finally {

            cursor.close();
            db.close();

        }

    }



}
