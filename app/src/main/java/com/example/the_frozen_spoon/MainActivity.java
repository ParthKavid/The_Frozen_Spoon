package com.example.the_frozen_spoon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.the_frozen_spoon.db.customerDB;
import com.example.the_frozen_spoon.db.orderDetailDB;
import com.example.the_frozen_spoon.db.productDB;

public class MainActivity extends AppCompatActivity {


    customerDB customerdb;
    productDB productdb;
    orderDetailDB orderdetaildb;
    EditText txt_firstname;
    EditText txt_lastname;
    EditText txt_email_id;
    EditText txt_phone_no;
    EditText txt_psw;
    EditText txt_confirm_psw;
    Button btn_signup;
    TextView lbl_loginLink;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customerdb = new customerDB(MainActivity.this);
        productdb = new productDB(MainActivity.this);
        orderdetaildb = new orderDetailDB(MainActivity.this);

        txt_firstname = (EditText) findViewById(R.id.txt_firstname);
        txt_lastname = (EditText) findViewById(R.id.txt_lastname);
        txt_email_id = (EditText) findViewById(R.id.txt_email_id);
        txt_phone_no = (EditText) findViewById(R.id.txt_phone_no);
        txt_psw = (EditText) findViewById(R.id.txt_psw);
        txt_confirm_psw = (EditText) findViewById(R.id.txt_confirm_psw);
        btn_signup = (Button) findViewById(R.id.btn_login);
        lbl_loginLink = (TextView) findViewById(R.id.lbl_loginLink);


        customerdb.createCustomerTable();
        productdb.createProductTable();
        orderdetaildb.createOrderTable();
        insertProduct();


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Log.i("test",txt_psw.getText().toString() + " "+txt_confirm_psw.getText().toString());
                    //if(txt_psw.getText().toString() == txt_confirm_psw.getText().toString())
                    //{
                        customerdb.addNewCustomer(txt_firstname.getText().toString(),txt_lastname.getText().toString(),txt_email_id.getText().toString(),txt_phone_no.getText().toString(),txt_psw.getText().toString(), txt_confirm_psw.getText().toString());
                        Toast.makeText(getApplicationContext(),"Registration successful.",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    //}
                   // else{
                     //  Toast.makeText(getApplicationContext(),"Password not match.",Toast.LENGTH_LONG).show();
                   // }


                }catch(Exception e){
                    Log.i("test",e.getMessage());
                }
            }
        });

        lbl_loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);

                //setContentView(R.layout.login_page);
            }
        });
    }


    public void insertProduct()
    {
        productdb.addNewProduct("Walnut Ice Cream","$5.99",R.drawable.ice_1,"cups heavy whipping cream,  white sugar, walnut halves, maple syrup, pinch salt, vanilla extract.");
        productdb.addNewProduct("Farki Natural ","$11.84",R.drawable.ice_2,"Shiratamako, sugar, potato starch and ice cream. ");
        productdb.addNewProduct("Kind ice cream","$10.25",R.drawable.ice_3,":  sweetened condensed milk, eggs, vanilla extract, white sugar .");
        productdb.addNewProduct("Peanut butter and honeycomb ","$14.39",R.drawable.ice_4,"Vanilla ice-cream , Chocolate honeycomb bars, Cruncy peanut butter, chocolate ice-cream.");
        productdb.addNewProduct("Chocolate and honeycomb","$15.99",R.drawable.ice_5,"A littler groundnut or vegetable oil, golden syrup,caster sugar, bicarbonate of soda,dark 70% chocolate,tub chocolate,vanilla ice cream. ice cream");
        productdb.addNewProduct("Mermaid ice cream","$29.99",R.drawable.ice_6,"Heavy cream, vanilla extract,sweetened condensed milk, neon blue gel food coloring ");
        productdb.addNewProduct("Rainbow candy ice-cream","$26.59",R.drawable.ice_7,"vanilla ice-cream , shortbread cream biscuits, rainbow nerds, block dark chocolate, vegetable oil.");
        productdb.addNewProduct("Baileys Sundae","$14.99",R.drawable.ice_8," Baileys original irish cream , vanilla ice cream , chocolate- covered pretzels to garnishing.");
        productdb.addNewProduct("Thandai Ice cream","$19.99",R.drawable.ice_9," Nestle Milkmaid , 3- peppercorns, fennel seeds , almonds , poppy seed.");
        productdb.addNewProduct("Rocky Road ","$20.99",R.drawable.ice_10,"Milk , salt , vanilla extract , sugar.");

    }
}