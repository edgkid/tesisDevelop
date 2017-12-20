package com.example.edgar.optotypedevelope;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 20/12/2017.
 */

public class MedicalTestDbHelper extends  DbApp {


    public MedicalTestDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + MedicalTestDbContract.MedicalTestEntry.TABLE_NAME + " ( ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.ID + " TEXT, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.TESTCODE + " TEXT, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.EYE + " TEXT, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.IDPATIENT + " TEXT, ";
        sql = sql + " UNIQUE ( " + MedicalTestDbContract.MedicalTestEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + MedicalTestDbContract.MedicalTestEntry.TABLE_NAME + " ( ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.ID + " TEXT, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.TESTCODE + " TEXT, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.EYE + " TEXT, ";
        sql = sql + MedicalTestDbContract.MedicalTestEntry.IDPATIENT + " TEXT, ";
        sql = sql + " UNIQUE ( " + MedicalTestDbContract.MedicalTestEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

}
