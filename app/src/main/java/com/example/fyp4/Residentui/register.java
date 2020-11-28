package com.example.fyp4.Residentui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.fyp4.R;
import com.example.fyp4.Resident;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    private static final String TAG = "register";
    //Boolean RName,RGender,RDOB,RAddress,Rphone,Remail,Rpass,RconPass;
    EditText name,pass,gender,address,phone,email,dob;
    Button submit, cancel;
    TextView login;
    FirebaseAuth auth;
    String id,Name,Gender,DOB,Address,Phone,Email,Pass;
    ProgressBar progressBar;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseFirestore data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarSignUp);
        data = FirebaseFirestore.getInstance();

        //get data id
        name = findViewById(R.id.editTextName);
        gender = findViewById(R.id.editTextGender);
        dob = findViewById(R.id.editTextDob);
        address = findViewById(R.id.editTextAddress);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextUsername);
        pass = findViewById(R.id.editTextPass);
        submit = findViewById(R.id.buttonSubmit);
        cancel = findViewById(R.id.buttonCancel);
        login = findViewById(R.id.textViewreLogin);
        //check the account whether is logout or not
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = name.getText().toString().trim();
                Gender = gender.getText().toString().trim();
                DOB = dob.getText().toString().trim();
                Address = address.getText().toString().trim();
                Phone = phone.getText().toString().trim();
                Email = email.getText().toString().trim();
                Pass = pass.getText().toString().trim();
                final Resident res = new Resident(Name,Gender,DOB,Address,Phone,Email,Pass);

                if(TextUtils.isEmpty(Name)){
                    name.setError("Please Enter Your Name!");
                    return;
                }
                if(TextUtils.isEmpty(Gender)){
                    gender.setError("Please Enter Your Gender");
                    return;
                }
                if(TextUtils.isEmpty(DOB)){
                    dob.setError("Please Enter Your Date of Birth!");
                    return;
                }
                if(TextUtils.isEmpty(Address)){
                    address.setError("Please Enter Your Address!");
                    return;
                }
                if(TextUtils.isEmpty(Phone)){
                    phone.setError("Please Enter Your Phone Number!");
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    email.setError("Please Enter Your Email!");
                    return;
                }
                if(TextUtils.isEmpty(Pass)){
                    pass.setError("Please Enter Your Password");
                    return;
                }
                if(Pass.length()<5){
                    pass.setError("Password must more than 5 words!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                    auth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(register.this, "Register Successful!", Toast.LENGTH_SHORT).show();
                                id = auth.getCurrentUser().getUid();
                                DocumentReference ref = data.collection("Resident").document(id);
                                Map<String,Object> res = new HashMap<>();
                                res.put("Name",Name);
                                res.put("Gender",Gender);
                                res.put("Date of Birth",DOB);
                                res.put("Address",Address);
                                res.put("Phone",Phone);
                                res.put("Email",Email);
                                //res.put("Password",Pass);
                                ref.set(res).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"onSuccess: User Profile is created for"+id);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"onFailure: "+e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(register.this, "Register Unsuccessful!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });

    }


}