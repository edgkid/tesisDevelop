package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 10/12/2017.
 */

public class InitializeApp {

    Context context;
    Cursor cursor;

    public InitializeApp (Context context) {
        this.context = context;
    }

    public void findOrCreteTablePatients (){

        PatientDbHelper patientDb = new PatientDbHelper(this.context);
        SQLiteDatabase db = patientDb.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT idPatient FROM " + PatientDbContract.PatientEntry.TABLE_NAME, null);
        }catch (Exception e){
            patientDb.onCreate(db);
        }
    }

    public void findOrCreateTableOptotypes () {

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT idOptotype FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME, null);
        }catch (Exception e){
            optotypeDb.onCreate(db);
        }
    }

}
