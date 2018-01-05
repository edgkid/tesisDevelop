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

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    Button logOut;
    Button update;
    ImageView imageUser;

    ListView listViewMenu;
    Context contextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        contextActivity = this;
        logOut = (Button) findViewById(R.id.buttonLogout);
        update = (Button) findViewById(R.id.buttonUpdate);
        imageUser = (ImageView) findViewById(R.id.imageViewLoginUser);
        logOut.setOnClickListener((View.OnClickListener) contextActivity);
        update.setOnClickListener((View.OnClickListener) contextActivity);
        listViewMenu = (ListView) findViewById(R.id.listViewDashBoardMenu);

        loadMenu();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                //Toast.makeText(contextActivity,"logOut",Toast.LENGTH_SHORT).show();
                logOutDashBoard();
                break;
            case R.id.buttonUpdate:
                Toast.makeText(contextActivity,"Update",Toast.LENGTH_SHORT).show();
                //updateDashBoard();
                break;
        }
    }

    /**
     *This method clean the shared preference to user with login
     */
    public void cleanPreferencesLogin (){

        SharedPreferences loginPreferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = loginPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();

    }

    /**
     * This metohd clos the sesion
     */
    public void logOutDashBoard(){
        cleanPreferencesLogin();

        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
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
        listViewMenu.setAdapter(patientsAdapter);

        callInteractionActivityByPatient ();
    }

    /**
     * This metohd fill menu for Doctor
     */
    public void loadListMenuDoctor(){

        ItemMenuDoctor itemsData[] = new ItemMenuDoctor[]{
                new ItemMenuDoctor(R.drawable.icon_appoinment, "Nueva Consulta"),
                new ItemMenuDoctor(R.drawable.icon_modify, "Modificar Consulta"),
                new ItemMenuDoctor(R.drawable.icon_garbage, "Eliminar Consulta"),
                new ItemMenuDoctor(R.drawable.icon_calendar, "Consulta del Día"),
                new ItemMenuDoctor(R.drawable.icon_new, "Nuevo Paciente")
        };

        ItemMenuDoctorAdapter itemMenuDoctorAdapter = new ItemMenuDoctorAdapter(this, R.layout.listview_item_doctor_row, itemsData);
        listViewMenu.setAdapter(itemMenuDoctorAdapter);

        callActivitiesByDoctor();

    }

    /**
     * This method fill a Listview with option for user or list the patients
     */
    public void loadMenu(){

        SharedPreferences preferences = getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE);

        if (preferences.getString("roll", "defaultroll").equals("Doctor")){
            loadListMenuDoctor();
        }else if(preferences.getString("roll", "defaultroll").equals("Paciente Infantil")){
            loadListPatientsToday();
        }

    }

    /**
     * This metohd fill a list for Patients
     */
    public void callInteractionActivityByPatient (){

        final Intent interactionActivity = new Intent(this, InteractionActivity.class);

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textName  = (TextView)view.findViewById(R.id.namePatientToday);
                TextView textyears = (TextView)view.findViewById(R.id.yearsOldPatientToday);
                TextView textCode  = (TextView) view.findViewById(R.id.codePatient);

                Patient patient = new Patient();
                patient.setIdPatient(textCode.getText().toString());
                patient.setName(textName.getText().toString());
                patient.setYearsOld(textyears.getText().toString());

                callNewActivity(patient, interactionActivity);


            }
        });

    }

    /**
     * this metohd fill a list type menu for doctor
     */
    public void callActivitiesByDoctor (){

        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newActivity = null;
                switch (position){

                    case 0:
                        //newActivity = new Intent(contextActivity, AppoinmentListActivity.class);
                        Toast.makeText(contextActivity, "opcion A", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //newActivity = new Intent(contextActivity, ModifyAppoinmentActivity.class);
                        Toast.makeText(contextActivity, "opcion B", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        newActivity = new Intent(contextActivity, CrudDeleteAppointmentActivity.class);
                        break;
                    case 3:
                        newActivity = new Intent(contextActivity, CrudReadAppointmentActivity.class);
                        //Toast.makeText(contextActivity, "opcion D", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        //Toast.makeText(contextActivity,"Nuevo Paciente",Toast.LENGTH_SHORT).show();
                        Toast.makeText(contextActivity, "opcion E", Toast.LENGTH_SHORT).show();
                        break;
                }
                if (newActivity != null)
                    startActivity(newActivity);
            }
        });


    }

    public void callNewActivity (Patient patient, Intent activity) {

        RequestInteraction interaction = new RequestInteraction(this.contextActivity);

        if(interaction.validateInteraction(patient)){
            //Toast.makeText(this, "el paciente ya ejecuto la interacción", Toast.LENGTH_SHORT).show();
            Intent resultInteraction = new Intent(this, ResultInteractionActivity.class);
            resultInteraction .putExtra("Patient", patient.getName());
            resultInteraction .putExtra("YearsOld", patient.getYearsOld());
            resultInteraction .putExtra("IdPatient", patient.getIdPatient());
            startActivity(resultInteraction);
        } else{
            activity.putExtra("Patient", patient.getName());
            activity.putExtra("YearsOld", patient.getYearsOld());
            activity.putExtra("IdPatient", patient.getIdPatient());
            startActivity(activity);
        }
    }
}
