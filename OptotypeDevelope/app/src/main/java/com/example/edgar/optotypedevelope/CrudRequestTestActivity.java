package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

public class CrudRequestTestActivity extends AppCompatActivity {

    Context contextActivity;
    ListView listPatients;
    ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_request_test);

        contextActivity = this;
        test = (ImageView) findViewById(R.id.idTestForPatient);
        listPatients = (ListView) findViewById(R.id.idListForRequesTest);

        loadListPatientsToday();
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

    }

}
