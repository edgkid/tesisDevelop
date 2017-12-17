package com.example.edgar.optotypedevelope;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

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
    Interaction controlInteraction;
    /*
    Patient patient;*/
    Bundle patientExtras;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);

        controlInteraction = new Interaction();
        elements = new ElementsInteraction(this);

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


        imageOptotype.setOnLongClickListener(logClickListener);

        imageOptotypeA.setOnDragListener(dragListenerA);
        imageOptotypeB.setOnDragListener(dragListenerB);
        imageOptotypeC.setOnDragListener(dragListenerC);

        Intent intentData = getIntent();
        patientExtras = intentData.getExtras();

        if (patientExtras != null){
            proccessBundle();
            proccessPhoto((String)patientExtras.get("IdPatient"));
            refreshInteractionActivity();

        }

    }

    public void initializeActivity(){
        ArrayList<String> startingInitials = new ArrayList<String>();
        int position = 0;

        startingInitials.add("barco_1");
        startingInitials.add("sol_1");
        startingInitials.add("estrella_1");

        Double number = Math.floor(Math.random() * startingInitials.size());
        position = number.intValue();

        switch (startingInitials.get(position)){
            case "barco_1":
                imageOptotype.setImageResource(R.drawable.barco_1);
                imageOptotype.setTag("barco_1");
                break;
            case "estrella_1":
                imageOptotype.setImageResource(R.drawable.estrella_1);
                imageOptotype.setTag("estrella_1");
                break;
            case "sol_1":
                imageOptotype.setImageResource(R.drawable.sol_1);
                imageOptotype.setTag("sol_1");
                break;
        }

        imageOptotypeA.setImageResource(R.drawable.barco_1);
        imageOptotypeA.setTag("barco_1");
        imageOptotypeB.setImageResource(R.drawable.estrella_1);
        imageOptotypeB.setTag("estrella_1");
        imageOptotypeC.setImageResource(R.drawable.sol_1);
        imageOptotypeC.setTag("sol_1");



    }

    public void refreshInteractionActivity(){

        String image = "";
        int position = 0;
        int sizeElements = 0;

        elements.fillInteractionElements();
        Double number = Math.floor(Math.random() * elements.getElements().size());
        position = number.intValue();
        sizeElements = elements.getElements().size();
        Log.d("message: ", String.valueOf(position));

        try{
            image = elements.getElements().get(position).getOptotypeCode();
            Log.d("message: ", image);
            assingBipmapImage(image, imageOptotype);
            assignOptotypeOptions(position, sizeElements, image);
        }catch (Exception e){
            Log.d("message: ", "problemas con el llenado de la lista (Vacia)");
            if(this.flag){
                initializeActivity();
                this.flag = false;
                elements.fillInteractionElements();
            }
        }
    }

    public void assignOptotypeOptions(int position, int size, String image){

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

    ///////////////////////////////Drag Section////////////////////////////////////////////////////
    View.OnLongClickListener  logClickListener = new View.OnLongClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public boolean onLongClick(View v) {

            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                v.startDragAndDrop(data,myShadowBuilder,v,0);
            } else {
                v.startDrag(data,myShadowBuilder,v,0);
            }

            return true;
        }
    };

    View.OnDragListener dragListenerA = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            actionDrag(dragEvent, view, 1);

            return true;
        }
    };

    View.OnDragListener dragListenerB = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            actionDrag(dragEvent, view, 2);

            return true;
        }
    };

    View.OnDragListener dragListenerC = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            actionDrag(dragEvent, view, 3);

            return true;
        }
    };

    public void actionDrag (int dragEvent, View view, int option){

        switch (dragEvent){
            //Accion para mover elemento
            case DragEvent.ACTION_DRAG_ENTERED:
                //identificar si es correcta la selección
                if (option == 1 ) {
                    if (imageOptotypeA.getTag().equals(imageOptotype.getTag()))
                        imageOptotypeA.setBackgroundColor(Color.rgb(0, 255, 102));
                    else
                        imageOptotypeA.setBackgroundColor(Color.rgb(183,28,28));
                }else if(option == 2){
                    if (imageOptotypeB.getTag().equals(imageOptotype.getTag()))
                        imageOptotypeB.setBackgroundColor(Color.rgb(0, 255, 102));
                    else
                        imageOptotypeB.setBackgroundColor(Color.rgb(183,28,28));
                }else if (option == 3){
                    if (imageOptotypeC.getTag().equals(imageOptotype.getTag()))
                        imageOptotypeC.setBackgroundColor(Color.rgb(0, 255, 102));
                    else
                        imageOptotypeC.setBackgroundColor(Color.rgb(183,28,28));
                }
                break;

            case DragEvent.ACTION_DRAG_EXITED:

                if (option == 1 ) {
                    imageOptotypeA.setBackgroundColor(Color.rgb(255,255,255));
                }else if(option == 2){
                    imageOptotypeB.setBackgroundColor(Color.rgb(255,255,255));
                }else if (option == 3){
                    imageOptotypeC.setBackgroundColor(Color.rgb(255,255,255));
                }

                break;
            case DragEvent.ACTION_DROP:

                //accionar sobre el elemeno seleccionado

                if (option == 1 ) {
                    if (imageOptotypeA.getTag().equals(imageOptotype.getTag()))
                        workWithRealOption(imageOptotype, imageOptotypeA);
                    else
                        workWithBackOption(imageOptotype, imageOptotypeA);
                }else if(option == 2){
                    if (imageOptotypeB.getTag().equals(imageOptotype.getTag()))
                        workWithRealOption(imageOptotype, imageOptotypeB);
                    else
                        workWithBackOption(imageOptotype, imageOptotypeB);
                }else if (option == 3){
                    if (imageOptotypeC.getTag().equals(imageOptotype.getTag()))
                        workWithRealOption(imageOptotype, imageOptotypeC);
                    else
                        workWithBackOption(imageOptotype, imageOptotypeC);
                }

                refreshInteractionActivity();
                break;
        }
    }

    public void workWithRealOption (ImageView optotype, ImageView option){

        int sizeElements =0;
        int total = 0;
        option.setBackgroundColor(Color.rgb(255, 255, 255));
        /*textDebug.setText(optotype.getTag().toString());
        textDebugB.setText(option.getTag().toString());*/

        ArrayList<Optotype> optotypes = elements.getElements();
        Iterator<Optotype> iterator = optotypes.iterator();

        total = controlInteraction.getTotalOptotypes();

        while (iterator.hasNext()){

            if (iterator.next().getOptotypeCode().equals(optotype.getTag())){
                total ++;
                controlInteraction.setTotalOptotypes(total);
                textDebugB.setText(Integer.toString(controlInteraction.getTotalOptotypes()));
                controlInteraction.getOptotypes().add(optotypes.get(sizeElements));
                if (sizeElements != 0 ) {
                    controlInteraction.setTotalOptotypes(total);
                    elements.getElements().remove(sizeElements);
                }
                break;
            }
            sizeElements ++;

            if (controlInteraction.getTotalOptotypes() >= 12){

                /*RequestInteraction requestInteraction = new RequestInteraction(this);
                requestInteraction.insertInteractionActivityData(controlInteraction.getOptotypes(), patient);*/
                Toast.makeText(this, "fin de la interacción", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void workWithBackOption (ImageView optotype, ImageView option){
        option.setBackgroundColor(Color.rgb(255, 255, 255));
    }



}
