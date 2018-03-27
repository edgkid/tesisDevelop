package com.example.edgar.optotypedevelope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TestFormActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textAppointmentDate;
    TextView textPatientName;
    TextView textPatientDate;
    TextView textPatientYearsOld;
    TextView textPatientSex;

    Spinner dropDownCenter;
    Spinner dropDownSustain;
    Spinner dropDownMaintain;
    Spinner dropDownAvRight;
    Spinner dropDownLeft;
    Spinner dropDownTypeTest;
    Spinner dropDownColaboration;
    Spinner dropDownPreviusSignal;

    EditText textPreviusMon;
    EditText textPreviusDad;
    EditText textDescription;

    TextView textSignal;

    Button buttonProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_form);

        initializeFormViews();
        initializeFormDropDowns();

        actionDropDownAvRight();
        actionDropDownAvLeft();
        actionDropDownCenter();
        actionDropDownSustain();
        actionDropDownMaintain();
        actionDropDownTypeTest();
        actionDropDownColaboration();

    }

    @Override
    public void onClick(View v) {

    }

    public void initializeFormViews (){

        textAppointmentDate = (TextView) findViewById(R.id.TextAppointmentDate);
        textPatientName = (TextView) findViewById(R.id.TextPatient);
        textPatientDate = (TextView) findViewById(R.id.TextPatientDate);
        textPatientYearsOld = (TextView) findViewById(R.id.TextPatientYearsOld);
        textPatientSex = (TextView) findViewById(R.id.TextPatientSex);

        dropDownAvRight = (Spinner) findViewById(R.id.DropDownRight);
        dropDownLeft = (Spinner) findViewById(R.id.DropDownLeft);
        dropDownCenter = (Spinner) findViewById(R.id.DropDowCenter);
        dropDownSustain = (Spinner) findViewById(R.id.DropDowSustain);
        dropDownMaintain = (Spinner) findViewById(R.id.DropDowMaintain);
        dropDownTypeTest = (Spinner) findViewById(R.id.DropDownTypeTest);
        dropDownColaboration = (Spinner) findViewById(R.id.DropDownCoperation);
        dropDownPreviusSignal = (Spinner) findViewById(R.id.DropDownSignal);

        textPreviusMon = (EditText) findViewById(R.id.EditPreviusMon);
        textPreviusDad = (EditText) findViewById(R.id.EditPreviusDad);
        textDescription = (EditText) findViewById(R.id.EditDescription);
        textSignal = (TextView) findViewById(R.id.TextAllSignals);
        buttonProcess = (Button) findViewById(R.id.AppointmetProcess);

    }

    public void initializeFormDropDowns(){

        ArrayAdapter<CharSequence> adapterAv = ArrayAdapter.createFromResource(this, R.array.av, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>  adapterSubjective = ArrayAdapter.createFromResource(this, R.array.subjective, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>  adapterTest = ArrayAdapter.createFromResource(this, R.array.test, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence>  adapterColaboration = ArrayAdapter.createFromResource(this, R.array.efectividad, android.R.layout.simple_spinner_item);

        dropDownAvRight.setAdapter(adapterAv);
        dropDownLeft.setAdapter(adapterAv);
        dropDownCenter.setAdapter(adapterSubjective);
        dropDownSustain.setAdapter(adapterSubjective);
        dropDownMaintain.setAdapter(adapterSubjective);
        dropDownTypeTest.setAdapter(adapterTest);
        dropDownColaboration.setAdapter(adapterColaboration);

    }

    public void actionDropDownAvRight(){
        dropDownAvRight.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            String text;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = dropDownAvRight.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text = dropDownAvRight.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }
        });
    }

    public void actionDropDownAvLeft(){
        dropDownLeft.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            String text;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = dropDownLeft.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text = dropDownLeft.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }
        });
    }

    public void actionDropDownCenter (){
        dropDownCenter.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            String text;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = dropDownCenter.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text = dropDownCenter.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }
        });
    }

    public void actionDropDownSustain(){
        dropDownSustain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            String text;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = dropDownSustain.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text = dropDownSustain.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }
        });
    }

    public void actionDropDownMaintain(){
        dropDownMaintain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            String text;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = dropDownMaintain.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text = dropDownMaintain.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }
        });
    }

    public void actionDropDownTypeTest(){
        dropDownTypeTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String text;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = dropDownTypeTest.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text = dropDownTypeTest.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }
        });
    }

    public void actionDropDownColaboration(){
        dropDownColaboration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String text;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text = dropDownColaboration.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                text = dropDownColaboration.getSelectedItem().toString();
                Log.d("Datos: ", text);
            }
        });
    }

}
