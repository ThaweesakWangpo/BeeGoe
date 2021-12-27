package com.example.beegoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {





    private TextView firstName,lastName,email,phone;
    private Button editPro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Connect .xml
        firstName =  findViewById(R.id.namePro);
        lastName =  findViewById(R.id.surPro);
        email = findViewById(R.id.emailPro);
        phone =  findViewById(R.id.phonePro);

        editPro = findViewById(R.id.editPro);



        //set text to show profile
        firstName.setText(MainActivity.sentName);
        lastName.setText(MainActivity.sentLastName);
       email.setText(MainActivity.sentEmail);
        phone.setText(MainActivity.sentPhone);

        editPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this,Register.class);
                startActivity(intent);
            }
        });



    }
}
