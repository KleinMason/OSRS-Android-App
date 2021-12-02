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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osrsutilities.model.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    public static final String ITEM_NAME =
            "com.example.osrsutilities.extra.ITEM_NAME";
    private Button buttonItem;
    private EditText editTextItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextItem = findViewById(R.id.editTextItem);
        buttonItem = findViewById(R.id.buttonItem);
        buttonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadItemDisplay();
            }
        });
    }

    private void loadItemDisplay() {
//        String itemName = editTextItem.getText().toString();
//        if (itemName.equals("")) {
//            Toast.makeText(this, "Enter the name of an item", Toast.LENGTH_SHORT).show();
//            return;
//        }
        Intent intent = new Intent(this, ItemDisplayActivity.class);
        startActivity(intent);
    }

}