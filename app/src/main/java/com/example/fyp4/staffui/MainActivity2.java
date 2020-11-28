package com.example.fyp4.staffui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.fyp4.R;
import com.example.fyp4.Residentui.FirstPage;
import com.example.fyp4.Residentui.MainActivity;
import com.example.fyp4.Residentui.ProflleFragment;
import com.example.fyp4.Residentui.login;
import com.example.fyp4.staffui.Home2Fragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private TextView NavName,NavEmail;
    private String id;
    private ImageView profile;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DocumentReference ref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        id = auth.getCurrentUser().getUid();
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        //drawer header
        View header = navView.getHeaderView(0);
        NavName = (TextView) header.findViewById(R.id.navName);
        NavEmail = (TextView) header.findViewById(R.id.navEmail);
        profile = (ImageView) header.findViewById(R.id.headerProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaffProfileFragment pro = new StaffProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,pro).addToBackStack(MainActivity2.class.getSimpleName()).commit();
            }
        });

        ref = firestore.collection("Staff").document(id);
        ref.addSnapshotListener(MainActivity2.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    NavEmail.setText(value.getString("Email"));
                    NavName.setText("Welcome " + value.getData().get("Name").toString());
                }catch(NullPointerException ignored){}

            }
        });

        StorageReference proref = storageReference.child("staff/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile);
            }
        });



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home2Fragment()).commit();
            navView.setCheckedItem(R.id.nav_home2);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_home2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home2Fragment()).commit();
                break;
            case R.id.nav_StaffProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new StaffProfileFragment()).commit();
                break;
            case R.id.nav_visitorRec:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new visitorPage()).commit();
                break;
            case R.id.nav_cctv:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new cctvFragment()).commit();
                break;
            case R.id.nav_navigatePS:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new checkParkingFragment()).commit();
                break;
            case R.id.nav_log_out:
                    try{
                        auth.signOut();
                        Toast.makeText(MainActivity2.this,"Sign Out successful",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity2.this, FirstPage.class);
                        startActivity(intent);

                    }catch (Exception e){
                        Toast.makeText(this,"Log Out Unsuccessful!",Toast.LENGTH_SHORT).show();
                    }
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
