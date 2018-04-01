package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        }finally{
            cursor.close();
            db.close();
        }
    }

    public void findOrCreateTableOptotypes () {

        OptotypeDbHelper optotypeDb = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDb.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT idOptotype FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME, null);
        }catch (Exception e){
            optotypeDb.onCreate(db);
        }finally{
            cursor.close();
            db.close();
        }
    }


    public void findOrCreateTableInteraction (){

        InteractionDbHelper interactionDb = new InteractionDbHelper(this.context);
        SQLiteDatabase db = interactionDb.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT idInteraction FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME, null);
        }catch (Exception e){
            interactionDb.onCreate(db);
        }finally {
            cursor.close();
            db.close();
        }
    }

    public void findOrCreateTableAvResultToDay (){

        AvLastResultTodayDbHelper avResultDb = new AvLastResultTodayDbHelper(this.context);
        SQLiteDatabase db = avResultDb.getReadableDatabase();

        try{
            cursor = db.rawQuery("SELECT idAvResult FROM " + AvLastResultToDayDbContract.AvLastResultToDayDbContractEntry.TABLE_NAME, null);
        }catch (Exception e) {
            avResultDb.onCreate(db);
        }finally {
            cursor.close();
            db.close();
        }

    }

    public void findOrCreateTableSignalDefect(){

        SignalDbHelper  signalDb = new SignalDbHelper(this.context);
        SQLiteDatabase db = signalDb.getReadableDatabase();

        try{
            cursor = db.rawQuery(" SELECT idSignal FROM "+ SignalDbContract.SignalDbContractEntry.TABLE_NAME, null);
        }catch (Exception e){
            signalDb.onCreate(db);

        }finally{
            cursor.close();
            db.close();
        }

    }

    public void findOrCreateTableAntecedentDefect (){

        AntecedentDefectHelper antecedentDb = new AntecedentDefectHelper(this.context);
        SQLiteDatabase db = antecedentDb.getReadableDatabase();

        try{
            cursor = db.rawQuery(" SELECT antecedentName FROM " + AntecedentDefectContract.AntecedentDefectContractEntry.TABLE_NAME, null);
        }catch (Exception e){
            antecedentDb.onCreate(db);

        }finally {
            cursor.close();
            db.close();
        }

    }

}
