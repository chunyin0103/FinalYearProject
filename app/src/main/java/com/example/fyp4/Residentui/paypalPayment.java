package com.example.fyp4.Residentui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp4.R;
import com.example.fyp4.config.Config;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class paypalPayment extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE =7171;
    public static final String TAG = "paypalPayment";
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    FirebaseFirestore firestore;

    Button btnPay,btnCancel;
    EditText Editamount;
    String amount = "";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
        firestore = FirebaseFirestore.getInstance();

        //start paypal services
        Intent intent = new Intent(this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        btnPay = (Button) findViewById(R.id.buttonPay);
        btnCancel = (Button) findViewById(R.id.cancelPayment);
        Editamount = (EditText) findViewById(R.id.editAmount);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPayment();
            }
        });

    }

    private void cancelPayment() {
        Toast.makeText(paypalPayment.this,"Payment Cancelled.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void processPayment() {
        amount = Editamount.getText().toString();
        if(amount.isEmpty()){
            Toast.makeText(paypalPayment.this,"Please Enter Amount!",Toast.LENGTH_SHORT).show();
        }else{
            PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"MYR","Total Amount",PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
            startActivityForResult(intent,PAYPAL_REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation!= null) {
                    try {
                        String paymentDetail = confirmation.toJSONObject().toString(4);
                        DocumentReference ref = firestore.collection("Payment").document();
                        Map<String,String> payment = new HashMap<>();
                        payment.put("Description","Month Payment");
                        payment.put("Status","Paid");
                        payment.put("Total Amount","700");
                        ref.set(payment).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG,"onSuccess: Payment Successful");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onFailure: "+e.toString());
                            }
                        });
                        Toast.makeText(this,"Payment Successful!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, PaymentDetail.class)
                                .putExtra("PaymentDetails", paymentDetail)
                                .putExtra("PaymentAmount", amount));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show();

        }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();

    }

}