package com.example.fyp4.staffui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.example.fyp4.Residentui.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.Inflater;

public class Home2Fragment extends Fragment {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String id;
    ImageView announcement,visitor,cctv,profile,navigate;
    StorageReference storageReference;
    TextView date,time,welcome;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_home2,container,false);
        date = (TextView) root.findViewById(R.id.textViewSDate);
        time = (TextView) root.findViewById(R.id.textViewSTime);
        welcome = (TextView) root.findViewById(R.id.textViewSUser);
        announcement = (ImageView) root.findViewById(R.id.Sannouncement);
        visitor = (ImageView) root.findViewById(R.id.visitorRec);
        cctv = (ImageView) root.findViewById(R.id.cctv);
        profile = (ImageView) root.findViewById(R.id.imageViewS);
        navigate = (ImageView) root.findViewById(R.id.navigation);

        // display visual phone current date and time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
        String currentTime = format.format(cal.getTime());
        date.setText(currentDate);
        time.setText(currentTime);

        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();

        // display the data = name through firebase firestore
        DocumentReference ref = firestore.collection("Staff").document(id);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    welcome.setText("Welcome " + value.getString("Name"));
                }catch (NullPointerException ignored){}
            }
        });


        // view photo
        StorageReference proref = storageReference.child("staff/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile);
            }
        });


        // onclick
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaffProfileFragment staff = new StaffProfileFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,staff).addToBackStack(Home2Fragment.class.getSimpleName()).commit();
             }
        });
        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                announcemenStaff announce = new announcemenStaff();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,announce).addToBackStack(Home2Fragment.class.getSimpleName()).commit();
            }
        });
        visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitorPage visitor = new visitorPage();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,visitor).addToBackStack(Home2Fragment.class.getSimpleName()).commit();
            }
        });
        cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            cctvFragment cctv = new cctvFragment();
           getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,cctv).addToBackStack(Home2Fragment.class.getSimpleName()).commit();
            }
        });
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkParkingFragment parking = new checkParkingFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,parking).addToBackStack(Home2Fragment.class.getSimpleName()).commit();
            }
        });


        return root;
    }
}
