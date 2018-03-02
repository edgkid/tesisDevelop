package com.example.edgar.optotypedevelope;

/**
 * Created by Edgar on 02/03/2018.
 */

public class AvLastResultToDay {

    private String idPatient;
    private String lastAppointmentDate;
    private String nextAppointmentDate;
    private String avRight;
    private String avLeft;
    private String Description;
    private String center;
    private String sustain;
    private String maintain;

    public AvLastResultToDay() {
    }

    public AvLastResultToDay(String idPatient, String lastAppointmentDate, String nextAppointmentDate, String avRight, String avLeft, String description, String center, String sustain, String maintain) {
        this.idPatient = idPatient;
        this.lastAppointmentDate = lastAppointmentDate;
        this.nextAppointmentDate = nextAppointmentDate;
        this.avRight = avRight;
        this.avLeft = avLeft;
        Description = description;
        this.center = center;
        this.sustain = sustain;
        this.maintain = maintain;
    }


    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getLastAppointmentDate() {
        return lastAppointmentDate;
    }

    public void setLastAppointmentDate(String lastAppointmentDate) {
        this.lastAppointmentDate = lastAppointmentDate;
    }

    public String getNextAppointmentDate() {
        return nextAppointmentDate;
    }

    public void setNextAppointmentDate(String nextAppointmentDate) {
        this.nextAppointmentDate = nextAppointmentDate;
    }

    public String getAvRight() {
        return avRight;
    }

    public void setAvRight(String avRight) {
        this.avRight = avRight;
    }

    public String getAvLeft() {
        return avLeft;
    }

    public void setAvLeft(String avLeft) {
        this.avLeft = avLeft;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getSustain() {
        return sustain;
    }

    public void setSustain(String sustain) {
        this.sustain = sustain;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }
}
