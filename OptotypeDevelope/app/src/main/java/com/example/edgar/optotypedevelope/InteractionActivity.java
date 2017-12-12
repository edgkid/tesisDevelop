package com.example.edgar.optotypedevelope;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class InteractionActivity extends AppCompatActivity {

    TextView textNames;
    TextView textLastNames;
    TextView textYearsOld;

    ImageView imageOptotype;
    ImageView imagePerfil;
    ImageView imageAnimation;

    ImageView imageOptotypeA;
    ImageView imageOptotypeB;
    ImageView imageOptotypeC;

    TextView textDebug;
    TextView textDebugB;

    ElementsInteraction elements;
    /*Interaction controlInteraction;
    Patient patient;*/
    Bundle patientExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);

        /*controlInteraction = new Interaction();*/
        elements = new ElementsInteraction(this);
        elements.fillInteractionElements();


        textDebug = (TextView) findViewById(R.id.textDebug);
        textDebugB = (TextView) findViewById(R.id.textDebug2);

        textNames = (TextView) findViewById(R.id.textViewNAmePatient);
        textLastNames = (TextView) findViewById(R.id.textViewLastnamePatient);
        textYearsOld = (TextView) findViewById(R.id.textYearsPatient);

        imageOptotype = (ImageView) findViewById(R.id.imageViewOptotype);
        imageAnimation = (ImageView) findViewById(R.id.imageInteractionEmotion);
        imagePerfil = (ImageView) findViewById(R.id.imageViewInteractionPatient);

        imageOptotypeA = (ImageView) findViewById(R.id.imageOptotypeOptionA);
        imageOptotypeB = (ImageView) findViewById(R.id.imageOptotypeOptionB);
        imageOptotypeC = (ImageView) findViewById(R.id.imageOptotypeOptionC);


        /*imageOptotype.setOnLongClickListener(logClickListener);

        imageOptotypeA.setOnDragListener(dragListenerA);
        imageOptotypeB.setOnDragListener(dragListenerB);
        imageOptotypeC.setOnDragListener(dragListenerC);*/

        Intent intentData = getIntent();
        patientExtras = intentData.getExtras();

        if (patientExtras != null){
            proccessBundle();
            proccessPhoto((String)patientExtras.get("IdPatient"));
            //refreshInteractionActivity();

        }

    }


    public void proccessBundle(){

        Log.d("message: ", patientExtras.get("Patient").toString() );
        String []completeName = patientExtras.get("Patient").toString().split(" ");
        String [] years = patientExtras.get("YearsOld").toString().split(" ");
        String name = completeName[1] + " " + completeName[2];
        String lastName = completeName[3] + " " + completeName[4];
        String yearsOld = years[1];
        textNames.setText(name);
        textLastNames.setText(lastName);
        textYearsOld.setText(yearsOld);
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

}
