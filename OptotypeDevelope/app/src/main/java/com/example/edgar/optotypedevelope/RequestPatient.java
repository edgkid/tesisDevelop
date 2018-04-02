package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by Edgar on 10/12/2017.
 */

public class RequestPatient {

    private String request;
    private Context context;

    public RequestPatient(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public RequestPatient(Context context) {

        this.context = context;
    }

    public void findPatientsToDay(){


        PatientDbHelper PatientDb = new PatientDbHelper(this.context);
        SQLiteDatabase db = PatientDb.getReadableDatabase();
        Cursor cursor = null;

        try{

            cursor = db.rawQuery("SELECT name FROM " + PatientDbContract.PatientEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()){
                ;
            }else{
                HttpHandlerPatient httpRequestPatient = new HttpHandlerPatient(request, context);
                httpRequestPatient.connectToResource((DashBoardActivity) context);
            }

        }catch (Exception e){

            Log.d("message: ", "SQLite");

        }finally {
            cursor.close();
            db.close();
        }

    }

    public int CountPatinetsToday (){

        int number = 0;
        PatientDbHelper PatientDb = new PatientDbHelper(this.context);
        SQLiteDatabase db = PatientDb.getReadableDatabase();
        Cursor cursor   = null;

        try{

            cursor = db.rawQuery("SELECT name FROM " + PatientDbContract.PatientEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    number = cursor.getCount();
                } while(cursor.moveToNext());

            }

        }catch (Exception e){
            Log.d("message: ", "SQLite");
        }finally {
            cursor.close();
            db.close();
        }

        return number;
    }

    public PatientsToday [] TakePatientsToday (){

        int value = 0;
        Bitmap image = null;
        PatientsToday patientsData[] = new PatientsToday[CountPatinetsToday ()];

        PatientDbHelper PatientDb = new PatientDbHelper(this.context);
        SQLiteDatabase db = PatientDb.getReadableDatabase();
        Cursor cursor = null;

        try{

            cursor = db.rawQuery("SELECT name, middleName, lastName, maidenName, yearsOld, idPatient, photo FROM " + PatientDbContract.PatientEntry.TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    PatientsToday patient = new PatientsToday();
                    patient.setIdPatient(Integer.parseInt(cursor.getString(5)));
                    patient.setName("Paciente: " + cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3));
                    patient.setYearsOld("Edad: " + cursor.getString(4) + " años");
                    byte[] byteCode = Base64.decode(cursor.getString(6), Base64.DEFAULT);
                    image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
                    patient.setPhoto(image);
                    patientsData[value] = patient;
                    value ++;
                } while(cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d("message: ", "SQLite");
        }finally {
            cursor.close();
            db.close();
        }

        return patientsData;
    }

    public String getPhoto (String idPatient){

        PatientDbHelper PatientDb = new PatientDbHelper(this.context);
        SQLiteDatabase db = PatientDb.getReadableDatabase();
        Cursor cursor = null;
        String value = "";

        try{
            cursor = db.rawQuery("SELECT photo  FROM " + PatientDbContract.PatientEntry.TABLE_NAME + " WHERE idPatient = " + idPatient, null);

            if (cursor.moveToFirst())
                value = cursor.getString(0);
            else
                value = null;

        }catch ( Exception e){}finally {
            cursor.close();
            db.close();
        }

        return value;
    }

    public void getPatientById (String idPatient, Patient patient){


        PatientDbHelper PatientDb = new PatientDbHelper(this.context);
        SQLiteDatabase db = PatientDb.getReadableDatabase();
        Cursor cursor = null;

        try{
            cursor = db.rawQuery("SELECT name, lastName, middleName, maidenName, yearsOld, idPatient, nextAppointment, gender, patientDate  FROM " + PatientDbContract.PatientEntry.TABLE_NAME + " WHERE idPatient = " + idPatient, null);

            if (cursor.moveToFirst()){
                patient.setName(cursor.getString(0));
                patient.setLastName(cursor.getString(1));
                patient.setMiddleName(cursor.getString(2));
                patient.setMaidenName(cursor.getString(3));
                patient.setYearsOld(cursor.getString(4));
                patient.setIdPatient(cursor.getString(5));
                patient.setNextAppointment(cursor.getString(6));
                patient.setGender(cursor.getString(7));
                patient.setPatientDate(cursor.getString(8));
            }

        }catch ( Exception e){}finally {
            cursor.close();
            db.close();
        }

    }



    public void getSomePatientForNewAppointment (ListView list, Patient patient, int action){

        HttpHandlerPatient httpHandlerPatient = new HttpHandlerPatient(request,context);
        httpHandlerPatient.connectToResource((CrudSaveAppointmentActivity) context, list, patient, action);

    }

}
