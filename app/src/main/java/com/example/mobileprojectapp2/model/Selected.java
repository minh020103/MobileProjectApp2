package com.example.mobileprojectapp2.model;

public class Selected {
    private int key;
    private int id;
    private String name;

    public Selected(int key, int id, String name) {
        this.key = key;
        this.id = id;
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
