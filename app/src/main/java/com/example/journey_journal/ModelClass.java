package com.example.journey_journal;

public class ModelClass {
    private int image1;
    private  int image2;
    private  int image3;
    private  String txt_title;
    private  String txt_des;
    private  String txt_location;


    ModelClass(int image1 , int image2 , int image3 , String txt_title , String txt_des , String txt_location){
        this.image1=image1;
        this.image2=image2;
        this.image3=image3;
        this.txt_title=txt_title;
        this.txt_des=txt_des;
        this.txt_location=txt_location;
    }

    public int getImage1() {
        return image1;
    }

    public int getImage2() {
        return image2;
    }

    public int getImage3() {
        return image3;
    }

    public String getTxt_title() {
        return txt_title;
    }

    public String getTxt_des() {
        return txt_des;
    }

    public String getTxt_location() {
        return txt_location;
    }
}
