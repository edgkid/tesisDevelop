package com.example.edgar.optotypedevelope;

/**
 * Created by Edgar on 10/12/2017.
 */

public class ItemMenuDoctor {

    private int icon;
    private String  option;

    public ItemMenuDoctor() {
        super();
    }

    public ItemMenuDoctor(int icon, String option) {
        this.icon = icon;
        this.option = option;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

}
