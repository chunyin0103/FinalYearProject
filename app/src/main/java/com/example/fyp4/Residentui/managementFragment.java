package com.example.fyp4.Residentui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp4.R;

import java.util.zip.Inflater;

public class managementFragment extends Fragment {
    Button back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = (ViewGroup) inflater.inflate(R.layout.fragment_management,container,false);

        back = (Button) root.findViewById(R.id.buttonBackToHome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment home = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,home).addToBackStack(managementFragment.class.getSimpleName()).commit();
            }
        });

        return root;
    }
}
