package com.example.restaurant;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private MenuRequest.Callback activity;
    private String category;

    public interface Callback {
        void gotMenu(ArrayList<MenuFoodItem> items);
        void gotMenuError(String message);
    }

    public MenuRequest(Context context) {
        this.context = context;
    }

    void getMenuItems(MenuRequest.Callback activity, String category) {
        this.category = category;
        this.activity = activity;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                context.getString(R.string.server_url) + category,
                null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotMenuError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray itemsJSON = response.getJSONArray("items");
            ArrayList<MenuFoodItem> menuFoodItems = new ArrayList<>();

            for (int i = 0; itemsJSON.length() > i; i++) {
                JSONObject JSON_object = itemsJSON.getJSONObject(i);
                MenuFoodItem menu_item = new MenuFoodItem(JSON_object.getString("name"),
                        JSON_object.getString("description"),
                        JSON_object.getString("image_url"),
                        JSON_object.getString("category"),
                        JSON_object.getDouble("price"));

                menuFoodItems.add(menu_item);
            }
            activity.gotMenu(menuFoodItems);
        } catch (Exception e) {
            activity.gotMenuError("Parsing error");
        }
    }
}
