package com.example.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    private ListView categories_List_View;
    private ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        CategoriesRequest Request = new CategoriesRequest(this);
        Request.getCategories(this);

        categories_List_View = findViewById(R.id.categoriesListView);
    }

    @Override
    public void gotCategories(ArrayList<String> categories) {

        CategoriesAdapter ca = new CategoriesAdapter(this, categories);
        categories_List_View.setAdapter(ca);
        categories_List_View.setOnItemClickListener(new OnCategoryClickListener());
    }

    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // selected item activity
    private class OnCategoryClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String category = parent.getItemAtPosition(position).toString();
            Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Button activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.Back_Button) {
            CategoriesRequest cr = new CategoriesRequest(this);
            cr.getCategories(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
