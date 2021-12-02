package com.example.osrsutilities;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.osrsutilities.MainActivity;
import com.example.osrsutilities.model.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemFetcher {
    private final String TAG = ItemFetcher.class.getSimpleName();
    private static int total = 3885; //TODO: make dynamic

    private final RequestQueue mRequestQueue;
    public ItemFetcher(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public interface OnItemDataReceivedListener {
        void onItemReceived(List<Item> itemList);
        void onErrorResponse(VolleyError error);
    }

    private final String WEBAPI_BASE_URL =
            "https://api.osrsbox.com/equipment";

    public void initItems(final OnItemDataReceivedListener listener) {
        int pageNum = 0;
        do {
            pageNum++;
            String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                    .appendQueryParameter("page", String.valueOf(pageNum)).toString();
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    response -> listener.onItemReceived(jsonToItem(response)),
                    error -> listener.onErrorResponse(error));
            mRequestQueue.add(request);
        } while (pageNum * 25 < total);
    }

    public void fetchItem(final OnItemDataReceivedListener listener, String pageNum) {
        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                .appendQueryParameter("page", pageNum).toString();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> listener.onItemReceived(jsonToItem(response)),
                error -> listener.onErrorResponse(error));
        mRequestQueue.add(request);
        if (Integer.parseInt(pageNum) < 2) {
            fetchItem(listener, "2");
        }
    }

    private List<Item> jsonToItem(JSONObject json) {
        List<Item> itemList = new ArrayList<>();
        try {
            total = json.getJSONObject("_meta").getInt("total");
            JSONArray itemArray = json.getJSONArray("_items");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject itemObj = itemArray.getJSONObject(i);
                Item item = new Item(itemObj.getString("id"));
                item.setName(itemObj.getString("name"));
                item.setExamine(itemObj.getString("examine"));
                JSONObject equipment = itemObj.getJSONObject("equipment");
                item.setSlot(equipment.getString("slot"));
                itemList.add(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "Field missing in the JSON data: " + e.getMessage());
        }
        return itemList;
    }
}
