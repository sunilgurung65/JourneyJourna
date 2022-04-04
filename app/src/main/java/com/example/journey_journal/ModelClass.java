package com.example.journey_journal;

import android.view.Display;

public class ModelClass {

    private  int id;
    private  String JTitle;
    private  String JDis;
    private  String JLocation;
    private  byte[] image;

    // for creating an empty model
    public ModelClass(){}

    public  ModelClass (int id, String jTitle, String jDis, String jLocation, byte[] image){
        this.id=id;
        this.JTitle=jTitle;
        this.JDis=jDis;
        this.JLocation=jLocation;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJTitle() {
        return JTitle;
    }

    public void setJTitle(String JTitle) {
        this.JTitle = JTitle;
    }

    public String getJDis() {
        return JDis;
    }

    public void setJDis(String JDis) {
        this.JDis = JDis;
    }

    public String getJLocation() {
        return JLocation;
    }

    public void setJLocation(String JLocation) {
        this.JLocation = JLocation;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


}

