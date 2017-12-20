package com.example.edgar.optotypedevelope;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Edgar on 11/12/2017.
 */

public class ElementsInteraction {

    private ArrayList<Optotype> elements = new ArrayList<Optotype>();
    private Context context;

    public ElementsInteraction(Context context) {
        this.context = context;
    }

    public ElementsInteraction() {
    }

    public ArrayList<Optotype> getElements() {
        return elements;
    }

    public void fillInteractionElements (){

        ArrayList <Optotype> optotypes = new ArrayList<Optotype>();
        //Optotype optotype;
        int count = 1;
        Log.d("message: ", "ingresa en la carga de de optotipos");
        RequestOptotype requestOptotype = new RequestOptotype("optotypes",context);
        requestOptotype.findOptotypes();
        optotypes = requestOptotype.takeOptotypes();

        for (Optotype element : optotypes){
            //optotype = new Optotype();
            /*optotype.setIdOptotype(Integer.toString(count));
            optotype.setOptotypeName(element);
            optotype.setOptotypeCode(element);
            count++;*/
            elements.add(element);
        }



    }


    //metodo para probar llenado de elementos
    public void viewInteractionElements(){

        Log.d("message: ", "size = " + this.elements.size());

    }

    public int validateElements (int position, int sizeElements){

        if ((position + 1 >= sizeElements)){
            position = 0;
        }

        return position;
    }

    public boolean evenNumber (int position){

        boolean value = false;

        if ((position % 2) == 0){
            value = true;
        }

        return value;
    }

    public boolean primeNumber(int position, int size){

        boolean value = false;
        int results = 0;

        for (int i = 1; i<=size; i++){

            if (position % i == 0)
                results ++;
            if (results > 2){
                value = true;
                break;
            }
        }

        return value;
    }

    public String getImageOptotype (String code){

        Log.d("message: ", "acedi a solicitud de optotypo image");
        String value = "";
        RequestOptotype requestOptotype = new RequestOptotype("optotypes",context);

        return requestOptotype.getBipmapOptotype(code);
    }

}
