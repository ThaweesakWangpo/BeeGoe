package com.example.beegoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ShowInfo extends AppCompatActivity {

    private TextView nameDogShow,ageDogShow,weightDogShow,lengthDogShow,breedDogShow,bloodDogShow;
    private Button editBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfo);

        //Connect text in .xml
        nameDogShow = findViewById(R.id.nameshow);
        ageDogShow = findViewById(R.id.ageshow);
       weightDogShow = findViewById(R.id.wieghtshow);
        lengthDogShow = findViewById(R.id.lengthshow);
        breedDogShow = findViewById(R.id.breedshow);
        bloodDogShow = findViewById(R.id.bloodshow);

        editBtn = (Button) findViewById(R.id.edit);

        //Set text information of dog

        if(MainActivity.a == true){

            nameDogShow.setText(MainActivity.sentDogName);
            ageDogShow.setText(MainActivity.sentDogAge);
            weightDogShow.setText(MainActivity.sentDogwieght);
            lengthDogShow.setText(MainActivity.sentDogLength);
            breedDogShow.setText(MainActivity.sentDogBreed);
            bloodDogShow.setText(MainActivity.sentDogBlood);
        }
        else  if(MainActivity.a == false){
            nameDogShow.setText(Information.sentDogNameIn);
            ageDogShow.setText(Information.sentDogAgeIn);
            weightDogShow.setText(Information.sentDogwieghtIn);
            lengthDogShow.setText(Information.sentDogLengthIn);
            breedDogShow.setText(Information.sentDogBreedIn);
            bloodDogShow.setText(Information.sentDogBloodIn);
            //MainActivity.a = true;

        }


//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ShowInfo.this,Information.class);
//                startActivity(intent);
//            }
//        });


    }


}
