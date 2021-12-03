package com.example.osrsutilities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osrsutilities.model.Equipment;
import com.example.osrsutilities.model.Item;
import com.example.osrsutilities.model.Weapon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    private Button buttonItem;
    private EquipmentFetcher mEquipmentFetcher;
    private ProgressBar mLoadingProgressBar;
    private int count = 0;

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
        // Show progress bar
        mLoadingProgressBar = findViewById(R.id.loading_progress_bar);
        mLoadingProgressBar.setMax(3885); // TODO: make dynamic
        mLoadingProgressBar.setVisibility(View.VISIBLE);

        mEquipmentFetcher = new EquipmentFetcher(this);
        mEquipmentFetcher.initEquipments(mFetchListener);
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
                        if (equipment.getSlot().equals("weapon")) {
                            Weapon.weaponList.add(equipment.getName());
                        }
                        count++;
                    }
                    mLoadingProgressBar.setProgress(count);
                    if (count < 3885) return; // TODO: make dynamic
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
}