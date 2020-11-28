package com.example.fyp4.Residentui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp4.R;
import com.example.fyp4.anouncement;
import com.example.fyp4.staffui.MainActivity2;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class announceFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection("Announcement");
    TextView title;
    private Button btnBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.activity_listannounce,container,false);
        title = (TextView) root.findViewById(R.id.Atitle);
        btnBack = (Button) root.findViewById(R.id.backFromAnnR);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity)getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void onStart() {
        super.onStart();
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!= null){
                    return;
                }
                String t1 ="";
                for(QueryDocumentSnapshot documentSnapshot: value){
                    anouncement ann = documentSnapshot.toObject(anouncement.class);
                    String Atitle = ann.getTitle();
                    String Adesc = ann.getDesc();

                    t1 +="Title: "+ Atitle+"\n\n"+"Description:\n"+Adesc+"\n\n\n\n";
                    title.setText(t1);

                }
            }
        });
    }
}
