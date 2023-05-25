package com.example.the_frozen_spoon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.the_frozen_spoon.db.customerDB;
import com.example.the_frozen_spoon.dbdao.Customer;

public class LoginActivity extends AppCompatActivity {

    EditText txt_email_id;
    EditText txt_psw;
    Button btn_login;
    TextView lbl_error;
    TextView lbl_registrationLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        txt_email_id = (EditText) findViewById(R.id.txt_email_id);
        txt_psw = (EditText) findViewById(R.id.txt_psw);
        lbl_error = (TextView) findViewById(R.id.lbl_error);
        lbl_registrationLink = (TextView) findViewById(R.id.lbl_registrationLink);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lbl_error.setText("");
                Log.i("test","btnLogin");
                customerDB cDB = new customerDB(LoginActivity.this);
                Customer custData = cDB.getCustomerByEmail_Psw(txt_email_id.getText().toString().trim(),txt_psw.getText().toString());

                if(String.valueOf(custData) == "null"){
                    lbl_error.setText("Email or Password is wrong. Please Try again");
                    Log.i("test","Email or Password is wrong. Please Try again");
                }
                else
                {
                    // Storing data into SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putInt("customer_id", custData.getId());
                    myEdit.commit();

                    Intent intent = new Intent(LoginActivity.this,ProductActivity.class);
                    startActivity(intent);
                }
            }
        });


       lbl_registrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"signup Link",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }




}
