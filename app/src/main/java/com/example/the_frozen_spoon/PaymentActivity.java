package com.example.the_frozen_spoon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.the_frozen_spoon.db.customerDB;
import com.example.the_frozen_spoon.db.orderDetailDB;
import com.example.the_frozen_spoon.dbdao.Customer;
import com.example.the_frozen_spoon.dbdao.Product;

import java.util.Timer;
import java.util.TimerTask;

public class PaymentActivity extends AppCompatActivity {
    RadioGroup rGroup;
    RadioButton rbCredit;
    RadioButton rbDebit;
    RadioButton rbCash;
    TableRow trCardDetail;
    EditText edittxtFirstName;
    EditText edittxtLastName;
    EditText edittxtAddress;
    EditText edittxtPhone;
    EditText edittxtCardNumber;
    EditText edittxtCVV;
    TextView txtItemName;
    TextView txtItemPrice;
    TableLayout tblCardDetail;
    TextView txtHidTotalPrice;
    int customer_id = 0;
    String customerName ="";
    String img_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        tblCardDetail = (TableLayout) findViewById(R.id.tblCardDetail);
        edittxtFirstName = (EditText) findViewById(R.id.edittxtFirstName);
        edittxtLastName = (EditText) findViewById(R.id.edittxtLastName);
        edittxtAddress = (EditText) findViewById(R.id.edittxtAddress);
        edittxtPhone = (EditText) findViewById(R.id.edittxtPhone);
        edittxtCardNumber = (EditText) findViewById(R.id.edittxtCardNumber);
        edittxtCVV = (EditText) findViewById(R.id.edittxtCVV);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        trCardDetail = (TableRow) findViewById(R.id.trCardDetail);
        txtItemName = (TextView) findViewById(R.id.txtItemName);
        txtItemPrice = (TextView) findViewById(R.id.txtItemPrice);
        txtHidTotalPrice = (TextView) findViewById(R.id.txtHidTotalPrice);

        Product i = (Product)getIntent().getSerializableExtra("Item");
        Intent intent_price = getIntent();
        String totalPrice = intent_price.getStringExtra("TotalPrice");
         img_id = i.getImg().toString();


        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        customer_id = sh.getInt("customer_id", 0);

        customerDB cdb = new customerDB(this);
        Customer objCustomer =  cdb.getCustomerById(customer_id);

        customerName = objCustomer.getFirstName();

        Log.i("test",totalPrice);


        edittxtFirstName.setText(objCustomer.getFirstName());
        edittxtLastName.setText(objCustomer.getLastName());
        txtItemName.setText(i.getProduct_name());
        txtItemPrice.setText(i.getPrice());
        txtHidTotalPrice.setText(totalPrice);
        edittxtPhone.setText(objCustomer.getPhoneNo());
        rbCash = (RadioButton) findViewById(R.id.rbCash);
        rbCash.setChecked(true);
        rbCash.performClick();

    }

    public void onRadioBtnClick(View v){
        boolean isChecked = ((RadioButton) v).isChecked();

        Log.i("test", String.valueOf(R.id.rbCash));


        switch (v.getId())
        {
            case R.id.rbCash:
                if(isChecked)
                {
                    trCardDetail.setVisibility(View.GONE);
                }
                break;
            default:
                trCardDetail.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void onPayClick(View v) {

        try {
            if (edittxtFirstName.getText().toString().isEmpty() || edittxtLastName.getText().toString().isEmpty() || edittxtAddress.getText().toString().isEmpty() || edittxtPhone.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter All Detail", Toast.LENGTH_LONG).show();
                return;
            }

//            customerDB objCustomerDB = new customerDB(this);
//            objCustomerDB.addNewCustomer(edittxtFirstName.getText().toString(), edittxtLastName.getText().toString(), edittxtAddress.getText().toString(), edittxtPhone.getText().toString());


            int cardTypeid = rGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(cardTypeid);
            String cardType = radioButton.getText().toString();


            orderDetailDB orderdetaildb = new orderDetailDB(this);
            orderdetaildb.addNewOrder(customer_id,txtItemName.getText().toString(), txtHidTotalPrice.getText().toString(), edittxtFirstName.getText().toString() + " " + edittxtLastName.getText().toString(), edittxtCardNumber.getText().toString(), edittxtCVV.getText().toString(), cardType,edittxtAddress.getText().toString());


            Toast.makeText(this, "ORDER CONFIRMED. Thank you.. ", Toast.LENGTH_LONG).show();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent i = new Intent(PaymentActivity.this, ConfirmationActivity.class);

                    Bundle extras = new Bundle();
                    extras.putString("customerName",edittxtFirstName.getText().toString());
                    extras.putString("ItemPrice",txtItemPrice.getText().toString());
                    extras.putString("TotalPrice",txtHidTotalPrice.getText().toString());
                    extras.putString("ItemName",txtItemName.getText().toString());
                    extras.putString("Address",edittxtAddress.getText().toString());
                    extras.putString("Image",img_id);
                    i.putExtras(extras);

                    startActivity(i);
                }
            }, 2000);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


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
                Intent i = new Intent(PaymentActivity.this,LoginActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
