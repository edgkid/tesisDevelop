package com.example.edgar.optotypedevelope;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 10/12/2017.
 */

public class PatientDbHelper extends DbApp {

    public PatientDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + PatientDbContract.PatientEntry.TABLE_NAME + " ( ";
        sql = sql + PatientDbContract.PatientEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + PatientDbContract.PatientEntry.ID + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.NAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.LASTNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MIDDLENAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MAIDENNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.YEARSOLD + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.PHOTO + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.FKUSER + " TEXT, ";
        sql = sql + " UNIQUE ( " + PatientDbContract.PatientEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql ="";
        ContentValues values = new ContentValues();

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + PatientDbContract.PatientEntry.TABLE_NAME + " ( ";
        sql = sql + PatientDbContract.PatientEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + PatientDbContract.PatientEntry.ID + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.NAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.LASTNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MIDDLENAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.MAIDENNAME + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.YEARSOLD + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.PHOTO + " TEXT, ";
        sql = sql + PatientDbContract.PatientEntry.FKUSER + " TEXT, ";
        sql = sql + " UNIQUE ( " + PatientDbContract.PatientEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }
}
