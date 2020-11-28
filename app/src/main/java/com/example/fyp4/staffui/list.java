package com.example.fyp4.staffui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp4.R;
import com.example.fyp4.visitor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.view.View;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class list extends AppCompatActivity {
    private static String TAG = "list";
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();;
    private DocumentReference ref;
    Button load;
    private TextView list;
    private CollectionReference colref = firestore.collection("Visitor") ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list = (TextView)findViewById(R.id.listdata);

        load = (Button) findViewById(R.id.back);
        load.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(list.this,MainActivity2.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        colref.orderBy("visit_date", Query.Direction.DESCENDING)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot value,FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                String data= "";
                for(QueryDocumentSnapshot documentSnapshot: value){
                    visitor visit = documentSnapshot.toObject(visitor.class);
                    String name = visit.getName();
                    String gender = visit.getGender();
                    String dob = visit.getDob();
                    String vDate = visit.getVisit_date();
                    String vTime = visit.getVisit_time();

                    data +="\nName: "+name +"\nGender: "+gender+"\nDate of Birth: "+dob+"\nVisit Date: "+vDate+"\nVisit Time: "+vTime+ "\n\n";
                    list.setText(data);
                }

            }
        });

    }
}
