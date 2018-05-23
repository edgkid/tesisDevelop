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

    public void findOptotypes (String year){

        String query = "SELECT optotypeName FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME;

        Log.d("message ", year);
        if (year.equals("3") ){
            query = query + " WHERE OptotypeYear <= 3 ORDER BY random() LIMIT 16";
        }
        if (year.equals("4")){
            query = query + " WHERE OptotypeYear <= 4 ORDER BY random() LIMIT 16";
        }
        if (year.equals("5")){
            query = query + " WHERE OptotypeYear <= 5 ORDER BY random() LIMIT 16";
        }

        Log.d("message ", query);

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;

        try{
            cursor = db.rawQuery(query, null);
            Log.d("message: ", "va a verificar que existan o no elementos locales");

            if (cursor.moveToFirst()){
                Log.d("message: ","existen datos en la tabla local");
            }else{
                Log.d("message: ","NO existen Elementos, solicitar al servidor");
                db.close();
                HttpHandlerOptotype httpRequestOptotype = new HttpHandlerOptotype(request,context);
                httpRequestOptotype.connectToResource((InteractionActivity)context);
            }

        }catch (Exception e){
            Log.d("message: ", "error SQLite");
        }finally{
            cursor.close();
            db.close();
        }


    }

    public ArrayList<Optotype> takeOptotypes (){

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper( this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;
        Optotype optotype = null;
        ArrayList<Optotype> optotypes = new ArrayList<Optotype>();
        String value = "";

        try{


            cursor = db.rawQuery("SELECT optotypeCode, idOptotype FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    optotype = new Optotype();
                    optotype.setIdOptotype(cursor.getString(1));
                    optotype.setOptotypeCode(cursor.getString(0));
                    optotypes.add(optotype);
                } while(cursor.moveToNext());
            }else {
                Log.d("message: ", "NO se lee registro");
            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");

        }finally {
            cursor.close();
            db.close();
        }

        return optotypes;
    }

    public String getBipmapOptotype (String code){
        Log.d("message: ", "acedi a la busqueda del Bitmap");
        String value = "";
        OptotypeDbHelper optotypeDb = new OptotypeDbHelper( this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();
        Cursor cursor = null;
        String sql = "SELECT image FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME + " WHERE optotypeCode = '" + code +"'";


        try{

            cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                value = cursor.getString(0);
            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");
        }finally{
            cursor.close();
            db.close();
        }


        return value;
    }




}
