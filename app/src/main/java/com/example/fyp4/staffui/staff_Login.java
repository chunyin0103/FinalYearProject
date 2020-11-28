package com.example.fyp4.staffui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp4.R;
import com.example.fyp4.Residentui.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class staff_Login extends AppCompatActivity {
    EditText eTextSUname,eTextSPass;
    String email,pass;
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    TextView forgetPass,tVReg, passlogin;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff__login);
        progressBar = (ProgressBar) findViewById(R.id.progressBarStaffLogin);

        eTextSUname = (EditText) findViewById(R.id.editTextStaffUsername);
        eTextSPass = (EditText) findViewById(R.id.editTextstaffPassword);
        btnLogin = (Button) findViewById(R.id.buttonStaffLogin);
        tVReg = (TextView) findViewById(R.id.textViewStaffSignUp);
        forgetPass = (TextView) findViewById(R.id.textForgetStaffPass);
        passlogin = (TextView) findViewById(R.id.passLogin);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = eTextSUname.getText().toString().trim();
                pass = eTextSPass.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    eTextSUname.setError("Please Enter Your Email!");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    eTextSPass.setError("Please Enter Your Password");
                    return;
                }
                if(pass.length()<5){
                    eTextSPass.setError("Password must more than 5 words!");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(staff_Login.this,"Login Successfully!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(staff_Login.this,"Login Unsuccessfully!",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetEmail = new EditText(v.getContext());
                AlertDialog.Builder passResetDialog = new AlertDialog.Builder(v.getContext());
                passResetDialog.setTitle("Reset Password ?");
                passResetDialog.setMessage("Enter Your Email To Received Reset Link");
                passResetDialog.setView(resetEmail);

                passResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email amd send the reset link
                        String mail = resetEmail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(staff_Login.this,"Reset Link Send to Your Email!",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(staff_Login.this,"Reset Link is Not Sent!"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(staff_Login.this,"Reset Password is cancelled.",Toast.LENGTH_SHORT).show();
                }
            });
                passResetDialog.create().show();
           }
   });

        tVReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Staffregister.class);
                startActivity(intent);
            }
        });
        passlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
    }
}