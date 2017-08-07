package com.pucha.pavan.hackernewsreader;

import android.os.AsyncTask;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class MainActivity extends AppCompatActivity {
    Map<Integer,String> articleURLs = new HashMap<Integer, String>();
    // Integer is article ID and String will store the url
    Map<Integer,String> articleTitles = new HashMap<Integer, String>();
    ArrayList<Integer>  articleIds = new ArrayList<Integer>();

    // Creating a DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask newTask = new DownloadTask();
      try{
          String result = newTask.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
          JSONArray jsonArray = new JSONArray(result);

          for(int i =0; i< 20;i++){
              String articleId = jsonArray.getString(i);
              DownloadTask getArticle= new DownloadTask();

              String articleInfo = getArticle.execute("https://hacker-news.firebaseio.com/v0/item/"+ jsonArray.getString(i)+".json?print=pretty").get();
              JSONObject jsonObject = new JSONObject(articleInfo);
              Log.i("jsonObject",jsonObject.toString());
              String articleTitle = jsonObject.getString("title");
              String articleUrl = jsonObject.getString("url");
              articleIds.add(Integer.valueOf(articleId));
              articleTitles.put(Integer.valueOf(articleId),articleTitle);
              articleURLs.put(Integer.valueOf(articleId),articleUrl);

          }
          Log.i("Article ID's", articleIds.toString());
          Log.i("Article Titles ", articleTitles.toString());
          Log.i("Article urL", articleURLs.toString());
      }
      catch (Exception e){
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
