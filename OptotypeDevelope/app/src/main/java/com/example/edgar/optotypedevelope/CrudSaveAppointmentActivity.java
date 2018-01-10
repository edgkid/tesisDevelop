package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CrudSaveAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    Context contextActivity;

    ListView listPatientsM;
    TextView textNames;
    TextView textLastNames;
    TextView textyears;
    TextView newDate;
    Button save;
    DatePicker calendar;
    ImageView perfil;

    View line;
    int action = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_save_appointment);

        contextActivity = this;
        listPatientsM = (ListView) findViewById(R.id.listPatienCrudC);
        perfil = (ImageView) findViewById(R.id.idCrudImagePerfilC);
        textNames = (TextView) findViewById(R.id.idCrudTextNamesC);
        textLastNames = (TextView) findViewById(R.id.idCrudTextLastNamesC);
        textyears = (TextView) findViewById(R.id.idCrudTextYearsC);
        newDate = (TextView) findViewById(R.id.idCrudNewAppointmentC);
        calendar = (DatePicker) findViewById(R.id.idCrudDatePieckerC);
        save= (Button) findViewById(R.id.idCrudButtonAceptedC);
        line = (View) findViewById(R.id.separatorC);

        /*perfil.setVisibility(View.INVISIBLE);
        textNames.setVisibility(View.INVISIBLE);
        textLastNames.setVisibility(View.INVISIBLE);
        textyears.setVisibility(View.INVISIBLE);
        newDate.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);*/

        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        
    }
}
