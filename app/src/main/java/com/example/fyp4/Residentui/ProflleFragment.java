package com.example.fyp4.Residentui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
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

public class ProflleFragment extends Fragment {
    private static String TAG = "ProflleFragment";
    ImageView proImage;
    Button editPro,back;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextView name,gender,dob,address,phone,email;
    String id;
    StorageReference storageReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_profile,container,false);
        name = (TextView) root.findViewById(R.id.textViewProName);
        gender = (TextView) root.findViewById(R.id.textViewProGender);
        dob = (TextView) root.findViewById(R.id.textViewProDob);
        address = (TextView) root.findViewById(R.id.textViewProAddress);
        phone = (TextView) root.findViewById(R.id.textViewProPhone);
        email = (TextView) root.findViewById(R.id.textViewP_Email);
        editPro = (Button) root.findViewById(R.id.buttonEditProfile);
        back = (Button) root.findViewById(R.id.buttonBack);
        proImage = (ImageView) root.findViewById(R.id.imageViewProImage);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        id = auth.getCurrentUser().getUid();
        StorageReference proref = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(proImage);
            }
        });




        DocumentReference ref = firestore.collection("Resident").document(id);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    name.setText("Name: "+ value.getString("Name"));
                    gender.setText("Gender: "+ value.getString("Gender"));
                    dob.setText("Date of Birth: "+value.getString("Date of Birth"));
                    address.setText("Address: "+value.getString("Address"));
                    phone.setText("Phone: "+ value.getString("Phone"));
                    email.setText("Email: "+value.getString("Email"));
                }
                else{
                    Log.d(TAG,error.getMessage());
                }


            }
        });


        editPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileFragment ePro = new editProfileFragment();
                getActivity().getIntent().putExtra("Name",name.getText().toString());
                getActivity().getIntent().putExtra("Gender",gender.getText().toString());
                getActivity().getIntent().putExtra("Date of Birth",dob.getText().toString());
                getActivity().getIntent().putExtra("Address",address.getText().toString());
                getActivity().getIntent().putExtra("Phone",phone.getText().toString());
                getActivity().getIntent().putExtra("Email",email.getText().toString());
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,ePro).addToBackStack(ProflleFragment.class.getSimpleName()).commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController();
                HomeFragment hf = new HomeFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,hf).addToBackStack(ProflleFragment.class.getSimpleName()).commit();
            }
        });
        return root;
    }
}
