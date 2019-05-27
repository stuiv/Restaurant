package com.example.restaurant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Dish");

        Intent intent;
        intent = getIntent();
        MenuFoodItem menu_Item = (MenuFoodItem) intent.getSerializableExtra("item");

        // Set Name of dish
        TextView nameDishView = findViewById(R.id.Name_food_View);
        nameDishView.setText(menu_Item.getName());

        // Set description
        TextView descriptionView = findViewById(R.id.description_View);
        descriptionView.setText(menu_Item.getDescription());

        // Set category
        TextView categoryView = findViewById(R.id.categoryTextView);
        String category = menu_Item.getCategory();
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        categoryView.setText(category);

        // Set image
        final ImageView imageView = findViewById(R.id.imageView);
        String imageUri = menu_Item.getImageUrl();
        Picasso.get().load(imageUri).fit().centerCrop().into(imageView);

        // Set Price
        TextView priceTextView = findViewById(R.id.priceTextView);
        @SuppressLint("DefaultLocale") String price = String.format("€%.2f", menu_Item.getPrice());
        priceTextView.setText(price);

        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    }
}