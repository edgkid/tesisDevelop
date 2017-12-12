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
            refreshInteractionActivity();

        }

    }

    public void refreshInteractionActivity(){

        String image = "";
        int position = 0;
        int sizeElements = 0;

        Double number = Math.floor(Math.random() * elements.getElements().size());
        position = number.intValue();
        sizeElements = elements.getElements().size();
        Log.d("message: ", String.valueOf(position));


        image = elements.getElements().get(position).getOptotypeCode();
        Log.d("message: ", image);
        assingBipmapImage(image, imageOptotype);
        assignOptotypeOptions(position, sizeElements, image);

    }

    public void assignOptotypeOptions(int position, int size, String image/*, String resource*/){

        if (elements.primeNumber(position, size) && elements.evenNumber(position)){

            assingBipmapImage(image, imageOptotypeA);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeB);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeC);

        }else if (elements.primeNumber(position, size) || ! elements.evenNumber(position)){

            assingBipmapImage(image, imageOptotypeC);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeB);

            position ++;

            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeA);

        }else{
            assingBipmapImage(image, imageOptotypeB);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeA);

            position ++;
            image = elements.getElements().get(elements.validateElements(position, size)).getOptotypeCode();
            assingBipmapImage(image, imageOptotypeC);
        }

    }

    public void assingBipmapImage (String image, ImageView optotypeOption) {

        byte[] byteCode = Base64.decode(elements.getImageOptotype(image),Base64.DEFAULT);
        Bitmap imageCode = null;

        try{
            imageCode = BitmapFactory.decodeByteArray(byteCode, 0 , byteCode.length);
        }catch (Exception e){
            Log.d("message: ","Erro al convertir imagen");
            imageCode = null;
        }
        if (imageCode != null)
            optotypeOption.setImageBitmap(imageCode);
        else
            optotypeOption.setImageResource(R.drawable.usuario_icon);

        optotypeOption.setTag(image);
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
