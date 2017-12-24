package com.example.edgar.optotypedevelope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Iterator;

/**
 * Created by Edgar on 21/12/2017.
 */

public class RequestMedicalTest {

    private String request;
    private Context context;

    public RequestMedicalTest() {
    }

    public RequestMedicalTest(Context context) {
        this.context = context;
    }

    public RequestMedicalTest(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void saveTest (VisualTest visualTest, Context context){

        Log.d("message: ", "llego al metodo que salvara cada test");
        ContentValues values = new ContentValues();
        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(context);
        SQLiteDatabase db = interactionDbHelper.getWritableDatabase();
        Iterator<Optotype> iterator = visualTest.getOptotypes().iterator();

        try{

            while (iterator.hasNext()){
                values.put(InteractionDbContract.InteractionEntry.IDOPTOTYPE, iterator.next().getIdOptotype());
                values.put(InteractionDbContract.InteractionEntry.IDPATIENT, visualTest.getIdPatient());
                values.put(InteractionDbContract.InteractionEntry.TESTCODE, visualTest.getTestCode());
                values.put(InteractionDbContract.InteractionEntry.EYE, visualTest.getTestEye());

                db.insert(InteractionDbContract.InteractionEntry.TABLE_NAME, null, values);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.d("Error: ", "Problema al guardar");
        }finally {
            db.close();
        }

    }


    public void takeOptotypesByTest (String idPatient){

        InteractionDbHelper interaction = new InteractionDbHelper(this.context);
        SQLiteDatabase db = interaction.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT DISTINCT(idOptotype) FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;
        query = query + " WHERE idPatient = " + idPatient;

        Log.d("message: ", query);

        try{
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Log.d("message: ", cursor.getString(0));
                } while(cursor.moveToNext());
            }

        }catch (Exception e){
            Log.d("message: ", "Problema en requesMedicalTest (takeOptotypeByTest)");
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }

    }

}
