package com.example.fyp4.staffui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class displayVideoFragment  extends AppCompatActivity {
    private static  final int PICK_VIDEO = 1 ;
    private VideoView video;
    Button back;
    private Uri videoUri;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayvideo);
        back = (Button) findViewById(R.id.buttonbackfromcam);
        video = (VideoView) findViewById(R.id.videoView);
            videoUri = Uri.parse(getIntent().getExtras().getString("videoURI"));

            video.setVideoURI(videoUri);
            video.setMediaController(new MediaController(this));
            video.requestFocus();
            video.start();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cctvFragment cctv = new cctvFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,cctv).addToBackStack(displayVideoFragment.class.getSimpleName()).commit();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_VIDEO || resultCode == RESULT_OK
        || data != null || data.getData() != null){
            videoUri = data.getData();

            video.setVideoURI(videoUri);
        }

    }
    public void ChooseVideo(View view){
        Intent intent = new Intent();
        intent.setType("videos/content:/media/external/video/media/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO);
    }
    private String getExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
