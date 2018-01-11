package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrudSaveAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    Context contextActivity;

    ListView listPatientsC;

    TextView textNames;
    TextView textLastNames;
    TextView textyears;
    TextView newDate;
    TextView textShared;

    Button save;
    Button shared;

    DatePicker calendar;
    ImageView perfil;

    View line;
    int action = 0;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_save_appointment);

        contextActivity = this;
        listPatientsC = (ListView) findViewById(R.id.listPatienCrudC);
        perfil = (ImageView) findViewById(R.id.idCrudImagePerfilC);
        textNames = (TextView) findViewById(R.id.idCrudTextNamesC);
        textLastNames = (TextView) findViewById(R.id.idCrudTextLastNamesC);
        textyears = (TextView) findViewById(R.id.idCrudTextYearsC);
        newDate = (TextView) findViewById(R.id.idCrudNewAppointmentC);
        calendar = (DatePicker) findViewById(R.id.idCrudDatePieckerC);
        save= (Button) findViewById(R.id.idCrudButtonAceptedC);
        line = (View) findViewById(R.id.separatorC);

        shared = (Button) findViewById(R.id.idCrudButtonShareC);
        textShared = (TextView) findViewById(R.id.idCrudButtonShareC);

        /*perfil.setVisibility(View.INVISIBLE);
        textNames.setVisibility(View.INVISIBLE);
        textLastNames.setVisibility(View.INVISIBLE);
        textyears.setVisibility(View.INVISIBLE);
        newDate.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);*/

        save.setOnClickListener(this);
        shared.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.idCrudButtonShareC:
                action = 3;
                patient.setName(textShared.getText().toString());
                RequestPatient requestPatient = new RequestPatient("patients",contextActivity);
                requestPatient.getSomePatientForNewAppointment(listPatientsC, patient, action);
                break;
            case R.id.idCrudButtonAceptedC:
                Toast.makeText(contextActivity,"calendario", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
