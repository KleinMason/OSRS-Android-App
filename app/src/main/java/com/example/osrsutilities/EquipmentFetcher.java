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

    public interface OnSingleEquipmentDataReceivedListener {
        void onSingleEquipmentReceived(Equipment equipment);
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
                    response -> listener.onEquipmentReceived(jsonToEquipmentList(response)),
                    listener::onErrorResponse);
            mRequestQueue.add(request);
        } while (pageNum * 25 < total);
    }

    public void fetchSingleEquipment(final OnSingleEquipmentDataReceivedListener listener, String equipmentName) {
        //https://api.osrsbox.com/equipment?where={ "name": "Bandos chestplate" }
        String search = String.format("{\"name\":\"%s\"}", equipmentName);
        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                .appendQueryParameter("where", search).toString();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> listener.onSingleEquipmentReceived(jsonToSingleEquipment(response)),
                listener::onErrorResponse);
        mRequestQueue.add(request);
    }

    private List<Equipment> jsonToEquipmentList(JSONObject json) {
        List<Equipment> EquipmentList = new ArrayList<>();
        try {
            total = json.getJSONObject("_meta").getInt("total");
            JSONArray equipmentArray = json.getJSONArray("_items");
            for (int i = 0; i < equipmentArray.length(); i++) {
                JSONObject equipmentObj = equipmentArray.getJSONObject(i);
                String name = equipmentObj.getString("name");
                String slot = equipmentObj.getJSONObject("equipment").getString("slot");
                Equipment equipment = new Equipment(name, slot);
                EquipmentList.add(equipment);
            }
        } catch (Exception e) {
            Log.e(TAG, "Field missing in the JSON data: " + e.getMessage());
        }
        return EquipmentList;
    }

    private Equipment jsonToSingleEquipment(JSONObject response) {
        Equipment equipment = new Equipment();
        try {
            JSONArray equipmentArray = response.getJSONArray("_items");
            JSONObject equipmentObj = equipmentArray.getJSONObject(0);
            JSONObject statsObj = equipmentObj.getJSONObject("equipment");
            String name = equipmentObj.getString("name");
            String icon = equipmentObj.getString("icon");
            String slot = statsObj.getString("slot");
            int attackStab = statsObj.getInt("attack_stab");
            int attackSlash = statsObj.getInt("attack_slash");
            int attackCrush = statsObj.getInt("attack_crush");
            int attackMagic = statsObj.getInt("attack_magic");
            int attackRanged = statsObj.getInt("attack_ranged");
            int defenceStab = statsObj.getInt("defence_stab");
            int defenceSlash = statsObj.getInt("defence_slash");
            int defenceCrush = statsObj.getInt("defence_crush");
            int defenceMagic = statsObj.getInt("defence_magic");
            int defenceRanged = statsObj.getInt("defence_ranged");
            int meleeStrength = statsObj.getInt("melee_strength");
            int rangedStrength = statsObj.getInt("ranged_strength");
            int magicDamage = statsObj.getInt("magic_damage");
            int prayer = statsObj.getInt("prayer");
            equipment.setName(name);
            equipment.setSlot(slot);
            equipment.setIconB64(icon);
            equipment.setAllStats(attackStab, attackSlash, attackCrush, attackMagic,
                    attackRanged, defenceStab, defenceSlash, defenceCrush, defenceMagic,
                    defenceRanged, meleeStrength, rangedStrength, magicDamage, prayer);
        } catch (Exception e) {
            Log.e(TAG, "Field missing in the JSON data: " + e.getMessage());
        }
        return equipment;
    }
}
