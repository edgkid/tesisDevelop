package com.example.edgar.optotypedevelope;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Edgar on 02/03/2018.
 */

public class AvLastResultTodayDbHelper extends  DbApp {

    public AvLastResultTodayDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql ="";

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.TABLE_NAME + " ( ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.ID + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.LASTAPPOINTMENTDATE + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.NEXTAPPOINTMENTDATE + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.AVRIGHT + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.AVLEFT + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.DESCRIPTION + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.CENTER + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.SUSTAIN + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.MAINTAIN + " TEXT, ";
        sql = sql + " UNIQUE ( " + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        String sql ="";

        // crear Tablas de base de datos
        sql = sql + "CREATE TABLE " + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.TABLE_NAME + " ( ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry._ID + " INTEGER PRIMARY KEY, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.ID + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.LASTAPPOINTMENTDATE + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.NEXTAPPOINTMENTDATE + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.AVRIGHT + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.AVLEFT + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.DESCRIPTION + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.CENTER + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.SUSTAIN + " TEXT, ";
        sql = sql + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.MAINTAIN + " TEXT, ";
        sql = sql + " UNIQUE ( " + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.ID + " ) ";
        sql = sql +" ) ";

        db.execSQL(sql);

    }


}
