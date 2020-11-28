package com.example.fyp4.Residentui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.FragmentManager;


import com.example.fyp4.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetail extends AppCompatActivity {
    private Activity activity;
    TextView id,amount,status;
    Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_paymentdetail);

        id = (TextView)findViewById(R.id.textID);
        amount = (TextView)findViewById(R.id.textAmount);
        status = (TextView) findViewById(R.id.textStatus);
        back = (Button) findViewById(R.id.buttonback);
        Intent intent = getIntent();
        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetail(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //HomeFragment home = new HomeFragment();
              Intent intent = new Intent(getApplicationContext(),MainActivity.class);
              startActivity(intent);
            }
        });

    }

    private void showDetail(JSONObject response, String paymentAmount) {
        try {
            id.setText(response.getString("id"));
            amount.setText("MYR"+paymentAmount);
            status.setText(response.getString("state"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
