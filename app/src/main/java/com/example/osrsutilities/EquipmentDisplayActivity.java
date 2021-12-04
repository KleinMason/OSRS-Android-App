package com.example.osrsutilities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.osrsutilities.model.Equipment;
import com.example.osrsutilities.model.Weapon;

import java.util.ArrayList;
import java.util.List;

public class EquipmentDisplayActivity extends AppCompatActivity {
    private static final String TAG = EquipmentDisplayActivity.class.getSimpleName();

    private TextView attStabTV;
    private TextView attSlashTV;

    private EquipmentFetcher mEquipmentFetcher;
    private TextView weaponTV;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_display);
        mEquipmentFetcher = new EquipmentFetcher(this);
        attStabTV = findViewById(R.id.attack_stab_text_view);
        attSlashTV = findViewById(R.id.attack_slash_text_view);
        weaponTV = findViewById(R.id.weapon_text_view);
        weaponTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: make all the code below a method
                dialog = new Dialog(EquipmentDisplayActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(650, 800);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        EquipmentDisplayActivity.this,
                        android.R.layout.simple_list_item_1,
                        Weapon.weaponList); // TODO: replace weaponList with variable
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) { }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        weaponTV.setText(adapter.getItem(position));
                        String equipmentName = weaponTV.getText().toString();
                        mEquipmentFetcher.fetchSingleEquipment(mFetchListener, equipmentName); //TODO: update method
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void textViewClicked() { } // TODO: assign all textViews

    private final EquipmentFetcher.OnSingleEquipmentDataReceivedListener mFetchListener =
            new EquipmentFetcher.OnSingleEquipmentDataReceivedListener() {
                @Override
                public void onSingleEquipmentReceived(Equipment equipment) {
                    attStabTV.setText(getString(
                            R.string.stab_text_view_with_stat,
                            String.valueOf(equipment.getAttackStab())));
                    attSlashTV.setText(getString(
                            R.string.slash_text_view_with_stat,
                            String.valueOf(equipment.getAttackSlash())));
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            "Error loading Equipment. Try again later.",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
//                    mLoadingProgressBar.setVisibility(View.GONE);
                }
            };
}