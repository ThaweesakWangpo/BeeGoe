package com.example.beegoe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {
    EditText firstName1,lastName1,email1,username1,password1,confirmPassword,phoneNumber1;
    Button btnReg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName1 = (EditText)findViewById(R.id.firstNameReg);
        lastName1 = (EditText)findViewById(R.id.lastNameReg);
        email1 = (EditText)findViewById(R.id.emailReg);
        username1 = (EditText)findViewById(R.id.usernameReg);
        password1 = (EditText)findViewById(R.id.passwordReg);
       confirmPassword = (EditText)findViewById(R.id.confirmPasswordReg);
        phoneNumber1 = (EditText)findViewById(R.id.phoneReg);
        btnReg = (Button) findViewById(R.id.registerBtn);


            btnReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(password1.getText().toString().equals(confirmPassword.getText().toString())&&password1.length()>0&&confirmPassword.length()>0
                    &&firstName1.length()>0&&lastName1.length()>0&&email1.length()>0&&phoneNumber1.length()>0&&username1.length()>0){

                        Toast toast = Toast.makeText ( Register.this, "Register successful", Toast.LENGTH_LONG );
                        toast.show ( );

                        Intent intent = new Intent(Register.this,MainActivity.class);
                        startActivity(intent);

                        HttpGetRequest post = new HttpGetRequest(firstName1.getText().toString(),lastName1.getText().toString(),email1.getText().toString(),username1.getText().toString(),password1.getText().toString()
                                ,phoneNumber1.getText().toString());

                        post.execute();

                    }
                    else if (!(password1.getText().toString().equals(confirmPassword.getText().toString()))){
                        confirmPassword.setError("Confirm password again");

                    }
                    else {
                        Toast toast = Toast.makeText ( Register.this, "Please check your information", Toast.LENGTH_LONG );

                        toast.show ( );
                    }

                    if(firstName1.length()<=0){
                        firstName1.setError("Please enter your first name");
                    }

                    if(lastName1.length()<=0){
                        lastName1.setError("Please enter your last name");
                    }
                    if(email1.length()<=0){
                        email1.setError("Please enter your email");
                    }
                    if(phoneNumber1.length()<=0){
                        phoneNumber1.setError("Please enter your phone number");
                    }
                    if(password1.length()<=0){
                        password1.setError("Please enter your password");
                    }
                    if(username1.length()<=0){
                        username1.setError("Please enter username");
                    }
                    if(confirmPassword.length()<=0){
                        confirmPassword.setError("Please confirm your password");
                    }

                }

            });



    }

    // Inner class to sent user information
    static class HttpGetRequest extends AsyncTask<Void, Void, Void> {
        String firstname ,lastname , email,username,password,phone;
        String url1 = ("http://192.168.60.246:1234/user/signup");
        HttpGetRequest(String name,String lastName,String email,String username,String password,String phone ){

            this.firstname = name;
            this.lastname = lastName;
            this.email = email;
            this.username = username;
            this.password = password;
            this.phone = phone;



        }


        @Override
        protected Void doInBackground(Void... strings){
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");

            //sent information to server
            JSONObject object = new JSONObject();
            try {
                object.put("email",email);
                object.put("username",username);
                object.put("password",password);
                object.put("firstName",firstname);
                object.put("lastName",lastname);
                object.put("phone",phone);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(mediaType, object.toString());
            Request request = new Request.Builder()
                    .url(url1)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                Log.i("State", response.message());


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


    }
}
