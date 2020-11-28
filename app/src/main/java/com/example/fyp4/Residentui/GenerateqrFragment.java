package com.example.fyp4.Residentui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class GenerateqrFragment extends Fragment {
    FirebaseFirestore firestore;
    DocumentReference ref;
    String id, Qname,Qgender,Qdob,Qaddress,Qphone;
    TextView name,gender,dob,address,phone;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View root = (ViewGroup)inflater.inflate(R.layout.fragment_generate_qr,container,false);
    Button btn = (Button) root.findViewById(R.id.buttonGenerateQR);
    Button btnBack = (Button) root.findViewById(R.id.backFromQR);
    final ImageView QrImage = (ImageView) root.findViewById(R.id.imageViewQR);
    firestore = FirebaseFirestore.getInstance();
    auth = FirebaseAuth.getInstance();
    id = auth.getCurrentUser().getUid();



    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ref = firestore.collection("Resident").document(id);
            QRGEncoder qrg = new QRGEncoder(ref.toString(),null, QRGContents.Type.TEXT,10);
            Bitmap bits = qrg.getBitmap();
            QrImage.setImageBitmap(bits);
        }
    });
    btnBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent((Activity)getActivity(),MainActivity.class);
            startActivity(intent);
        }
    });

        return root;
    }


}
