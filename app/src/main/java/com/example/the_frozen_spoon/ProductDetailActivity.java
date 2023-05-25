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

import androidx.appcompat.app.AppCompatActivity;

import com.example.the_frozen_spoon.dbdao.Product;

public class ProductDetailActivity extends AppCompatActivity {
    TextView txtTitle_detail;
    TextView txtPrice_detail;
    TextView txtDesc_detail;
    TextView txtCount_detail;
    ImageView btnMinus_detail;
    ImageView btnPlus_detail;
    ImageView img_detail;
    Button btnCheckOut;
    Integer img_id;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_page2);
        txtTitle_detail = (TextView) findViewById(R.id.txtTitle_detail);
        txtPrice_detail = (TextView) findViewById(R.id.txtPrice_detail);
        txtDesc_detail = (TextView) findViewById(R.id.txtDesc_detail);
        txtCount_detail = (TextView) findViewById(R.id.txtCount_detail);
        btnMinus_detail = (ImageView) findViewById(R.id.btnMinus_detail);
        btnPlus_detail = (ImageView) findViewById(R.id.btnPlus_detail);
        btnCheckOut = (Button) findViewById(R.id.btnCheckOut);
        img_detail = (ImageView) findViewById(R.id.img_detail);
        Product p = (Product)getIntent().getSerializableExtra("ProductData");
        txtTitle_detail.setText(p.getProduct_name());
        txtPrice_detail.setText(p.getPrice());
        txtDesc_detail.setText(p.getDescription());
        txtDesc_detail.setText(p.getDescription());
        img_detail.setImageResource(p.getImg());

        img_id = p.getImg();

        btnMinus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentCount = txtCount_detail.getText().toString();
                if(Integer.parseInt(currentCount) > 0 )
                {
                    txtCount_detail.setText(String.valueOf(Integer.parseInt(currentCount) - 1));
                }

            }
        });

        btnPlus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentCount = txtCount_detail.getText().toString();
                //Log.i("test", String.valueOf((Integer.parseInt(currentCount) + 1)));
                txtCount_detail.setText(String.valueOf(Integer.parseInt(currentCount) + 1));
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Integer.parseInt(txtCount_detail.getText().toString()) == 0 )
                {
                    Toast.makeText(getApplicationContext(),"Please select atleast 1 item ",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(ProductDetailActivity.this,PaymentActivity.class);
                Product p = new Product();
                p.setProduct_name(txtTitle_detail.getText().toString());
                Float totalPrice = (Float.parseFloat(txtPrice_detail.getText().toString().replace("$","")) * Integer.parseInt(txtCount_detail.getText().toString()));
                p.setPrice(txtPrice_detail.getText().toString() + " (X "+txtCount_detail.getText().toString() +") = $" + totalPrice);
                p.setImg(img_id);

                Bundle extras = new Bundle();
                extras.putSerializable("Item",(Product) p);
                extras.putString("TotalPrice",totalPrice.toString());
                i.putExtras(extras);



                //i.putExtra("Item",(Product) p);

                //i.putExtra("TotalPrice",totalPrice);
                startActivity(i);
            }
        });
        //Log.i("test",p.toString());


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
                Intent i = new Intent(ProductDetailActivity.this,LoginActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
