package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 31/03/2018.
 */

public class RequestAntecedentDefect {

    private String request;
    private Context context;

    public RequestAntecedentDefect(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public RequestAntecedentDefect(Context context) {
        this.context = context;
    }

    public void findAntecedentDefect() {

        AntecedentDefectHelper antecedentDefectHelper = new AntecedentDefectHelper(this.context);
        SQLiteDatabase db = antecedentDefectHelper.getReadableDatabase();

        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT idAntecedent FROM " + AntecedentDefectContract.AntecedentDefectContractEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                ;
            } else {
                HttpHandlerAntecedent httpHandlerAntecedent = new HttpHandlerAntecedent(this.request, this.context);
                httpHandlerAntecedent.connectToResource((DashBoardActivity) context);
            }

        } catch (Exception e) {
            Log.d("message: ", "SQLite");

        } finally {

            cursor.close();
            db.close();

        }
    }

    public void getAntecendetDefect (ArrayList arrayAntecedent){

        Cursor cursor= null;
        String where = " ORDER BY antecedentName asc ";
        AntecedentDefectHelper antecedentDefectHelper = new AntecedentDefectHelper(this.context);
        SQLiteDatabase db = antecedentDefectHelper.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT antecedentName FROM " + AntecedentDefectContract.AntecedentDefectContractEntry.TABLE_NAME + where, null);

            if (cursor.moveToFirst()) {
                do {
                    arrayAntecedent.add(cursor.getString(0));
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.d("message: ", "SQLite");

        } finally {

            cursor.close();
            db.close();

        }


    }

}
