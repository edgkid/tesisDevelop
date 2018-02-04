package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrudRequestTestActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listPatients;
    ImageView test;

    Button buttonLogOut;
    Button buttonUpdate;
    Context contextActivity;

    int action = 4;

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

        Intent testPresentationActivity = new Intent(this, TestPresentationActivity.class);
        startActivity(testPresentationActivity);

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

                PatientsToday patient = (PatientsToday) parent.getAdapter().getItem(position);
                Log.d("message: ",String.valueOf(patient.getIdPatient()));
                Log.d("message: ",patient.getName());
                RequestMedicalTest requestMedicalTest = new RequestMedicalTest("test",contextActivity);
                requestMedicalTest.requestTest(patient,4, action, test);
            }
        });

    }



}
