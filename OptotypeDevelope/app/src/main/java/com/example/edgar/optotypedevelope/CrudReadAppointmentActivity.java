package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.List;

public class CrudReadAppointmentActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listPatients;

    TextView names;
    TextView lastNames;
    TextView yearsOld;
    TextView lastAppointment;
    TextView nextAppointment;
    TextView avRight;
    TextView avLeft;
    TextView center;
    TextView sustain;
    TextView maintain;
    TextView description;

    ImageView perfil;

    Context contextActivity;
    Patient patient = null;
    AvLastResultToDay avPatient = null;

    Button buttonLogOut;
    Button buttonUpdate;
    Button buttonDiagnostic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_read_appointment);

        contextActivity = this;

        listPatients = (ListView) findViewById(R.id.listPatienCrudR);
        names = (TextView) findViewById(R.id.idCrudRTextNamesR);
        lastNames = (TextView) findViewById(R.id.idCrudRTextLastNamesR);
        yearsOld = (TextView) findViewById(R.id.idCrudRTextYears);
        lastAppointment = (TextView) findViewById(R.id.idCrudRLastAppointment);
        nextAppointment = (TextView) findViewById(R.id.idCrudRNexttAppointment);
        avRight = (TextView) findViewById(R.id.idCrudAvRightEstimated);
        avLeft = (TextView) findViewById(R.id.idCrudAvLeftEstimated);
        center = (TextView) findViewById(R.id.idCrudCenterSubjective);
        sustain = (TextView) findViewById(R.id.idCrudSustainSubjective);
        maintain = (TextView) findViewById(R.id.idCrudMaintainSubjective);
        description = (TextView) findViewById(R.id.idCrudDescription);

        perfil = (ImageView) findViewById(R.id.idCrudRImagePeril);
        buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDiagnostic = (Button) findViewById(R.id.buttonViewDiagnosticRead);

        buttonLogOut.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonDiagnostic.setOnClickListener(this);

        loadListPatientsToday();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonLogout:
                logOutApp();
                break;
            case R.id.buttonUpdate:
                loadListPatientsToday();
                break;
            case R.id.buttonViewDiagnosticRead:
                Intent newActivity = new Intent(this, DiagnosticActivity.class);
                newActivity.putExtra("idPatient",patient.getIdPatient());
                newActivity.putExtra("patientName", patient.getName() + " " + patient.getMiddleName() + " " +patient.getLastName() + " " + patient.getMaidenName());
                newActivity.putExtra("year",patient.getYearsOld());
                startActivity(newActivity);
                break;
        }

    }

    public void logOutApp(){

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

        actionReadElement();

    }

    public void actionReadElement (){

        listPatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textCode  = (TextView) view.findViewById(R.id.codePatient);
                proccessPhoto(textCode.getText().toString());

                patient = new Patient();

                RequestPatient requestPatient = new RequestPatient(contextActivity);
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

        names.setText("Nombre: " + patient.getName() + " " + patient.getMiddleName());
        lastNames.setText("Apellido: " + patient.getLastName() + " " + patient.getMaidenName());
        yearsOld.setText("Edad: " + patient.getYearsOld() + " años");

        Log.d("explorar:", "voy a buscar datos de consulta");
        gettAvDataByPatient();

        lastAppointment.setText((avPatient.getLastAppointmentDate() != null)? "Ultima Consulta: " + avPatient.getLastAppointmentDate(): "Ultima Consulta: " + patient.getNextAppointment());
        nextAppointment.setText("Proxima Consulta: " + patient.getNextAppointment());
        avRight.setText((avPatient.getAvRight() != null)? "Av Derecho: " + avPatient.getAvRight(): "Av Derecho: 0");
        avLeft.setText((avPatient.getAvLeft() != null)? "Av Izquierdo: " + avPatient.getAvLeft(): "Av Izquierdo: 0");
        center.setText((avPatient.getCenter() != null)? "Centra: " + avPatient.getCenter(): "Centra: ?");
        sustain.setText((avPatient.getSustain() != null)? "Sostiene: " + avPatient.getSustain(): "Sostiene: ?");
        maintain.setText((avPatient.getMaintain() != null)? "Mantiene: " + avPatient.getMaintain(): "Mantiene: ?");
        description.setText((avPatient.getDescription() != null)? avPatient.getDescription(): "No Existe datos de consulta");
    }

    public void gettAvDataByPatient(){

        avPatient = new AvLastResultToDay();
        RequestAvResult requestAvResult = new RequestAvResult(this.contextActivity);
        requestAvResult.getAvResultByPatient(patient.getIdPatient().toString(), avPatient);

    }

}
