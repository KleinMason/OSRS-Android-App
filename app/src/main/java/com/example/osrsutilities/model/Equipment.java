package com.example.osrsutilities.model;

public class Equipment {
//    int id = 0;
    String name = null;
    String slot = null;
    int attackStab = 0;
    int attackSlash = 0;
    int attackCrush = 0;
    int attackMagic = 0;
    int attackRanged = 0;
    int defenceStab = 0;
    int defenceSlash = 0;
    int defenceCrush = 0;
    int defenceMagic = 0;
    int defenceRanged = 0;
    int meleeStrength = 0;
    int rangedStrength = 0;
    int magicDamage = 0;
    // TODO: add requirements?;

    public Equipment() {
    }

    public Equipment(String name, String slot) {
//        this.id = id;
        this.name = name;
        this.slot = slot;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

//    public int getId() {
//        return id;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getSlot() {
        return slot;
    }

    public void setAttackStab(int stat) {
        attackStab = stat;
    }

    public int getAttackStab() {
        return attackStab;
    }

    public void setAttackSlash(int stat) {
        attackSlash = stat;
    }

    public int getAttackSlash() {
        return attackSlash;
    }

    public void setAllStats(int attStab, int attSlash) {
        attackStab = attStab;
        attackSlash = attSlash;
    }
}
