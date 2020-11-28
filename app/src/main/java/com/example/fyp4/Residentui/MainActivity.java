package com.example.fyp4.Residentui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp4.R;
import com.example.fyp4.staffui.checkParkingFragment;
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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView NavName,NavEmail;
    private FirebaseAuth auth;
    private FirebaseFirestore data;
    private String id,name,email;
    private ImageView profile;
    private StorageReference storageReference;
    private DocumentReference ref;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        View header = navView.getHeaderView(0);

        drawer = findViewById(R.id.drawer_layout);
        NavName = (TextView) header.findViewById(R.id.navName);
        NavEmail = (TextView) header.findViewById(R.id.navEmail);
        profile = (ImageView) header.findViewById(R.id.headerProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProflleFragment pro = new ProflleFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,pro).addToBackStack(MainActivity.class.getSimpleName()).commit();
            }
        });


        //Firebase
        auth = FirebaseAuth.getInstance();
        data =FirebaseFirestore.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        try {
            id = auth.getCurrentUser().getUid();
        }catch (NullPointerException ignored){}
        ref = data.collection("Resident").document(id);
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try{
                    NavEmail.setText(value.getData().get("Email").toString());
                    NavName.setText("Welcome " + value.getString("Name"));
                }catch (NullPointerException ignored){

                }

           }
       });
        StorageReference proref = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navView.setCheckedItem(R.id.nav_home);
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                break;
            case R.id.nav_generateQR:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GenerateqrFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProflleFragment()).commit();
                break;
            case R.id.nav_PayMF:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PaymentFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(MainActivity.this,"Sign Out successful",Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MainActivity.this,FirstPage.class);
                startActivity(intent);
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