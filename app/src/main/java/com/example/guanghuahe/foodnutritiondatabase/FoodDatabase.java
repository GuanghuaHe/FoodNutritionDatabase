package com.example.guanghuahe.foodnutritiondatabase;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class FoodDatabase extends AsyncTask <Void, Void, String> {
  @Override
  protected String doInBackground(Void... params) {
    String response="";
    try {
      URL API = new URL("https");
      HttpsURLConnection myConnection = (HttpsURLConnection) API.openConnection();

      myConnection.setRequestProperty("40cb1f76","9dd571cf4d9e83a7796c460130be79dd");

      if(myConnection.getResponseCode() == 200) {
        InputStream responseBody = myConnection.getInputStream();
      }

      return response;

    }catch (Exception e){
      e.printStackTrace();
    }

    return null;
  }

}