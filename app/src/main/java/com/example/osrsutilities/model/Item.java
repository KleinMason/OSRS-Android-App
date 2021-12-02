package com.example.osrsutilities.model;

public class Item {
    private String name;
    private String examine;

    public Item(String name) {
        this.name = name;
        examine = "None";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }
}
