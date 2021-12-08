package com.example.osrsutilities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.osrsutilities.model.Equipment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class EquipmentDisplayActivity extends AppCompatActivity {
    private static final String TAG = EquipmentDisplayActivity.class.getSimpleName();

    private TextView attackStabTV;
    private TextView attackSlashTV;
    private TextView attackCrushTV;
    private TextView attackMagicTV;
    private TextView attackRangedTV;
    private TextView defenceStabTV;
    private TextView defenceSlashTV;
    private TextView defenceCrushTV;
    private TextView defenceMagicTV;
    private TextView defenceRangedTV;
    private TextView meleeStrengthTV;
    private TextView rangedStrengthTV;
    private TextView magicDamageTV;
    private TextView prayerTV;

    private int totalAttackStab;
    private int totalAttackSlash;
    private int totalAttackCrush;
    private int totalAttackMagic;
    private int totalAttackRanged;
    private int totalDefenceStab;
    private int totalDefenceSlash;
    private int totalDefenceCrush;
    private int totalDefenceMagic;
    private int totalDefenceRanged;
    private int totalMeleeStrength;
    private int totalRangedStrength;
    private int totalMagicDamage;
    private int totalPrayer;

    private TextView headTV;
    private TextView capeTV;
    private TextView neckTV;
    private TextView ammoTV;
    private TextView weaponTV;
    private TextView shieldTV;
    private TextView bodyTV;
    private TextView legsTV;
    private TextView handsTV;
    private TextView feetTV;
    private TextView ringTV;

    private Equipment head;
    private Equipment cape;
    private Equipment neck;
    private Equipment ammo;
    private Equipment weapon;
    private Equipment shield;
    private Equipment body;
    private Equipment legs;
    private Equipment hands;
    private Equipment feet;
    private Equipment ring;

    private EquipmentFetcher mEquipmentFetcher;
    private Dialog dialog;

    private enum Slots {HEAD, CAPE, NECK, AMMO, WEAPON, SHIELD, BODY, LEGS, HANDS, FEET, RING, TWOHANDED}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_display);
        mEquipmentFetcher = new EquipmentFetcher(this);
        assignTextViews();
        setTotalStatVariables();
        setEquipmentObjects();

        headTV.setOnClickListener(v -> showSearchableSpinner(headTV, Equipment.headList));
        capeTV.setOnClickListener(v -> showSearchableSpinner(capeTV, Equipment.capeList));
        neckTV.setOnClickListener(v -> showSearchableSpinner(neckTV, Equipment.neckList));
        ammoTV.setOnClickListener(v -> showSearchableSpinner(ammoTV, Equipment.ammoList));
        weaponTV.setOnClickListener(v -> showSearchableSpinner(weaponTV, Equipment.weaponList));
        shieldTV.setOnClickListener(v -> showSearchableSpinner(shieldTV, Equipment.shieldList));
        bodyTV.setOnClickListener(v -> showSearchableSpinner(bodyTV, Equipment.bodyList));
        legsTV.setOnClickListener(v -> showSearchableSpinner(legsTV, Equipment.legsList));
        handsTV.setOnClickListener(v -> showSearchableSpinner(handsTV, Equipment.handsList));
        feetTV.setOnClickListener(v -> showSearchableSpinner(feetTV, Equipment.feetList));
        ringTV.setOnClickListener(v -> showSearchableSpinner(ringTV, Equipment.ringList));
    }

    private void showSearchableSpinner(TextView tv, List<String> list) {
        dialog = new Dialog(EquipmentDisplayActivity.this);
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        dialog.getWindow().setLayout(650, 800);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        EditText editText = dialog.findViewById(R.id.edit_text);
        ListView listView = dialog.findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                EquipmentDisplayActivity.this,
                android.R.layout.simple_list_item_1,
                list);
        listView.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            tv.setText(adapter.getItem(position));
            String equipmentName = tv.getText().toString();
            mEquipmentFetcher.fetchSingleEquipment(mFetchListener, equipmentName);
            dialog.dismiss();
        });
    }

    private final EquipmentFetcher.OnSingleEquipmentDataReceivedListener mFetchListener =
            new EquipmentFetcher.OnSingleEquipmentDataReceivedListener() {
                @Override
                public void onSingleEquipmentReceived(Equipment equipment) {
                    Slots slots = Slots.valueOf(equipment.getSlot().toUpperCase(Locale.ROOT));

                    try {
                        switch (slots) {
                            case HEAD:
                                subtractFromTotalValues(head);
                                head = equipment;
                                addToTotalValues(head);
                                break;
                            case CAPE:
                                subtractFromTotalValues(cape);
                                cape = equipment;
                                addToTotalValues(equipment);
                                break;
                            case NECK:
                                subtractFromTotalValues(neck);
                                neck = equipment;
                                addToTotalValues(equipment);
                                break;
                            case AMMO:
                                subtractFromTotalValues(ammo);
                                ammo = equipment;
                                addToTotalValues(equipment);
                                break;
                            case WEAPON:
                                subtractFromTotalValues(weapon);
                                weapon = equipment;
                                addToTotalValues(equipment);
                                break;
                            case SHIELD:
                                subtractFromTotalValues(shield);
                                shield = equipment;
                                addToTotalValues(equipment);
                                break;
                            case BODY:
                                subtractFromTotalValues(body);
                                body = equipment;
                                addToTotalValues(equipment);
                                break;
                            case LEGS:
                                subtractFromTotalValues(legs);
                                legs = equipment;
                                addToTotalValues(equipment);
                                break;
                            case HANDS:
                                subtractFromTotalValues(hands);
                                hands = equipment;
                                addToTotalValues(equipment);
                                break;
                            case FEET:
                                subtractFromTotalValues(feet);
                                feet = equipment;
                                addToTotalValues(equipment);
                                break;
                            case RING:
                                subtractFromTotalValues(ring);
                                ring = equipment;
                                addToTotalValues(equipment);
                            default:
                                break;
//   private enum Slots {HEAD, CAPE, NECK, AMMO, WEAPON, SHIELD, BODY, LEGS, HANDS, FEET, RING, TWOHANDED}
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "No Slots of type: " + e.getMessage());
                    }
                    setStatTextViews();
                }

                private void setStatTextViews() {
                    attackStabTV.setText(getString(
                            R.string.stab_attack_stat_text_view,
                            String.valueOf(totalAttackStab)));
                    attackSlashTV.setText(getString(
                            R.string.slash_attack_stat_text_view,
                            String.valueOf(totalAttackSlash)));
                    attackCrushTV.setText(getString(
                            R.string.crush_attack_stat_text_view,
                            String.valueOf(totalAttackCrush)));
                    attackMagicTV.setText(getString(
                            R.string.magic_attack_stat_text_view,
                            String.valueOf(totalAttackMagic)));
                    attackRangedTV.setText(getString(
                            R.string.ranged_attack_stat_text_view,
                            String.valueOf(totalAttackRanged)));
                    defenceStabTV.setText(getString(
                            R.string.stab_defence_stat_text_view,
                            String.valueOf(totalDefenceStab)));
                    defenceSlashTV.setText(getString(
                            R.string.slash_defence_stat_text_view,
                            String.valueOf(totalDefenceSlash)));
                    defenceCrushTV.setText(getString(
                            R.string.crush_defence_stat_text_view,
                            String.valueOf(totalDefenceCrush)));
                    defenceMagicTV.setText(getString(
                            R.string.magic_defence_stat_text_view,
                            String.valueOf(totalDefenceMagic)));
                    defenceRangedTV.setText(getString(
                            R.string.ranged_defence_stat_text_view,
                            String.valueOf(totalDefenceRanged)));
                    meleeStrengthTV.setText(getString(
                            R.string.melee_strength_stat_text_view,
                            String.valueOf(totalMeleeStrength)));
                    rangedStrengthTV.setText(getString(
                            R.string.ranged_strength_stat_text_view,
                            String.valueOf(totalRangedStrength)));
                    magicDamageTV.setText(getString(
                            R.string.magic_defence_stat_text_view,
                            String.valueOf(totalMagicDamage)));
                    prayerTV.setText(getString(
                            R.string.prayer_stat_text_view,
                            String.valueOf(totalPrayer)));
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            "Error loading Equipment. Try again later.",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    Log.e(TAG, "Error: " + error.getMessage());
//                    mLoadingProgressBar.setVisibility(View.GONE);
                }
            };

    private void assignTextViews() {
        attackStabTV = findViewById(R.id.attack_stab_text_view);
        attackSlashTV = findViewById(R.id.attack_slash_text_view);
        attackCrushTV = findViewById(R.id.attack_crush_text_view);
        attackMagicTV = findViewById(R.id.attack_magic_text_view);
        attackRangedTV = findViewById(R.id.attack_ranged_text_view);
        defenceStabTV = findViewById(R.id.defence_stab_text_view);
        defenceSlashTV = findViewById(R.id.defence_slash_text_view);
        defenceCrushTV = findViewById(R.id.defence_crush_text_view);
        defenceMagicTV = findViewById(R.id.defence_magic_text_view);
        defenceRangedTV = findViewById(R.id.defence_ranged_text_view);
        meleeStrengthTV = findViewById(R.id.melee_strength_text_view);
        rangedStrengthTV = findViewById(R.id.ranged_strength_text_view);
        magicDamageTV = findViewById(R.id.magic_damage_text_view);
        prayerTV = findViewById(R.id.prayer_text_view);
        headTV = findViewById(R.id.head_text_view);
        capeTV = findViewById(R.id.cape_text_view);
        neckTV = findViewById(R.id.neck_text_view);
        ammoTV = findViewById(R.id.ammo_text_view);
        weaponTV = findViewById(R.id.weapon_text_view);
        shieldTV = findViewById(R.id.shield_text_view);
        bodyTV = findViewById(R.id.body_text_view);
        legsTV = findViewById(R.id.legs_text_view);
        handsTV = findViewById(R.id.hands_text_view);
        feetTV = findViewById(R.id.feet_text_view);
        ringTV = findViewById(R.id.ring_text_view);
    }

    private void setTotalStatVariables() {
        totalAttackStab = 0;
        totalAttackSlash = 0;
        totalAttackCrush = 0;
        totalAttackMagic = 0;
        totalAttackRanged = 0;
        totalDefenceStab = 0;
        totalDefenceSlash = 0;
        totalDefenceCrush = 0;
        totalDefenceMagic = 0;
        totalDefenceRanged = 0;
        totalMeleeStrength = 0;
        totalRangedStrength = 0;
        totalMagicDamage = 0;
        totalPrayer = 0;
    }

    private void setEquipmentObjects() {
        head = new Equipment("head");
        cape = new Equipment("cape");
        neck = new Equipment("neck");
        ammo = new Equipment("ammo");
        weapon = new Equipment("weapon");
        shield = new Equipment("shield");
        body = new Equipment("body");
        legs = new Equipment("legs");
        hands = new Equipment("hands");
        feet = new Equipment("feet");
        ring = new Equipment("ring");
    }

    private void addToTotalValues(Equipment equipment) {
        totalAttackStab += equipment.getAttackStab();
        totalAttackSlash += equipment.getAttackSlash();
        totalAttackCrush += equipment.getAttackCrush();
        totalAttackMagic += equipment.getAttackMagic();
        totalAttackRanged += equipment.getAttackRanged();
        totalDefenceStab += equipment.getDefenceStab();
        totalDefenceSlash += equipment.getDefenceSlash();
        totalDefenceCrush += equipment.getDefenceCrush();
        totalDefenceMagic += equipment.getDefenceMagic();
        totalDefenceRanged += equipment.getDefenceRanged();
        totalMeleeStrength += equipment.getMeleeStrength();
        totalRangedStrength += equipment.getRangedStrength();
        totalMagicDamage += equipment.getMagicDamage();
        totalPrayer += equipment.getPrayer();
    }

    private void subtractFromTotalValues(Equipment equipment) {
        if (equipment.getName() == null) return;
        totalAttackStab -= equipment.getAttackStab();
        totalAttackSlash -= equipment.getAttackSlash();
        totalAttackCrush -= equipment.getAttackCrush();
        totalAttackMagic -= equipment.getAttackMagic();
        totalAttackRanged -= equipment.getAttackRanged();
        totalDefenceStab -= equipment.getDefenceStab();
        totalDefenceSlash -= equipment.getDefenceSlash();
        totalDefenceCrush -= equipment.getDefenceCrush();
        totalDefenceMagic -= equipment.getDefenceMagic();
        totalDefenceRanged -= equipment.getDefenceRanged();
        totalMeleeStrength -= equipment.getMeleeStrength();
        totalRangedStrength -= equipment.getRangedStrength();
        totalMagicDamage -= equipment.getMagicDamage();
        totalPrayer -= equipment.getPrayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else{
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode
                        (AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return true;
    }
}