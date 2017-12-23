package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.util.Iterator;

/**
 * Created by Edgar on 20/12/2017.
 */

public class RequestInteraction {

    private String request;
    private Context context;

    public RequestInteraction() {
    }

    public RequestInteraction(Context context) {
        this.context = context;
    }

    public RequestInteraction(String request, Context context) {
        this.request = request;
        this.context = context;
    }

    public void processInteraction (Interaction newData, Patient patient){

        int elementsToTest = 0;
        String rTestCode = "";
        String lTestCode = "";
        Iterator<Optotype> iterator = newData.getOptotypes().iterator();
        VisualTest rVisualTest = new VisualTest("R");
        VisualTest lVisualTest = new VisualTest("L");

        rTestCode = patient.getName().substring(0,1) + patient.getLastName().substring(0,1);
        rTestCode = rTestCode + rVisualTest.getTestCodeByInteraction() + rVisualTest.getTestEye();
        rTestCode = rTestCode + patient.getIdPatient();

        lTestCode = patient.getName().substring(0,1) + patient.getLastName().substring(0,1);
        lTestCode = lTestCode + lVisualTest.getTestCodeByInteraction() + lVisualTest.getTestEye();
        lTestCode = lTestCode + patient.getIdPatient();

        rVisualTest.setIdPatient(Integer.parseInt(patient.getIdPatient()));
        rVisualTest.setTestCode(rTestCode);

        lVisualTest.setIdPatient(Integer.parseInt(patient.getIdPatient()));
        lVisualTest.setTestCode(lTestCode);

        elementsToTest = newData.getOptotypes().size() / 2;

        Log.d("message: ", rTestCode);
        Log.d("message: ", lTestCode);

        while (iterator.hasNext()){
            if (elementsToTest < newData.getOptotypes().size() / 2)
                rVisualTest.getOptotypes().add(iterator.next());
            else
                lVisualTest.getOptotypes().add(iterator.next());
        }

        if (rVisualTest.getOptotypes() != null && lVisualTest.getOptotypes() != null){
            rVisualTest.save(this.context);
            lVisualTest.save(this.context);
        }

    }

    public boolean validateInteraction (Patient patient){

        Log.d("message: ", "validateInteraction");
        Cursor cursor = null;
        String query = "";
        String id = "";
        boolean value = false;

        InteractionDbHelper interactionDbHelper = new InteractionDbHelper(context);
        SQLiteDatabase db = interactionDbHelper.getReadableDatabase();

        query = "SELECT testCode FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;
        query = query + " WHERE idPatient = " + patient.getIdPatient();

        Log.d("message: ", query);

        try{

            cursor = db.rawQuery(query,null);

            if (cursor.moveToFirst()) {
                do {
                    Log.d("message: ", cursor.getString(0));
                    value = true;
                } while(cursor.moveToNext());
            }


        }catch (Exception e){
           e.printStackTrace();
           Log.d("error: ", "problema con el cursor");
        }finally{
            if (cursor != null)
                cursor.close();
            db.close();
        }

        Log.d("message: ", String.valueOf(value));

        return value;
    }

}
