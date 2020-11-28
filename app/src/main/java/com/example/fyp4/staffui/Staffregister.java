package com.example.fyp4.staffui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Staffregister extends AppCompatActivity {
    public static final String TAG = "Staffregister";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextView login;
    EditText name, gender,dob, position,email, password;
    String id,Name,Gender,DOB,Position,Email,Password;
    Button reg,cancel;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffregister);

        name = (EditText) findViewById(R.id.editTextStaffName);
        gender = (EditText)findViewById(R.id.editTextStaffGender);
        dob = (EditText)findViewById(R.id.editTextStaffDob);
        position = (EditText)findViewById(R.id.editTextStaffPosition);
        email = (EditText) findViewById(R.id.editTextStaffRUsername);
        password = (EditText) findViewById(R.id.editTextStaffRPass);
        progressBar = (ProgressBar) findViewById(R.id.progressBarStaffRes);

        reg = (Button) findViewById(R.id.buttonStaffSubmit);
        cancel= (Button) findViewById(R.id.buttonStaffCancel);
        login = (TextView) findViewById(R.id.textViewTextLogin);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            finish();
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = name.getText().toString().trim();
                Gender = gender.getText().toString().trim();
                DOB = dob.getText().toString().trim();
                Position = position.getText().toString().trim();
                Email = email.getText().toString().trim();
                Password = password.getText().toString().trim();
                if (TextUtils.isEmpty(Name)) {
                    name.setError("Please Enter Your Name!");
                    return;
                }
                if (TextUtils.isEmpty(Gender)) {
                    gender.setError("Please Enter Your Gender");
                    return;
                }
                if (TextUtils.isEmpty(DOB)) {
                    dob.setError("Please Enter Your Date of Birth!");
                    return;
                }
                if (TextUtils.isEmpty(Position)) {
                    position.setError("Please Enter Your Address!");
                    return;
                }

                if (TextUtils.isEmpty(Email)) {
                    email.setError("Please Enter Your Email!");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("Please Enter Your Password");
                    return;
                }
                if (Password.length() < 5) {
                    password.setError("Password must more than 5 words!");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Staffregister.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            id = auth.getCurrentUser().getUid();

                            DocumentReference ref = firestore.collection("Staff").document(id);
                            Map<String, Object> staff = new HashMap<>();
                            staff.put("Name", Name);
                            staff.put("Gender", Gender);
                            staff.put("Date Of Birth", DOB);
                            staff.put("Position", Position);
                            staff.put("Email", Email);
                            ref.set(staff).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User Profile is created for" + id);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                        }else {
                            Toast.makeText(Staffregister.this, "Register Unsuccessful!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), staff_Login.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),staff_Login.class);
                startActivity(intent);
            }
        });

    }
}

