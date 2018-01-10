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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrudModifyAppointmentActivity extends AppCompatActivity implements View.OnClickListener {


    Context contextActivity;
    Patient patient;

    ListView listPatientsM;
    TextView textNames;
    TextView textLastNames;
    TextView textyears;
    TextView date;
    TextView newDate;
    Button   updated;
    DatePicker calendar;
    ImageView perfil;

    View line;
    int action = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_modify_appointment);

        contextActivity = this;
        listPatientsM = (ListView) findViewById(R.id.listPatienCrudM);
        perfil = (ImageView) findViewById(R.id.idCrudImagePerfilM);
        textNames = (TextView) findViewById(R.id.idCrudTextNamesM);
        textLastNames = (TextView) findViewById(R.id.idCrudTextLastNamesM);
        textyears = (TextView) findViewById(R.id.idCrudTextYearsM);
        date = (TextView) findViewById(R.id.idCrudLastAppointmentM);
        newDate = (TextView) findViewById(R.id.idCrudNewAppointmentM);
        calendar = (DatePicker) findViewById(R.id.idCrudDatePieckerM);
        updated = (Button) findViewById(R.id.idCrudButtonAceptedM);
        line = (View) findViewById(R.id.separatorM);

        perfil.setVisibility(View.INVISIBLE);
        textNames.setVisibility(View.INVISIBLE);
        textLastNames.setVisibility(View.INVISIBLE);
        textyears.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        newDate.setVisibility(View.INVISIBLE);
        calendar.setVisibility(View.INVISIBLE);
        updated.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);

        updated.setOnClickListener(this);

        loadListPatientsToday();

    }

    /**
     * This metohd fill menu for Patient
     */
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
        listPatientsM.setAdapter(patientsAdapter);

        actionOnElement();
    }

    public void actionOnElement (){

        listPatientsM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textCode  = (TextView) view.findViewById(R.id.codePatient);
                proccessPhoto(textCode.getText().toString());

                patient = new Patient();

                RequestPatient requestPatient = new RequestPatient(contextActivity);
                patient.setIdPatient(textCode.getText().toString());
                requestPatient.getPatientById(textCode.getText().toString(), patient);

                showPatientData();

            }
        });

    }

    public void proccessPhoto (String idPatient){

        Bitmap image = null;
        String code = null;
        RequestPatient requestPatient = new RequestPatient(this);

        code = requestPatient.getPhoto(idPatient);
        if (code != null){
            byte[] byteCode = Base64.decode(code, Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);

            if (image != null)
                perfil.setImageBitmap(image);
            else
                perfil.setImageResource(R.drawable.usuario_icon);
        }
    }

    public void showPatientData(){

        textNames.setText(patient.getName() + " " + patient.getMiddleName());
        textLastNames.setText(patient.getLastName() + " " + patient.getMaidenName());
        textyears.setText(patient.getYearsOld());


        perfil.setVisibility(View.VISIBLE);
        textNames.setVisibility(View.VISIBLE);
        textLastNames.setVisibility(View.VISIBLE);
        textyears.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        newDate.setVisibility(View.VISIBLE);
        calendar.setVisibility(View.VISIBLE);
        updated.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(View v) {

        String date = "";
        date = String.valueOf(calendar.getDayOfMonth()) + "/" + String.valueOf(calendar.getMonth()+1) + "/" + String.valueOf(calendar.getYear());
        newDate.setText(date);

        RequestAppointment requestAppointment = new RequestAppointment("appointment",this);
        requestAppointment.requestDeleteActualAppointment(patient, action);

    }
}
