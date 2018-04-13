package com.example.edgar.optotypedevelope;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CrudRequestTestActivity extends AppCompatActivity implements View.OnClickListener, MessageDialog.MessageDialogListener {

    ListView listPatients;
    ImageView test;
    Button buttonLogOut;
    Button buttonUpdate;
    Context contextActivity;

    int action = 4;
    int distanceByTest;
    PatientsToday patient = null;
    ArrayList<String> imagesTest = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_request_test);

        contextActivity = this;
        test = (ImageView) findViewById(R.id.idTestForPatient);
        listPatients = (ListView) findViewById(R.id.idListForRequesTest);

        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);

        buttonUpdate.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);

        test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                nextActivity();
            }
        });

        loadListPatientsToday();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogout:
                logOutApp();
                break;
            case R.id.buttonUpdate:
                loadListPatientsToday ();
                break;
        }
    }

    public void nextActivity (){

        Intent testFormActivity = new Intent(this, TestFormActivity.class);
        testFormActivity.putExtra("idPatient", String.valueOf(patient.getIdPatient()));
        testFormActivity.putStringArrayListExtra("listTest", imagesTest);
        startActivity(testFormActivity);


    }

    public void logOutApp (){
        cleanPreferencesLogin();

        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }

    public void cleanPreferencesLogin (){

        SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = loginPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();

    }

    public void loadListPatientsToday (){

        int countValue = 0;

        //Listado de pacieste desde el servicio web
        RequestPatient reuquestPatient = new RequestPatient("patients", this);
        reuquestPatient.findPatientsToDay();

        PatientsToday patientsData[] = new PatientsToday[reuquestPatient.CountPatinetsToday()];
        Log.d("message: ", Integer.toString(reuquestPatient.CountPatinetsToday()));
        PatientsToday patients[] = reuquestPatient.TakePatientsToday();

        while (countValue < reuquestPatient.CountPatinetsToday() ){
            patientsData[countValue] = new PatientsToday(patients[countValue].getName(), patients[countValue].getYearsOld(),patients[countValue].getPhoto(),patients[countValue].getIdPatient());
            countValue ++;
        }


        PatientsTodayAdapter patientsAdapter = new PatientsTodayAdapter(this,R.layout.listview_item_patients_today_row, patientsData);
        listPatients.setAdapter(patientsAdapter);

        actionOnElement();

    }

    public void actionOnElement (){

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                patient = (PatientsToday) parent.getAdapter().getItem(position);
                setDistanceByTest();

            }
        });

    }


    @Override
    public void applyData(String data) {
        distanceByTest = Integer.parseInt(data);
        /*RequestMedicalTest requestMedicalTest = new RequestMedicalTest("test",contextActivity);
        requestMedicalTest.requestTest(patient,distanceByTest, action, test);*/
        requestTest();
    }

    public void setDistanceByTest(){
        MessageDialog messageDialog = new MessageDialog();
        messageDialog.show(getSupportFragmentManager(),"Message Dialog");
    }

    public void requestTest (){
        RequestMedicalTest requestMedicalTest = new RequestMedicalTest("test",contextActivity);
        requestMedicalTest.requestTest(patient,distanceByTest, action, test, imagesTest);
        displayWaitDialog();
    }

    public void displayWaitDialog(){

        final ProgressDialog progressDialog = new ProgressDialog(contextActivity);
        progressDialog.setTitle("Generando Carta Optometrica");
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setMessage("Se estan procesando los optotypos que seran utilizado en el Examen. Por favor espere");
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (progressDialog.getProgress() <= progressDialog.getMax()){
                        Thread.sleep(400);
                        progressDialog.incrementProgressBy(3);
                        if (progressDialog.getProgress() == progressDialog.getMax()){
                            progressDialog.dismiss();
                        }
                    }
                }catch(Exception e){

                }
            }
        }).start();

        progressDialog.show();

    }
}
