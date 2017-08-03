package com.pucha.pavan.hackernewsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask newTask = new DownloadTask();
      try{
          String result = newTask.execute("https://hacker-news.firebaseio.com/v0/item/8863.json?print=pretty").get();
          Log.i("Result",result);
      }
      catch (ExecutionException e){
          e.printStackTrace();
      }
      catch (InterruptedException e){
          e.printStackTrace();
      }
    }

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url ;
            HttpURLConnection urlConnection = null;
            /*
            Initial values are null
             */
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while(data!=-1){
                    char currentCharacter = (char) data;
                    result+= currentCharacter;
                    data = inputStreamReader.read();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }
}
