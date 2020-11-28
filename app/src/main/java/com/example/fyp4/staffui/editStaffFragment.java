package com.example.fyp4.staffui;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class editStaffFragment extends Fragment {
    ImageView proImage;
    FirebaseUser user;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String Sname,Sgender,Sdob,Sposition,Semail;
    EditText name,gender,dob,position,email;
    Button btnSubmit,btnCancel;
    StorageReference storageReference;
    String id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_editstaff,container,false);

        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        id = auth.getCurrentUser().getUid();

        name = (EditText) root.findViewById(R.id.editTextStaffEDName);
        gender = (EditText) root.findViewById(R.id.editTextStaffEDGender);
        dob = (EditText) root.findViewById(R.id.editTextStaffEDDob);
        position = (EditText) root.findViewById(R.id.editTextStaffEDPosition);
        email = (EditText) root.findViewById(R.id.editTextStaffEDEmailAddress);
        proImage = (ImageView) root.findViewById(R.id.imageViewStaffImage);

        Intent data = getActivity().getIntent();
        String SName = data.getStringExtra("Name");
        String SGender = data.getStringExtra("Gender");
        String SDob = data.getStringExtra("Date of Birth");
        String SPositon = data.getStringExtra("Position");
        String SEmail = data.getStringExtra("Email");

        name.setText(SName);
        gender.setText(SGender);
        dob.setText(SDob);
        position.setText(SPositon);
        email.setText(SEmail);

        StorageReference proref = storageReference.child("staff/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(proImage);
            }
        });

        proImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent opG = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opG,1000);

            }
        });

        btnSubmit = (Button) root.findViewById(R.id.buttonStaffEDSubmit);
        btnCancel =(Button) root.findViewById(R.id.buttonStaffEDCancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sname = name.getText().toString().trim();
                Sgender = gender.getText().toString().trim();
                Sdob = dob.getText().toString().trim();
                Sposition = position.getText().toString().trim();
                Semail = email.getText().toString().trim();
                if(Sname.isEmpty() || Sgender.isEmpty()||Sdob.isEmpty()||Sposition.isEmpty()||Semail.isEmpty()){
                    Toast.makeText(getContext(),"Some of the details is empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                user.updateEmail(Semail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference ref = firestore.collection("Staff").document(id);
                        Map<String,Object> editS = new HashMap<>();
                        editS.put("Name",Sname);
                        editS.put("Gender",Sgender);
                        editS.put("Date of Birth",Sdob);
                        editS.put("Position",Sposition);
                        editS.put("Email",Semail);
                        ref.update(editS);
                        Toast.makeText(getContext(),"Edit Profile Successful!",Toast.LENGTH_SHORT).show();
                        StaffProfileFragment sPro = new StaffProfileFragment();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,sPro).addToBackStack(editStaffFragment.class.getSimpleName()).commit();

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
                StaffProfileFragment staffPro = new StaffProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,staffPro).addToBackStack(editStaffFragment.class.getSimpleName()).commit();
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
        final StorageReference fileref = storageReference.child("staff/"+auth.getCurrentUser().getUid()+"/profile.jpg");
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
