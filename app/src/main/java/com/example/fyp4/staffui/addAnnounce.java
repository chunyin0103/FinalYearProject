package com.example.fyp4.staffui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.example.fyp4.Residentui.announceFragment;
import com.example.fyp4.anouncement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.zip.Inflater;

public class addAnnounce extends Fragment {
    private Button add,cancel;
    private EditText title,desc;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference ref = firestore.collection("Announcement");
    private String Title,Desc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_addannounce,container,false);
        add = (Button) root.findViewById(R.id.btnAddAnn);
        cancel = (Button) root.findViewById(R.id.btnCanAnn);
        title = (EditText) root.findViewById(R.id.AddannounceTitle);
        desc = (EditText) root.findViewById(R.id.AddannounceDecs);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = title.getText().toString().trim();
                Desc = desc.getText().toString().trim();

                if(Title.isEmpty()){
                    title.setError("Please Enter Your Announcement Title!");
                    return;
                }
                if (Desc.isEmpty()){
                    desc.setError("Please Enter Your Announcement Description!");
                    return;
                }
                anouncement announce =  new anouncement(Title,Desc);
                ref.add(announce);
                Toast.makeText(getContext(),"New Annoucement is Added.",Toast.LENGTH_SHORT).show();
                announcemenStaff ann = new announcemenStaff();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,ann).addToBackStack(addAnnounce.class.getSimpleName()).commit();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcemenStaff ann = new announcemenStaff();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,ann).addToBackStack(addAnnounce.class.getSimpleName()).commit();
            }
        });



        return root;
    }


}
