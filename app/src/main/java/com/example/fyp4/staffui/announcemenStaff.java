package com.example.fyp4.staffui;

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

import com.example.fyp4.R;
import com.example.fyp4.anouncement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class announcemenStaff extends Fragment {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference ref = firestore.collection("Announcement");
    private FloatingActionButton actionButton;
    private TextView title;
    private Button btnback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.activity_add_announcement,container,false);
        actionButton = (FloatingActionButton) root.findViewById(R.id.add_announce);
        //actionButton.setImageResource(R.drawable.ic_baseline_add);
        title = (TextView) root.findViewById(R.id.Announcetitle);
        btnback = (Button) root.findViewById(R.id.backFromAnn);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity)getActivity(),MainActivity2.class);
                startActivity(intent);
            }
        });


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnnounce add = new addAnnounce();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,add).addToBackStack(announcemenStaff.class.getSimpleName()).commit();
            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!= null){
                    return;
                }
                String t1 ="";
                String t2 ="";
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
