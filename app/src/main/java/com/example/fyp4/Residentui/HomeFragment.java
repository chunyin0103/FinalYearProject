package com.example.fyp4.Residentui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.example.fyp4.staffui.checkParkingFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    ImageView announce,payment,management,contact_us,qrCode,navigate, proImage;
    TextView username;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    FirebaseAuth auth;
    String id, name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_home,container,false);

        //display current date and time
        TextView date = (TextView) root.findViewById(R.id.textViewDate);
        TextView time = (TextView) root.findViewById(R.id.textViewTime);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
        String currentTime = format.format(cal.getTime());
        date.setText(currentDate);
        time.setText(currentTime);

        username = (TextView) root.findViewById(R.id.textViewUser);
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        //display Username
        id = auth.getCurrentUser().getUid();
        DocumentReference ref =  firestore.collection("Resident").document(id);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    username.setText("Welcome " + value.getString("Name"));
                }catch (NullPointerException ignored){}
            }
        });
        proImage = (ImageView) root.findViewById(R.id.imageViewpM);
        StorageReference proref = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(proImage);
            }
        });


        //ImageView profile = (ImageView) root.findViewById(R.id.imageViewpM);
        announce = (ImageView) root.findViewById(R.id.announcement);
        payment = (ImageView) root.findViewById(R.id.payment);
        management = (ImageView) root.findViewById(R.id.securityGuard);
        contact_us = (ImageView) root.findViewById(R.id.contactus);
        qrCode = (ImageView) root.findViewById(R.id.QRcode);



        proImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProflleFragment pro = new ProflleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,pro).addToBackStack(HomeFragment.class.getSimpleName()).commit();
            }
        });
        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announceFragment anounceFra = new announceFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,anounceFra).addToBackStack(HomeFragment.class.getSimpleName()).commit();
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment pay = new PaymentFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,pay).addToBackStack(HomeFragment.class.getSimpleName()).commit();
            }
        });
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              GenerateqrFragment qr = new GenerateqrFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,qr).addToBackStack(HomeFragment.class.getSimpleName()).commit();
            }
        });
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementFragment manage = new managementFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,manage).addToBackStack(HomeFragment.class.getSimpleName()).commit();
            }
        });
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactUsFragment contact = new contactUsFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,contact).addToBackStack(HomeFragment.class.getSimpleName()).commit();
            }
        });

        return root;
    }
}
