package com.example.beegoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button signinBTN;
    private TextView creatACC;
   private EditText username,password;

   public static String keep;
   public static  String sentDogName="",sentDogAge="",sentDogwieght="",sentDogLength="",sentDogBreed="",sentDogBlood="";
   public  static boolean a = true;



     private TextView mTextViewResult,sad;

     private  SharedPreferences sharedPreferences;
    public  static String sentName="",sentLastName="",sentEmail="",sentPhone="",key="",id="";
    //@SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.userNameLog);
        password = (EditText)findViewById(R.id.passwordLog);
        signinBTN = (Button) findViewById(R.id.loginBtn) ;

        creatACC = (TextView) findViewById(R.id.newAccount);


        mTextViewResult = (TextView) findViewById(R.id.signin);
        sad = findViewById(R.id.sad);

        sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);


        //Text to register
        creatACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        //Button sign in
        signinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.length()<=0){
                    username.setError("Please enter your username");
                }
                if(password.length()<=0){
                    password.setError("Please enter your password");
                }
                if(username.length()>0&&password.length()>0){
                    keep = username.getText().toString();
                    SentUsernamePassword sent = new SentUsernamePassword(username.getText().toString(),password.getText().toString());
                    sent.execute();


                }




            }


        });
    }






    // Inner class sent email and password to server
    private class SentUsernamePassword extends AsyncTask<String, Void, String> {
        String username,password;
        String url1 = ("http://192.168.60.246:1234/user/signin");
        SentUsernamePassword(String username,String password){


            this.username = username;
            this.password = password;

        }

        @Override
        protected String doInBackground(String... strings){
            Response response = null;
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            final JSONObject object = new JSONObject();


            try {

                object.put("username",username);
                object.put("password",password);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(mediaType, object.toString());
            final Request request = new Request.Builder()
                    .url(url1)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            //get information
          client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if(response.isSuccessful()){
                        final String myResponse = response.body().string();






                        MainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {


                                try {
                                    JSONObject jsonObject = new JSONObject(myResponse);
                                 sentName = jsonObject.getString("Name")   ;
                                 sentLastName = jsonObject.getString("Lastname")   ;
                                sentEmail = jsonObject.getString("Email")   ;
                                sentPhone = jsonObject.getString("Phone")   ;



                                key = jsonObject.getString("foundDog")   ;

                                if(key.equals("null")){


                                }
                                JSONObject jsonObject2 = new JSONObject(key);


                                    sentDogName = jsonObject2.getString("nameDog")   ;
                                    sentDogAge = jsonObject2.getString("age")   ;
                                    sentDogwieght = jsonObject2.getString("weight")   ;
                                    sentDogLength = jsonObject2.getString("high")   ;
                                    sentDogBreed = jsonObject2.getString("breed")   ;
                                    sentDogBlood = jsonObject2.getString("blood")   ;


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                              // mTextViewResult.setText(myResponse);
                               // sad.setText(key);


                            }
                        });
                    }
                }
            });





            try {
                response = client.newCall(request).execute();
                Log.i("State", response.message());
                Log.i("test",response.body().toString());


            } catch (IOException e) {
                e.printStackTrace();
            }


            return response.message();
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("OK")&&username.length()>0&&password.length()>0){

                Toast toast = Toast.makeText ( MainActivity.this, "Sign in Complete", Toast.LENGTH_LONG );
                toast.show ( );

                Intent intent = new Intent(MainActivity.this,PageMenu.class);
                startActivity(intent);

            }
            else{

                Toast toast = Toast.makeText ( MainActivity.this, "Sign in fail. Please check your username and password.", Toast.LENGTH_LONG );
                toast.show ( );

            }


            super.onPostExecute(result);
        }


    }

}
