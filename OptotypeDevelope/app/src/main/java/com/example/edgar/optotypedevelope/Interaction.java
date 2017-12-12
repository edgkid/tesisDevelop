package com.example.edgar.optotypedevelope;

import java.util.ArrayList;

/**
 * Created by Edgar on 11/12/2017.
 */

public class Interaction {

    private Patient patient;
    private ArrayList<Optotype> optotypes = new ArrayList<Optotype>();
    private int Position;
    private int totalOptotypes;

    public Interaction() {
        this.totalOptotypes = 1;
    }

    public Interaction(Patient patient, ArrayList<Optotype> optotypes, int position, int totalOptotypes) {
        this.patient = patient;
        this.optotypes = optotypes;
        Position = position;
        this.totalOptotypes = totalOptotypes;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ArrayList<Optotype> getOptotypes() {
        return optotypes;
    }

    public void setOptotypes(ArrayList<Optotype> optotypes) {
        this.optotypes = optotypes;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public int getTotalOptotypes() {
        return totalOptotypes;
    }

    public void setTotalOptotypes(int totalOptotypes) {
        this.totalOptotypes = totalOptotypes;
    }

}
