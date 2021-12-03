package com.example.osrsutilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osrsutilities.model.Equipment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EquipmentFetcher {
    private final String TAG = EquipmentFetcher.class.getSimpleName();
    private static int total = 3885; //TODO: make dynamic

    private final RequestQueue mRequestQueue;
    public EquipmentFetcher(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public interface OnEquipmentDataReceivedListener {
        void onEquipmentReceived(List<Equipment> equipmentList);
        void onErrorResponse(VolleyError error);
    }

    private final String WEBAPI_BASE_URL =
            "https://api.osrsbox.com/equipment";

    public void initEquipments(final OnEquipmentDataReceivedListener listener) {
        int pageNum = 0;
        do {
            pageNum++;
            String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                    .appendQueryParameter("page", String.valueOf(pageNum)).toString();
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    response -> listener.onEquipmentReceived(jsonToEquipment(response)),
                    error -> listener.onErrorResponse(error));
            mRequestQueue.add(request);
        } while (pageNum * 25 < total);
    }

    public void fetchEquipment(final OnEquipmentDataReceivedListener listener, String pageNum) {
        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                .appendQueryParameter("page", pageNum).toString();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> listener.onEquipmentReceived(jsonToEquipment(response)),
                error -> listener.onErrorResponse(error));
        mRequestQueue.add(request);
        if (Integer.parseInt(pageNum) < 2) {
            fetchEquipment(listener, "2");
        }
    }

    private List<Equipment> jsonToEquipment(JSONObject json) {
        List<Equipment> EquipmentList = new ArrayList<>();
        try {
            total = json.getJSONObject("_meta").getInt("total");
            JSONArray EquipmentArray = json.getJSONArray("_items");
            for (int i = 0; i < EquipmentArray.length(); i++) {
                JSONObject EquipmentObj = EquipmentArray.getJSONObject(i);
                String name = EquipmentObj.getString("name");
                String slot = EquipmentObj.getJSONObject("equipment").getString("slot");
                Equipment equipment = new Equipment(name, slot);
                EquipmentList.add(equipment);
            }
        } catch (Exception e) {
            Log.e(TAG, "Field missing in the JSON data: " + e.getMessage());
        }
        return EquipmentList;
    }
}
