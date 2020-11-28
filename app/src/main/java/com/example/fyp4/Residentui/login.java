package com.example.fyp4.Residentui;
import com.example.fyp4.staffui.staff_Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
        private static String TAG ="Login Page";
        private FirebaseAuth auth;
        TextView NvName,NvEmail, passtoS;
        EditText eTextUname;
        EditText eTextPass;
        ProgressBar progressBar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_login);
            auth = FirebaseAuth.getInstance();
            eTextUname = (EditText) findViewById(R.id.editTextUsername);
            eTextPass = (EditText) findViewById(R.id.editTextPassword);
            Button btnLogin = (Button) findViewById(R.id.buttonLogin);
            TextView textReg = (TextView) findViewById(R.id.textSignUp);
            TextView textForget = (TextView) findViewById(R.id.textForgetPass);
            progressBar = findViewById(R.id.progressBarlogin);
            passtoS = (TextView) findViewById(R.id.passLogintoStaff);
            NvName = findViewById(R.id.navName);
            NvEmail = findViewById(R.id.navEmail);
            //email = eTextUname.getText().toString().trim();
            //pass = eTextPass.getText().toString().trim();

            //login account
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Email = eTextUname.getText().toString().trim();
                    String Pass = eTextPass.getText().toString().trim();
                    if(TextUtils.isEmpty(Email)){
                        eTextUname.setError("Please Enter Your Email!");
                        return;
                    }
                    if(TextUtils.isEmpty(Pass)){
                        eTextPass.setError("Please Enter Your Password");
                        return;
                    }
                    if(Pass.length()<5){
                        eTextPass.setError("Password must more than 5 words!");
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    //authentication by using email and password
                    auth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                Toast.makeText(login.this,"Login Successful!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(login.this,"Login Unsuccessfil!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });


                    //validation();

                }
            });

            //forget password
            textForget.setOnClickListener(new View.OnClickListener() {
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
                            if(mail.isEmpty()){
                                Toast.makeText(login.this,"Please Enter the Email",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(login.this,"Reset Link Send to Your Email!",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(login.this,"Reset Link is Not Sent!"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    passResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        String mail = resetEmail.getText().toString();
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(mail.isEmpty()){
                                Toast.makeText(login.this,"Please Enter the Email",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            auth.sendPasswordResetEmail(mail).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(login.this,"The forget password is declined"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    passResetDialog.create().show();
                }
            });

            textReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), register.class);
                    startActivity(intent);
                }
            });
            passtoS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),staff_Login.class);
                    startActivity(intent);
                }
            });
        }



}


