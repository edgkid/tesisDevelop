package com.example.edgar.optotypedevelope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
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


    public ArrayList<String> takeOptotypesByTest (String idPatient){

        InteractionDbHelper interaction = new InteractionDbHelper(this.context);
        SQLiteDatabase db = interaction.getReadableDatabase();
        ArrayList<String>  list = new ArrayList<String>();
        Cursor cursor = null;
        String query = "SELECT DISTINCT(idOptotype) FROM " + InteractionDbContract.InteractionEntry.TABLE_NAME;
        query = query + " WHERE idPatient = " + idPatient;

        Log.d("message: ", query);

        try{
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    list.add(cursor.getString(0));
                } while(cursor.moveToNext());
            }

        }catch (Exception e){
            Log.d("message: ", "Problema en requesMedicalTest (takeOptotypeByTest)");
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }

        return list;

    }

    public OptotypeForPatient[] takeOptotypesByTest( int size, ArrayList<String> optotypesId){

        int count = 0;
        String query = "";
        Bitmap image = null;
        OptotypeForPatient optotype = null;
        Iterator<String> iteratorOptotypeId = optotypesId.iterator();
        OptotypeForPatient optotypesData[] = new OptotypeForPatient[size];

        OptotypeDbHelper optotypeDbHelper = new OptotypeDbHelper(this.context);
        SQLiteDatabase db = optotypeDbHelper.getReadableDatabase();
        Cursor cursor = null;

        query = "SELECT idOptotype, optotypeName, image FROM " + OptotypeDbContract.OptotypeEntry.TABLE_NAME;
        query = query + " WHERE idOptotype IN (";

        while (iteratorOptotypeId.hasNext()){
            query = query + iteratorOptotypeId.next() + ",";
        }

        query = query.substring(0, (query.length()-1)) + ")";

        Log.d("message: ", query);

        try {

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    optotype = new OptotypeForPatient();
                    optotype.setIdOptotype(cursor.getString(0));
                    optotype.setOptotypeCode(cursor.getString(1));

                    byte[] byteCode = Base64.decode(cursor.getString(2), Base64.DEFAULT);
                    image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
                    optotype.setImage(image);
                    optotypesData[count] = optotype;
                    count ++;
                } while(cursor.moveToNext());
            }


        }catch (Exception e){
            e.printStackTrace();
            Log.d("message: ", "Probelmas con el cursor");
        }

        return optotypesData;
    }


    public void sendDataInteraction(Patient patient, int action){

        HttpHandlerInteraction httpHandlerInteraction = new HttpHandlerInteraction("test",this.context);
        httpHandlerInteraction.connectToResource((InteractionActivity) context, patient, action);

    }

    public void requestTest (PatientsToday patient, int distance, int action, ImageView test){

        HttpHandlerMedicalTest httpHandlerMedicalTest = new HttpHandlerMedicalTest(this.request, this.context);
        httpHandlerMedicalTest.connectToResource((CrudRequestTestActivity) context, patient, distance, action, test);


    }

}
