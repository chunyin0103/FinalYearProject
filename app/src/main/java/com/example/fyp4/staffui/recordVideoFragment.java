package com.example.fyp4.staffui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.example.fyp4.Residentui.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class recordVideoFragment extends AppCompatActivity {
    StorageReference storageReference;
    private int VIDEO_REQUEST=101;
    private Uri videoUri = null;
    Button record,display,back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordvideo);
        record = (Button) findViewById(R.id.captureV);
        display = (Button) findViewById(R.id.playV);
        back = (Button) findViewById(R.id.videoBack);
        storageReference = FirebaseStorage.getInstance().getReference();
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureVideo(v);
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayVideo(v);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });


    }
    public void CaptureVideo(View view){
       Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA},VIDEO_REQUEST);
        }
        if(intent .resolveActivity(getPackageManager())!=null){
           startActivityForResult(intent,VIDEO_REQUEST);
       }

    }
    public void displayVideo(View view){
        Intent intent = new Intent(this,displayVideoFragment.class);
        intent.putExtra("videoURI",videoUri.toString());
        startActivity(intent);


    }

   @Override
   public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK) {
            videoUri = data.getData();
            uploadVideo(videoUri);
        }

    }

    private void uploadVideo(Uri content) {

        final StorageReference uploadvideo = storageReference.child("videos/"+ videoUri);
        uploadvideo.putFile(content).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadvideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(recordVideoFragment.this, "Upload video successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == VIDEO_REQUEST){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Camera Permission Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Camera Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
