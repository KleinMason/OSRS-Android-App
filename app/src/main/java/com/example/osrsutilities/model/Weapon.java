package com.example.osrsutilities.model;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Equipment {
    public static List<String> weaponList = new ArrayList<>();

    public Weapon(Equipment equipment) {
        name = equipment.name;
        slot = equipment.slot;
    }
}
