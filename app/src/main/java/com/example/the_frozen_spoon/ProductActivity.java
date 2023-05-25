package com.example.the_frozen_spoon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_frozen_spoon.db.productDB;
import com.example.the_frozen_spoon.dbdao.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements MyAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> myDataset;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("test","ProActivity");
        setContentView(R.layout.productlist_page);

        myDataset = new ArrayList<Product>();

        productDB pdb = new productDB(ProductActivity.this);

        List<Product> productList = pdb.getAllProducts();

        for (Product item : productList) {
            myDataset.add(item);
        }

        recyclerView = findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        MyAdapter mAdapter = new MyAdapter(myDataset,this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ProductActivity.this,ProductDetailActivity.class);
        Product selectedData = myDataset.get(position);//.toString();


        intent.putExtra("ProductData", selectedData);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                // do your code
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent i = new Intent(ProductActivity.this,LoginActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
