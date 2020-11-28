package com.example.fyp4.Residentui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fyp4.R;
import com.example.fyp4.staffui.staff_Login;

public class FirstPage extends AppCompatActivity {
    private static int Splash_screen =5000;
    Animation topAnimation,bot_Animation;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_page);
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bot_Animation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        Button resLogin = (Button) findViewById(R.id.buttonResLogin);
        Button staffLogin = (Button) findViewById(R.id.buttonStaff_Login);
        image = findViewById(R.id.imageViewLogo);

        image.setAnimation(topAnimation);
        resLogin.setAnimation(bot_Animation);
        staffLogin.setAnimation(bot_Animation);

        resLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, login.class);
                startActivity(intent);
            }
        });
        staffLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstPage.this, staff_Login.class);
                startActivity(intent);
            }
        });
    }
}