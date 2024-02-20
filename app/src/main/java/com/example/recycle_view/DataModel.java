package com.example.recycle_view;

public class DataModel {

    private String name;
    private  String version;
    private int id_;
    private int image;
    private String description;

    public DataModel(String name, String version, int id_, int image, String description) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image = image;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getId_() {
        return id_;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
