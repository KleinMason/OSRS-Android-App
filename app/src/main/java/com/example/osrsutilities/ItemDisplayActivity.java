package com.example.osrsutilities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.osrsutilities.model.Item;

import java.util.List;

public class ItemDisplayActivity extends AppCompatActivity {
    private static final String TAG = ItemDisplayActivity.class.getSimpleName();
    private LinearLayout mItemLayoutContainer;
    private ItemFetcher mItemFetcher;
    private ProgressBar mLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        mItemLayoutContainer = findViewById(R.id.item_layout);

        // Show progress bar
        mLoadingProgressBar = findViewById(R.id.loading_progress_bar);
        mLoadingProgressBar.setVisibility(View.VISIBLE);

//        String itemName = getIntent().getStringExtra(MainActivity.ITEM_NAME);
        mItemFetcher = new ItemFetcher(this);
        mItemFetcher.fetchItem(mFetchListener, "1");
    }

    private final ItemFetcher.OnItemDataReceivedListener mFetchListener =
            new ItemFetcher.OnItemDataReceivedListener() {

                @Override
                public void onItemReceived(List<Item> itemList) {

                    // Hide progress bar
                    mLoadingProgressBar.setVisibility(View.GONE);

                    // Create a checkbox for each item
                    for (Item item : itemList) {
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setTextSize(24);
                        checkBox.setText(item.getName());
                        checkBox.setTag(item);
                        mItemLayoutContainer.addView(checkBox);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            "Error loading items. Try again later.",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                    mLoadingProgressBar.setVisibility(View.GONE);
                }
            };
}