package com.jntucep.c19_delhi;

public class ImgUpload {
    String name;
    String URL;
    public ImgUpload(){

    }
    public ImgUpload(String name, String url){
        if(name.trim().equals("")){
            name = "No Name";
        }
        this.name = name;
        this.URL = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
