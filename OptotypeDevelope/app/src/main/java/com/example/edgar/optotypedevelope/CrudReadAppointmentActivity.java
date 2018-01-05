package com.example.edgar.optotypedevelope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CrudReadAppointmentActivity extends AppCompatActivity {

    ListView listPatients;

    TextView names;
    TextView lastNames;
    TextView yearsOld;
    TextView ultimateAppointment;
    TextView av;

    ImageView perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_read_appointment);


        listPatients = (ListView) findViewById(R.id.listPatienCrudR);
        names = (TextView) findViewById(R.id.idCrudRTextNamesR);
        lastNames = (TextView) findViewById(R.id.idCrudRTextLastNamesR);
        yearsOld = (TextView) findViewById(R.id.idCrudRTextYears);
        ultimateAppointment = (TextView) findViewById(R.id.idCrudRLastAppointment);
        av = (TextView) findViewById(R.id.idCrudAvEstimated);
        perfil = (ImageView) findViewById(R.id.idCrudRImagePeril);

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
        listPatients.setAdapter(patientsAdapter);

    }
}
