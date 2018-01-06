package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CrudDeleteAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listPatients;
    TextView messageText;
    ImageView perfil;
    Button actionDelete;

    Patient patient;
    Context contextActivity;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_delete_appointment);

        contextActivity = this;
        listPatients = (ListView) findViewById(R.id.listPatienCrudD);
        messageText = (TextView) findViewById(R.id.idCrudDMessage);
        perfil = (ImageView) findViewById(R.id.idCrudDImagePeril);
        actionDelete = (Button) findViewById(R.id.idCrudDButtonAcepted);

        messageText.setVisibility(View.INVISIBLE);
        perfil.setVisibility(View.INVISIBLE);
        actionDelete.setVisibility(View.INVISIBLE);

        actionDelete.setOnClickListener(this);

        loadListPatientsToday();

    }

    @Override
    public void onClick(View v) {

        RequestAppointment requestAppointment = new RequestAppointment("appointment",this);
        requestAppointment.requestDeleteActualAppointment(patient);

        refreshPatientData();

        finish();
        startActivity(getIntent());
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
        listPatients.setAdapter(patientsAdapter);

        actionOnElement ();

    }


    public void actionOnElement (){

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos = position;

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

        String messageDelete = "Esta seguro que desea eleminar la cita de hoy dd/mm/yyyy para el paciente ";
        messageDelete = messageDelete + patient.getName() + " " + patient.getMiddleName() + " ";
        messageDelete = messageDelete + patient.getLastName() + " " + patient.getMaidenName();

        perfil.setVisibility(View.VISIBLE);
        messageText.setText(messageDelete);
        messageText.setVisibility(View.VISIBLE);
        actionDelete.setVisibility(View.VISIBLE);

    }

    public void refreshPatientData(){

        String messageDelete = " La cita a sido eliminada con exito";

        listPatients.removeViewAt(pos);

        actionDelete.setVisibility(View.INVISIBLE);

        perfil.setImageResource(R.drawable.ok);
        messageText.setText(messageDelete);

    }



}
