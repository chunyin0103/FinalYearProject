package com.example.fyp4.staffui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.example.fyp4.visitor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRecFragment extends Fragment {

    public static final String TAG = "AddRecordFagment";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference ref = firestore.collection("Visitor").document();
    private CollectionReference colRef = firestore.collection("Visitor");
    FirebaseAuth auth;
    String id,vName,vGender,vDob,v_date,v_time;
    EditText name,gender,dob,vdate,vtime;
    Button add,cancel;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_addvisitor,container,false);
        name = (EditText) root.findViewById(R.id.VisitorName);
        gender = (EditText) root.findViewById(R.id.vGender);
        dob = (EditText) root.findViewById(R.id.vDob);
        vdate = (EditText) root.findViewById(R.id.vDate);
        vtime = (EditText)root.findViewById(R.id.vTime);
        add = (Button) root.findViewById(R.id.buttonAddvRec);
        cancel = (Button) root.findViewById(R.id.buttonvCancel);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBarVisitRec);
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vName = name.getText().toString().trim();
                vGender = gender.getText().toString().trim();
                vDob = dob.getText().toString().trim();
                v_date = vdate.getText().toString().trim();
                v_time = vtime.getText().toString().trim();

                if(TextUtils.isEmpty(vName)){
                    name.setError("Please Enter Visitor Name!");
                    return;
                }
                if(TextUtils.isEmpty(vGender)){
                    gender.setError("Please Enter Visitor Gender!");
                    return;
                }
                if(TextUtils.isEmpty(vDob)){
                    dob.setError("Please Enter Visitor Date of Birth!");
                    return;
                }
                if(TextUtils.isEmpty(v_date)){
                    vdate.setError("Please Enter the Visit Date!");
                    return;
                }
                if(TextUtils.isEmpty(v_time)){
                    vtime.setError("Please Enter the visit time!");
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);

                visitor vis = new visitor(vName,vGender,vDob,v_date,v_time);
                colRef.add(vis);

                Toast.makeText(getContext(),"Visitor Record Added.",Toast.LENGTH_SHORT).show();
                visitorPage visitorPage = new visitorPage();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,visitorPage).addToBackStack(AddRecFragment.class.getSimpleName()).commit();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home2Fragment home = new Home2Fragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,home).addToBackStack(AddRecFragment.class.getSimpleName()).commit();
            }
        });


        return root;
    }


}
