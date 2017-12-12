package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 11/12/2017.
 */

public class RequestOptotype {

    private String request;
    private Context context;

    public RequestOptotype(Context context) {

    }

    public RequestOptotype(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void findOptotypes (){

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT optotypeName FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME, null);
        Log.d("message: ", "va a verificar que existan o no elementos locales");

        if (cursor.moveToFirst()){
            Log.d("message: ","existen datos en la tabla local");
        }else{
            Log.d("message: ","NO existen Elementos, solicitar al servidor");
            HttpHandlerOptotype httpRequestOptotype = new HttpHandlerOptotype(request,context);
            httpRequestOptotype.connectToResource((InteractionActivity)context);
        }
    }

    public ArrayList<String> takeOptotypes (){

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper( this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<String> optotypesCode = new ArrayList<String>();
        String value = "";

        cursor = db.rawQuery("SELECT optotypeCode FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                value = String.valueOf(cursor.getString(0));
                optotypesCode.add(value);
            } while(cursor.moveToNext());
        }else {
            Log.d("message: ", "NO se lee registro");
        }

        return optotypesCode;
    }

    public String getBipmapOptotype (String code){
        Log.d("message: ", "acedi a la busqueda del Bitmap");
        String value = "";
        OptotypeDbHelper optotypeDb = new OptotypeDbHelper( this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;
        String sql = "SELECT image FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME + " WHERE optotypeCode = '" + code +"'";
        cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            value = cursor.getString(0);
        }

        return value;
    }



}
