package com.example.beegoe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Information  extends AppCompatActivity {



    private Spinner spinner;
    private Button save;
    private EditText nameDog,ageDog,weightDog,lengthDog,breedDog;
    private String bloodDog;
    private TextView wee;
    public static  String sentDogNameIn="",sentDogAgeIn="",sentDogwieghtIn="",sentDogLengthIn="",sentDogBreedIn="",sentDogBloodIn="";

   // public static  String sentDogName="",sentDogAge="",sentDogwieght="",sentDogLength="",sentDogBreed="",sentDogBlood="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        nameDog = (EditText) findViewById(R.id.dogname);
        ageDog = (EditText) findViewById(R.id.age);
        lengthDog = (EditText) findViewById(R.id.length);
        weightDog = (EditText) findViewById(R.id.weight);

        breedDog = (EditText) findViewById(R.id.breed);
        save = (Button) findViewById(R.id.saveBtn);

        wee = findViewById(R.id.a);

        spinner =  findViewById(R.id.spinner);


        List<String> category = new ArrayList<>();
        category.add(0,"choose blood");
        category.add("DEA 1.1");
        category.add("DEA 1.2");
        category.add("DEA 3");
        category.add("DEA 4");
        category.add("DEA 5");
        category.add("DEA 6");
        category.add("DEA 7");
        category.add("DEA 8");


        ArrayAdapter<String> dataAdapt;
        dataAdapt = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, category);


        dataAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapt);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("choose blood")){

                }
                else {
                    String item = parent.getItemAtPosition(position).toString();

                    bloodDog=item;

                    //Toast.makeText(parent.getContext(),"Select: "+item,Toast.LENGTH_SHORT).show();
                }


                 //bloodDog = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });

       // bloodDog = spinner.getSelectedItem().toString();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameDog.length()<=0){
                    nameDog.setError("Please enter dog's name");
                }
                if(ageDog.length()<=0){
                    ageDog.setError("Please enter dog's age");
                }
                if(weightDog.length()<=0){
                    weightDog.setError("Please enter dog's weight");
                }
                if(lengthDog.length()<=0){
                    lengthDog.setError("Please enter dog's length");
                }
                if(breedDog.length()<=0){
                    breedDog.setError("Please enter dog's breed");
                }



                if(nameDog.length()>0&&ageDog.length()>0&&weightDog.length()>0&&lengthDog.length()>0) {
                    HttpInformation sent = new HttpInformation(nameDog.getText().toString(), ageDog.getText().toString(), weightDog.getText().toString(), lengthDog.getText().toString(),breedDog.getText().toString(),bloodDog);
                    sent.execute();

                }

            }
        });

    }

    private class HttpInformation extends AsyncTask <String, Void, String> {
        String name,age,weight,length,blood,breed;
        String url1 = ("http://192.168.60.246:1234/info/addin");
        HttpInformation(String name,String age,String weight,String length,String breed,String blood){


            this.name = name;
            this.age = age;
            this.weight = weight;
            this.length = length;
            this.breed = breed;
            this.blood = blood;


        }

        @Override
        protected String doInBackground(String... strings){
            Response response = null;
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject object = new JSONObject();


            try {

                object.put("nameDog",name);
                object.put("age",age);
                object.put("weight",weight);
                object.put("high",length);
                object.put("blood",blood);
                object.put("breed",breed);
                object.put("username",MainActivity.keep);




            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(mediaType, object.toString());
            Request request = new Request.Builder()
                    .url(url1)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            //Get Detail
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if(response.isSuccessful()){
                        final String myResponse = response.body().string();

                         Information.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {


                                try {
                                    JSONObject jsonObject = new JSONObject(myResponse);

                                     sentDogNameIn = jsonObject.getString("nameDog")   ;
                                    sentDogAgeIn = jsonObject.getString("age")   ;
                                    sentDogwieghtIn = jsonObject.getString("weight")   ;
                                    sentDogLengthIn = jsonObject.getString("high")   ;
                                    sentDogBreedIn = jsonObject.getString("breed")   ;
                                    sentDogBloodIn = jsonObject.getString("blood")   ;

                                    MainActivity.a = false;
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


            } catch (IOException e) {
                e.printStackTrace();
            }


            return response.message();
        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("OK")&&name.length()>0&&age.length()>0&&weight.length()>0&&length.length()>0&&breed.length()>0&&blood.length()>0){

                Toast toast = Toast.makeText ( Information.this, "Information Complete", Toast.LENGTH_LONG );
                toast.show ( );

                Intent intent = new Intent(Information.this,ShowInfo.class);
                startActivity(intent);

            }
            else{

                Toast toast = Toast.makeText ( Information.this, "Please check your information", Toast.LENGTH_LONG );
                toast.show ( );

            }


            super.onPostExecute(result);
        }




    }

}
