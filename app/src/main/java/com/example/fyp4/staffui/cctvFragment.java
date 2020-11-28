package com.example.fyp4.staffui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;
import com.example.fyp4.Residentui.MainActivity;

public class cctvFragment extends Fragment {
    Button record,back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup)inflater.inflate(R.layout.fragment_cctv,container,false);
        record = (Button) root.findViewById(R.id.buttonRecordVideo);
        back = (Button) root.findViewById(R.id.back);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity)getActivity(),recordVideoFragment.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity)getActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
