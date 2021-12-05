package com.example.osrsutilities.model;

import java.util.ArrayList;
import java.util.List;

public class Equipment {
    String name = null;
    String slot = "none";
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
    int prayer = 0;
    // TODO: add requirements?;

    public static List<String> headList = new ArrayList<>();
    public static List<String> capeList = new ArrayList<>();
    public static List<String> neckList = new ArrayList<>();
    public static List<String> ammoList = new ArrayList<>();
    public static List<String> weaponList = new ArrayList<>();
    public static List<String> shieldList = new ArrayList<>();
    public static List<String> bodyList = new ArrayList<>();
    public static List<String> legsList = new ArrayList<>();
    public static List<String> handsList = new ArrayList<>();
    public static List<String> feetList = new ArrayList<>();
    public static List<String> ringList = new ArrayList<>();

    public Equipment() {
    }

    public Equipment(String slot) {
        this.slot = slot;
    }

    public Equipment(String name, String slot) {
        this.name = name;
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getAttackStab() {
        return attackStab;
    }

    public void setAttackStab(int stat) {
        attackStab = stat;
    }

    public int getAttackSlash() {
        return attackSlash;
    }

    public void setAttackSlash(int stat) {
        attackSlash = stat;
    }

    public int getAttackCrush() {
        return attackCrush;
    }

    public void setAttackCrush(int attackCrush) {
        this.attackCrush = attackCrush;
    }

    public int getAttackMagic() {
        return attackMagic;
    }

    public void setAttackMagic(int attackMagic) {
        this.attackMagic = attackMagic;
    }

    public int getAttackRanged() {
        return attackRanged;
    }

    public void setAttackRanged(int attackRanged) {
        this.attackRanged = attackRanged;
    }

    public int getDefenceStab() {
        return defenceStab;
    }

    public void setDefenceStab(int defenceStab) {
        this.defenceStab = defenceStab;
    }

    public int getDefenceSlash() {
        return defenceSlash;
    }

    public void setDefenceSlash(int defenceSlash) {
        this.defenceSlash = defenceSlash;
    }

    public int getDefenceCrush() {
        return defenceCrush;
    }

    public void setDefenceCrush(int defenceCrush) {
        this.defenceCrush = defenceCrush;
    }

    public int getDefenceMagic() {
        return defenceMagic;
    }

    public void setDefenceMagic(int defenceMagic) {
        this.defenceMagic = defenceMagic;
    }

    public int getDefenceRanged() {
        return defenceRanged;
    }

    public void setDefenceRanged(int defenceRanged) {
        this.defenceRanged = defenceRanged;
    }

    public int getMeleeStrength() {
        return meleeStrength;
    }

    public void setMeleeStrength(int meleeStrength) {
        this.meleeStrength = meleeStrength;
    }

    public int getRangedStrength() {
        return rangedStrength;
    }

    public void setRangedStrength(int rangedStrength) {
        this.rangedStrength = rangedStrength;
    }

    public int getMagicDamage() {
        return magicDamage;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public int getPrayer() {
        return prayer;
    }

    public void setPrayer(int prayer) {
        this.prayer = prayer;
    }

    public void setAllStats(int attackStab, int attackSlash, int attackCrush,
                            int attackMagic, int attackRanged, int defenceStab,
                            int defenceSlash, int defenceCrush, int defenceMagic,
                            int defenceRanged, int meleeStrength, int rangedStrength,
                            int magicDamage, int prayer) {
        this.attackStab = attackStab; this.attackSlash = attackSlash;
        this.attackCrush = attackCrush; this.attackMagic = attackMagic;
        this.attackRanged = attackRanged; this.defenceStab = defenceStab;
        this.defenceSlash = defenceSlash; this.defenceCrush = defenceCrush;
        this.defenceMagic = defenceMagic; this.defenceRanged = defenceRanged;
        this.meleeStrength = meleeStrength; this.rangedStrength = rangedStrength;
        this.magicDamage = magicDamage; this.prayer = prayer;
    }
}
