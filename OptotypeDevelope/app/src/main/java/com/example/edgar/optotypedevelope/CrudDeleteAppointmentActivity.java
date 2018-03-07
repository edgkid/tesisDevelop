package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CrudDeleteAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    ListView listPatients;

    View line;
    ImageView perfil;
    Button actionDelete;
    Button buttonLogOut;
    Button buttonUpdate;

    TextView names;
    TextView lastNames;
    TextView yearsOld;
    TextView lastAppointmentDate;
    TextView nextAppointmentDate;

    Patient patient;
    AvLastResultToDay avPatient;
    Context contextActivity;

    int action = 2;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_delete_appointment);

        contextActivity = this;
        listPatients = (ListView) findViewById(R.id.listPatienCrudD);

        perfil = (ImageView) findViewById(R.id.idCrudDImagePeril);
        actionDelete = (Button) findViewById(R.id.idCrudDButtonAcepted);
        names = (TextView) findViewById(R.id.idCrudDTextNames);
        lastNames = (TextView) findViewById(R.id.idCrudDTextLastNames);
        yearsOld = (TextView) findViewById(R.id.idCrudDTextYears);
        lastAppointmentDate = (TextView) findViewById(R.id.idCrudDLastAppointment);
        nextAppointmentDate = (TextView) findViewById(R.id.idCrudDNexttAppointment);

        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        line = (View) findViewById(R.id.separatorD);

        perfil.setVisibility(View.INVISIBLE);
        actionDelete.setVisibility(View.INVISIBLE);
        names.setVisibility(View.INVISIBLE);
        lastNames.setVisibility(View.INVISIBLE);
        yearsOld.setVisibility(View.INVISIBLE);
        lastAppointmentDate.setVisibility(View.INVISIBLE);
        nextAppointmentDate.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);

        actionDelete.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);

        loadListPatientsToday();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonUpdate:
                loadListPatientsToday();
                break;
            case R.id.idCrudDButtonAcepted:
                alertDialog();
                break;
            case R.id.buttonLogout:
                logOutApp();
                break;
        }
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

        names.setText(patient.getName() + " " + patient.getMiddleName());
        lastNames.setText(patient.getLastName() + " " + patient.getMaidenName());
        yearsOld.setText(patient.getYearsOld() + "años");

        gettAvDataByPatient();
        lastAppointmentDate.setText((avPatient.getLastAppointmentDate() != null)? "Ultima Consulta: " + avPatient.getLastAppointmentDate(): "Ultima Consulta: " + patient.getNextAppointment());
        nextAppointmentDate.setText("Proxima Consulta: " + patient.getNextAppointment());

        perfil.setVisibility(View.VISIBLE);
        names.setVisibility(View.VISIBLE);
        lastNames.setVisibility(View.VISIBLE);
        yearsOld.setVisibility(View.VISIBLE);
        lastAppointmentDate.setVisibility(View.VISIBLE);
        nextAppointmentDate.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        actionDelete.setVisibility(View.VISIBLE);

    }

    public void gettAvDataByPatient(){

        avPatient = new AvLastResultToDay();
        RequestAvResult requestAvResult = new RequestAvResult(this.contextActivity);
        requestAvResult.getAvResultByPatient(patient.getIdPatient().toString(), avPatient);

    }

    public void alertDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextActivity);
        alertDialog.setTitle("Se Eliminara una Cita");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("¿Seguro que desea eliminar la cita del paciente seleccionado?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAppointment();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    public void deleteAppointment(){

        RequestAppointment requestAppointment = new RequestAppointment("appointment",this);
        requestAppointment.requestDeleteActualAppointment(patient, action);

    }


}
