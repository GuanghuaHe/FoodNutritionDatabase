package com.example.guanghuahe.foodnutritiondatabase;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  TextView output;
  EditText input;

  ProgressDialog pd;

  String app_id="40cb1f76", app_key="9dd571cf4d9e83a7796c460130be79dd";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("API")));
      app_id = br.readLine();
      app_key = br.readLine();
    }catch (FileNotFoundException exc){
      Log.d("API File error:",exc.toString());
    }catch (IOException exc){
      Log.d("API Credential Error:",exc.toString());
    }

    RelativeLayout relativeLayout = findViewById(R.id.mainLayout);
    output = findViewById(R.id.output);
    input = findViewById(R.id.FoodInput);

    Button search = findViewById(R.id.search_button);
    search.setOnClickListener(this);


  }

  @Override
  public void onClick(View v){

    String food = input.getText().toString();
    output.setText(food);
    new JsonTask().execute(getFoodParser(food));


  }

  private class JsonTask extends AsyncTask<String, String, String> {

    protected void onPreExecute() {
      super.onPreExecute();

      pd = new ProgressDialog(MainActivity.this);
      pd.setMessage("Please wait");
      pd.setCancelable(false);
      pd.show();
    }

    protected String doInBackground(String... params) {


      HttpURLConnection connection = null;
      BufferedReader reader = null;

      try {
        URL url = new URL(params[0]);
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();


        InputStream stream = connection.getInputStream();

        reader = new BufferedReader(new InputStreamReader(stream));

        StringBuffer buffer = new StringBuffer();
        String line = "";

        while ((line = reader.readLine()) != null) {
          buffer.append(line+"\n");
          Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

        }

        return buffer.toString();


      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (connection != null) {
          connection.disconnect();
        }
        try {
          if (reader != null) {
            reader.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return null;
    }

    @Override
    protected void onPostExecute(String result) {
      super.onPostExecute(result);
      if (pd.isShowing()){
        pd.dismiss();
      }
      output.setText(result);
    }
  }

  private String getFoodParser (String food){
    food.replace(" ","%20");
    return "https://api.edamam.com/api/food-database/parser?ingr=" + food + "&app_id=" + app_id + "&app_key=" + app_key;
  }
}