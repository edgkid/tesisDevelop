package com.example.edgar.optotypedevelope;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DiagnosticActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imagePhoto;

    TextView textDate;
    TextView textPatient;
    TextView textYears;
    TextView textSex;
    TextView textReazon;
    TextView textAVRE;
    TextView textAVLE;
    TextView textAVRP;
    TextView textAVLP;
    TextView textCenter;
    TextView textSustain;
    TextView textMaintain;

    Button comeBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);
        initializeView();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        // se considera una buena practica y debe hacerse
        if (extras != null){
            textPatient.setText(textPatient.getText().toString() + extras.getString("patientName"));
            textYears.setText(textYears.getText().toString()+ extras.getString("patientName"));
            proccessPhoto(extras.getString("idPatient"));
        }

    }

    public void initializeView(){

        imagePhoto = (ImageView) findViewById(R.id.diagnosticPhoto);
        textDate = (TextView) findViewById(R.id.diagnosticAppointment);
        textPatient = (TextView) findViewById(R.id.diagnosticPatient);
        textYears = (TextView) findViewById(R.id.diagnosticYear);
        textSex = (TextView) findViewById(R.id.diagnosticsex);
        textReazon = (TextView) findViewById(R.id.diagnosticReazon);
        textAVRE = (TextView) findViewById(R.id.diagnosticAvRigthE);
        textAVLE = (TextView) findViewById(R.id.diagnosticAvLeftE);
        textAVRP = (TextView) findViewById(R.id.diagnosticAvRigthP);
        textAVLP = (TextView) findViewById(R.id.diagnosticAvLeftP);
        textCenter = (TextView) findViewById(R.id.diagnosticCenter);
        textSustain = (TextView) findViewById(R.id.diagnosticSustain);
        textMaintain = (TextView) findViewById(R.id.diagnosticMaintain);
        comeBackButton = (Button) findViewById(R.id.diagnosticButton);
        comeBackButton.setOnClickListener(this);

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
                imagePhoto.setImageBitmap(image);
            else
                imagePhoto.setImageResource(R.drawable.usuario_icon);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.diagnosticButton:
                Toast.makeText(this, "volver", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonLogout:
                Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonUpdate:
                Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
