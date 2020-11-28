package com.example.fyp4.staffui;

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

import java.util.zip.Inflater;

public class StaffProfileFragment extends Fragment {
    private static String TAG = "StaffProfileFragment";
    StorageReference storageReference;
    ImageView proImage;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    TextView name,gender,dob,position,email;
    Button btnEdit,btnBack;
    String id;
    //String id,Name,Gender,DOB,Postion,Email;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_staffprofile,container,false);
        name = (TextView) root.findViewById(R.id.textViewStaffName);
        gender = (TextView) root.findViewById(R.id.textViewStaffGender);
        dob = (TextView) root.findViewById(R.id.textViewStaffDob);
        position = (TextView) root.findViewById(R.id.textViewStaffPosition);
        email = (TextView) root.findViewById(R.id.textViewS_Email);
        btnEdit = (Button) root.findViewById(R.id.buttonEditStaff);
        btnBack = (Button) root.findViewById(R.id.buttonBackStaff);
        proImage = (ImageView) root.findViewById(R.id.imageViewProSImage);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        id = auth.getCurrentUser().getUid();
        DocumentReference ref = firestore.collection("Staff").document(id);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()) {
                    name.setText("Name: " + value.getString("Name"));
                    gender.setText("Gender: " + value.getString("Gender"));
                    dob.setText("Date of Birth: " + value.getString("Date of Birth"));
                    position.setText("Position: " + value.getString("Position"));
                    email.setText("Email: " + value.getString("Email"));
                }
                else{
                    Log.d(TAG,error.getMessage());
                }
            }
        });

        StorageReference proref = storageReference.child("staff/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(proImage);
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStaffFragment edit = new editStaffFragment();
                getActivity().getIntent().putExtra("Name",name.getText().toString());
                getActivity().getIntent().putExtra("Gender",gender.getText().toString());
                getActivity().getIntent().putExtra("Date of Birth",dob.getText().toString());
                getActivity().getIntent().putExtra("Position",position.getText().toString());
                getActivity().getIntent().putExtra("Email",email.getText().toString());
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,edit).addToBackStack(StaffProfileFragment.class.getSimpleName()).commit();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home2Fragment home = new Home2Fragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,home).addToBackStack(StaffProfileFragment.class.getSimpleName()).commit();
            }
        });





        return root;
    }
}
