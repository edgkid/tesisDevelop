package com.example.edgar.optotypedevelope;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ResultInteractionActivity extends AppCompatActivity {

    ImageView imagePerfil;
    TextView  textNames;
    TextView  textLastNames;
    TextView  textYearsOld;

    ListView interactionResult;

    Bundle patientExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_interaction);

        imagePerfil = (ImageView) findViewById(R.id.imageViewResultPatient);
        textNames = (TextView) findViewById(R.id.textViewNAmePatient);
        textLastNames = (TextView) findViewById(R.id.textViewLastnamePatient);
        textYearsOld = (TextView) findViewById(R.id.textYearsPatient);
        interactionResult = (ListView) findViewById(R.id.listResult);

        Intent intentData = getIntent();
        patientExtras = intentData.getExtras();

        if (patientExtras != null){
            proccessBundle();
            proccessPhoto((String)patientExtras.get("IdPatient"));
        }


    }

    public void proccessBundle(){

        Log.d("message: ", "ProcessBundle");
        String []completeName = patientExtras.get("Patient").toString().split(" ");
        String [] years = patientExtras.get("YearsOld").toString().split(" ");

        textNames.setText("Nombre: " + completeName[1] + " " + completeName[2]);
        textLastNames.setText("Apellido: " + completeName[3] + " " + completeName[4]);
        textYearsOld.setText("Edad: " + years[1] + " años");
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
                imagePerfil.setImageBitmap(image);
            else
                imagePerfil.setImageResource(R.drawable.usuario_icon);
        }
    }

    public void loadListOptotypes (){
        
    }




}
