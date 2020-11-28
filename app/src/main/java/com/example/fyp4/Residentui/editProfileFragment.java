package com.example.fyp4.Residentui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fyp4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class editProfileFragment extends Fragment {

    ImageView proImage;
    EditText name,gender,dob,address,phone,email;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    FirebaseUser user;
    String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_edit_profile,container,false);
        name = (EditText) root.findViewById(R.id.editTextProName);
        gender= (EditText) root.findViewById(R.id.editTextP_Gender);
        dob = (EditText) root.findViewById(R.id.editTextP_Dob);
        address = (EditText) root.findViewById(R.id.editTextP_Address);
        phone = (EditText) root.findViewById(R.id.editTextP_Phone);
        email = (EditText) root.findViewById(R.id.editTextP_EmailAddress);
        proImage = (ImageView) root.findViewById(R.id.imageViewProfileImage);
        Button btnSubmit = (Button) root.findViewById(R.id.buttonEPSubmit);
        Button btnCancel = (Button) root.findViewById(R.id.buttonEPCancel);
        Intent data =  getActivity().getIntent();
        String Name = data.getStringExtra("Name");
        String Gender = data.getStringExtra("Gender");
        String Dob = data.getStringExtra("Date of Birth");
        String Address = data.getStringExtra("Address");
        String Phone = data.getStringExtra("Phone");
        String Email = data.getStringExtra("Email");
        name.setText(Name);
        gender.setText(Gender);
        dob.setText(Dob);
        address.setText(Address);
        phone.setText(Phone);
        email.setText(Email);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference proref = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(proImage);
            }
        });
        user = auth.getCurrentUser();
        id = user.getUid();

        proImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opG,1000);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String eName = name.getText().toString();
                final String eGender = gender.getText().toString();
                final String eDob = dob.getText().toString();
                final String eAdd =address.getText().toString();
                final String ePhone = phone.getText().toString();
                final String Email = email.getText().toString();
                if(eName.isEmpty()||eGender.isEmpty()||eDob.isEmpty()||eAdd.isEmpty()||ePhone.isEmpty()){
                    Toast.makeText(getContext(), "Some of the details is Empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference ref = firestore.collection("Resident").document(id);
                        Map<String,Object> edit = new HashMap<>();
                        edit.put("Name",eName);
                        edit.put("Gender",eGender);
                        edit.put("Date of Birth",eDob);
                        edit.put("Address",eAdd);
                        edit.put("Phone",ePhone);
                        edit.put("Email",Email);
                        ref.update(edit);
                        Toast.makeText(getContext(),"The data is changed!",Toast.LENGTH_SHORT).show();
                        ProflleFragment pro = new ProflleFragment();

                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,pro).addToBackStack(editProfileFragment.class.getSimpleName()).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProflleFragment pro = new ProflleFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,pro).addToBackStack(editProfileFragment.class.getSimpleName()).commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri content = data.getData();
                //proImage.setImageURI(content);

                uploadImage(content);
            }
        }
    }

    private void uploadImage(Uri content) {
        //upload image to firebase
        final StorageReference fileref = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        fileref.putFile(content).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(proImage);
                    }
                });
                Toast.makeText(getActivity(),"Upload Successful",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}