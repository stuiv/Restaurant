package com.example.restaurant;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<String> {
    private final Context context;

    public CategoriesAdapter(Context context, ArrayList<String> categories) {
        super(context, 0, categories);
        this.context = context;
    }

    @Override
    @Nullable
    public View getView(int position, View convertView, ViewGroup parent) {

        String category = getItem(position);
        if (category == null) throw new AssertionError();
        category = category.substring(0, 1).toUpperCase() + category.substring(1);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_list_item, parent, false);

        TextView nameText = convertView.findViewById(R.id.Name_food_View);
        nameText.setText(category);

        TextView indexText = convertView.findViewById(R.id.Index_food);
        indexText.setText(String.valueOf(position + 1));

        return convertView;
    }
}