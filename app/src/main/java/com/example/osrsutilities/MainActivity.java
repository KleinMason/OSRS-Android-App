package com.example.osrsutilities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.osrsutilities.model.Equipment;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private Button buttonItem;
    private EquipmentFetcher mEquipmentFetcher;
    private ProgressBar mLoadingProgressBar;
    private int count = 0;

    private enum Slots {HEAD, CAPE, NECK, AMMO, WEAPON, SHIELD, BODY, LEGS, HANDS, FEET, RING, TWOHANDED}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonItem = findViewById(R.id.buttonItem);
        buttonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadEquipmentDisplay();
            }
        });
        mLoadingProgressBar = findViewById(R.id.loading_progress_bar);
        if (!Equipment.listsCreated) {
            // Show progress bar
            mLoadingProgressBar = findViewById(R.id.loading_progress_bar);
            mLoadingProgressBar.setMax(3885); // TODO: make dynamic
            mLoadingProgressBar.setVisibility(View.VISIBLE);

            mEquipmentFetcher = new EquipmentFetcher(this);
            mEquipmentFetcher.initEquipments(mFetchListener);
        }
        else {
            mLoadingProgressBar.setVisibility(View.GONE);
            buttonItem.setVisibility(View.VISIBLE);
        }
    }

    private void loadEquipmentDisplay() {
        Intent intent = new Intent(this, EquipmentDisplayActivity.class);
        startActivity(intent);
    }

    private final EquipmentFetcher.OnEquipmentDataReceivedListener mFetchListener =
            new EquipmentFetcher.OnEquipmentDataReceivedListener() {
                @Override
                public void onEquipmentReceived(List<Equipment> equipmentList) {
                    for (Equipment equipment : equipmentList) {
                        try {
                            Slots slots = Slots.valueOf(equipment.getSlot().toUpperCase(Locale.ROOT));
                            switch (slots) {
                                case HEAD:
                                    Equipment.headList.add(equipment.getName());
                                    break;
                                case CAPE:
                                    Equipment.capeList.add(equipment.getName());
                                    break;
                                case NECK:
                                    Equipment.neckList.add(equipment.getName());
                                    break;
                                case AMMO:
                                    Equipment.ammoList.add(equipment.getName());
                                    break;
                                case WEAPON:
                                    Equipment.weaponList.add(equipment.getName());
                                    break;
                                case SHIELD:
                                    Equipment.shieldList.add(equipment.getName());
                                    break;
                                case BODY:
                                    Equipment.bodyList.add(equipment.getName());
                                    break;
                                case LEGS:
                                    Equipment.legsList.add(equipment.getName());
                                    break;
                                case HANDS:
                                    Equipment.handsList.add(equipment.getName());
                                    break;
                                case FEET:
                                    Equipment.feetList.add(equipment.getName());
                                    break;
                                case RING:
                                    Equipment.ringList.add(equipment.getName());
                                default:
                                    break;
//   private enum Slots {HEAD, CAPE, NECK, AMMO, WEAPON, SHIELD, BODY, LEGS, HANDS, FEET, RING, TWOHANDED}
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "No Slots of type: " + e.getMessage());
                        }
                        count++;
                    }
                    mLoadingProgressBar.setProgress(count);
                    if (count < 3885) return; // TODO: make dynamic
                    Equipment.listsCreated = true;
                    // Hide progress bar
                    mLoadingProgressBar.setVisibility(View.GONE);
                    buttonItem.setVisibility(View.VISIBLE);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            "Error loading Equipments. Try again later.",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    mLoadingProgressBar.setVisibility(View.GONE);
                }
            };

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