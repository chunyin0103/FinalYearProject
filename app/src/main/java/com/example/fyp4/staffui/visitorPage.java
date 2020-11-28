package com.example.fyp4.staffui;

import android.app.Activity;
import android.content.Context;
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

public class visitorPage extends Fragment {
    View v;
    Context mContext;
    Button add,view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View root = (ViewGroup)inflater.inflate(R.layout.fragment_visitor,container,false);
       add = (Button) root.findViewById(R.id.buttonAddRec);
       view = (Button) root.findViewById(R.id.buttonViewRec);

       add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AddRecFragment addRec = new AddRecFragment();
               getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,addRec).addToBackStack(visitorPage.class.getSimpleName()).commit();
           }
       });

       view.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent((Activity)getContext(), list.class);
                startActivity(intent);

           }
       });

        return root;
    }
}
