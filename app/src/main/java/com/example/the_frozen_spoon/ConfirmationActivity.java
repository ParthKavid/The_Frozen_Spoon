package com.example.the_frozen_spoon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {

    TextView lbl_item_purchased;
    ImageView img_item_purchased;
    TextView lbl_purchased_price;
    TextView lbl_confirm_address;
    TextView lbl_item_price_val;
    TextView lbl_shipping_val;
    TextView lbl_confirm_total_val;
    TextView lbl_confirm_customer_name;
    Button btn_continue_shop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.confirmation_page);

        lbl_item_purchased = (TextView) findViewById(R.id.lbl_item_purchased);
        lbl_purchased_price = (TextView) findViewById(R.id.lbl_purchased_price);
        lbl_confirm_address = (TextView) findViewById(R.id.lbl_confirm_address);
        lbl_item_price_val = (TextView) findViewById(R.id.lbl_item_price_val);
        lbl_shipping_val = (TextView) findViewById(R.id.lbl_shipping_val);
        lbl_confirm_total_val = (TextView) findViewById(R.id.lbl_confirm_total_val);
        lbl_confirm_customer_name = (TextView) findViewById(R.id.lbl_confirm_customer_name);
        img_item_purchased = (ImageView) findViewById(R.id.img_item_purchased);
        btn_continue_shop = (Button) findViewById(R.id.btn_continue_shop);

        Intent intent_data = getIntent();

        int imgId = Integer.parseInt(intent_data.getStringExtra("Image"));
        String customerName = intent_data.getStringExtra("customerName");
        String totalPrice = intent_data.getStringExtra("TotalPrice");
        String itemName = intent_data.getStringExtra("ItemName");
        String address = intent_data.getStringExtra("Address");
        String itemPrice = intent_data.getStringExtra("ItemPrice");

        itemPrice = itemPrice.substring(0,itemPrice.indexOf("="));
        Toast.makeText(this, itemName, Toast.LENGTH_LONG).show();


        img_item_purchased.setImageResource(imgId);
        lbl_item_purchased.setText(itemName);
        lbl_purchased_price.setText(itemPrice);
        lbl_confirm_address.setText(address);
        lbl_item_price_val.setText(itemPrice);
        lbl_shipping_val.setText("0");
        lbl_confirm_total_val.setText("$"+totalPrice);
        lbl_confirm_customer_name.setText(customerName.toUpperCase(Locale.ROOT)+",");


        btn_continue_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmationActivity.this,ProductActivity.class);
                startActivity(i);
            }
        });

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
                Intent i = new Intent(ConfirmationActivity.this,LoginActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
