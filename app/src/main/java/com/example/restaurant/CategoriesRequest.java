package com.example.restaurant;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;
    private VolleyError error_response;

    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    public CategoriesRequest(Context context) {
        this.context = context;
    }

    void getCategories(Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);
        this.activity = activity;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                context.getString(R.string.cat_url),
                null,
                this,
                this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotCategoriesError(this.error_response.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray categories_JSON = response.getJSONArray("categories");
            ArrayList<String> categories = new ArrayList<>();

            for (int i = 0; i < categories_JSON.length(); i++)
                categories.add(categories_JSON.getString(i));
            activity.gotCategories(categories);

        } catch (JSONException e) {
            activity.gotCategoriesError("Parsing error");
        }
    }
}
