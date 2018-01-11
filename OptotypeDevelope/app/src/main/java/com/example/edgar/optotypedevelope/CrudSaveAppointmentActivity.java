package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
    EditText textShared;

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
        textShared = (EditText) findViewById(R.id.idCrudShareSaveC);

        perfil.setVisibility(View.INVISIBLE);
        textNames.setVisibility(View.INVISIBLE);
        textLastNames.setVisibility(View.INVISIBLE);
        textyears.setVisibility(View.INVISIBLE);
        newDate.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);

        save.setOnClickListener(this);
        shared.setOnClickListener(this);
    }

    public void showData(PatientsToday patient){

        String[] name = patient.getName().split(" ");
        String[] years = patient.getYearsOld().split(" ");


        if (patient.getPhoto() != null)
            perfil.setImageBitmap(patient.getPhoto());
        else
            perfil.setImageResource(R.drawable.usuario_icon);

        textNames.setText(name[0] + " " + name[1]);
        textLastNames.setText(name[2] + " " + name[3]);
        textyears.setText("Edad: " + years[1]);

        perfil.setVisibility(View.VISIBLE);
        textNames.setVisibility(View.VISIBLE);
        textLastNames.setVisibility(View.VISIBLE);
        textyears.setVisibility(View.VISIBLE);
        newDate.setVisibility(View.VISIBLE);
        calendar.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);

    }

    public void actionOnElement (){

        listPatientsC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PatientsToday patientsToday = (PatientsToday) parent.getAdapter().getItem(position);

                Log.d("message: ", "paciente");
                Log.d("message: ", patientsToday.getName());
                Log.d("message: ", patientsToday.getYearsOld());
                Log.d("message: ", String.valueOf(patientsToday.getIdPatient()));
                Log.d("message: ", String.valueOf(patientsToday.getPhoto()));

                showData(patientsToday);
            }
        });

    }

    public void processNewDate (){

        String date = "";

        date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth()+1) + "/" + String.valueOf(calendar.getYear());
        newDate.setText("Nueva Fecha de Consulta: " + date);

    }

    @Override
    public void onClick(View v) {

        patient = new Patient();

        switch (v.getId()){
            case R.id.idCrudButtonShareC:
                action = 3;
                patient.setName(textShared.getText().toString());
                RequestPatient requestPatient = new RequestPatient("patients",contextActivity);
                requestPatient.getSomePatientForNewAppointment(listPatientsC, patient, action);
                actionOnElement();
                break;
            case R.id.idCrudButtonAceptedC:
                processNewDate();
                break;
        }
    }

}
