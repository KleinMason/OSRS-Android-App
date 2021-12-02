package com.example.osrsutilities.model;

public class Item {
    private String id;
    private String name;
    private String examine;
    private String slot;

    public Item(String id) {
        this.id = id;
        name = "None";
        examine = "None";
        slot = "None";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}
