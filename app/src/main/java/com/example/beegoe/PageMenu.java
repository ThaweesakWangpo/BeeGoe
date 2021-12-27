package com.example.beegoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PageMenu extends AppCompatActivity {



    private Button mapBtn,barkBtn,informBtn,videoBtn,profileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagemenu);

        mapBtn = (Button) findViewById(R.id.mapBtn);
        barkBtn = (Button) findViewById(R.id.barkBtn);
        informBtn = (Button) findViewById(R.id.detailBtn);
        videoBtn = (Button) findViewById(R.id.videoBtn);
        profileBtn = (Button) findViewById(R.id.profile);



        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageMenu.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        barkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageMenu.this, Sound.class);
                startActivity(intent);
            }
        });

        informBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.key.equals("null")){
                    Intent intent = new Intent(PageMenu.this, Information.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(PageMenu.this, ShowInfo.class);
                    startActivity(intent);
                }

            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PageMenu.this, Profile.class);
                startActivity(intent);
            }
        });
     }
    }
