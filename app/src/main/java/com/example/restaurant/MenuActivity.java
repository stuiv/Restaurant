package com.example.restaurant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuRequest.Callback {

    GridView menuListView;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        this.category = category;

        menuListView = findViewById(R.id.menuListView);
        menuListView.setOnItemClickListener(new OnMenuClickListener());

        new MenuRequest(this).getMenuItems(this, category);
        setTitle(category.substring(0, 1).toUpperCase() + category.substring(1));

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    @Override
    public void gotMenu(ArrayList<MenuFoodItem> items) {
        menuListView.setAdapter(new MenuAdapter(this, items));
    }

    @Override
    public void gotMenuError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class OnMenuClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MenuFoodItem food_item = (MenuFoodItem) parent.getItemAtPosition(position);
            Intent intent = new Intent(MenuActivity.this, DetailActivity.class);
            intent.putExtra("item", food_item);
            startActivity(intent);
        }
    }

    //action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Back_Button) {
            MenuRequest request_menu = new MenuRequest(this);
            request_menu.getMenuItems(this, category);
        }
        return super.onOptionsItemSelected(item);
    }


    public class MenuAdapter extends ArrayAdapter {
        Context context;
        public MenuAdapter(Context context, ArrayList<MenuFoodItem> items) {
            super(context, 0, items);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MenuFoodItem item = (MenuFoodItem) getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, parent, false);
            }

            TextView Name_food_View = convertView.findViewById(R.id.Name_food_View);
            Name_food_View.setText(item.getName());

            TextView description_View = convertView.findViewById(R.id.description_View);
            description_View.setText(item.getDescription());

            TextView priceTextView = convertView.findViewById(R.id.priceTextView);
            String price = String.format("€%.2f", item.getPrice());
            priceTextView.setText(price);

            ImageView imageView = convertView.findViewById(R.id.imageView);
            Picasso.get().load(item.getImageUrl()).fit().centerCrop().into(imageView);

            return convertView;
        }
    }
}