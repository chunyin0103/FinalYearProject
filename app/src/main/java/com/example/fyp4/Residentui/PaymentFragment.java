package com.example.fyp4.Residentui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PaymentFragment extends Fragment {
    Context context;
    TextView date,name, price1,price2,price3,total_amount;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String id;
    Calendar cal;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_payment,container,false);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String currentDate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(cal.getTime());
        String currentTime = format.format(cal.getTime());
        date = (TextView) root.findViewById(R.id.textViewPaymentDate);
        name = (TextView) root.findViewById(R.id.textViewUserName);
        price1 = (TextView) root.findViewById(R.id.textViewprice1);
        price2 = (TextView) root.findViewById(R.id.textViewprice2);
        price3 = (TextView) root.findViewById(R.id.textViewprice3);
        total_amount = (TextView) root.findViewById(R.id.textViewprice4);

        price1.setText("200");
        price2.setText("200");
        price3.setText("300");
        total_amount.setText("700");

        date.setText(currentDate+" "+currentTime);
        DocumentReference ref = firestore.collection("Resident").document(id);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText("Name: " +value.getString("Name"));
            }
        });

        Button btnPay = (Button) root.findViewById(R.id.buttonPay);
        Button btnCancel = (Button) root.findViewById(R.id.buttonCancel);



        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),paypalPayment.class);
                startActivity(intent);
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment home = new HomeFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,home).addToBackStack(PaymentFragment.class.getSimpleName()).commit();

            }
        });
        return root;
    }
}
