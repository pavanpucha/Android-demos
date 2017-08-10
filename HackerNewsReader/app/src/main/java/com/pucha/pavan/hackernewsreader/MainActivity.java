package com.pucha.pavan.hackernewsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.support.v7.widget.AppCompatDrawableManager.get;

public class MainActivity extends AppCompatActivity {
    Map<Integer, String> articleURLs = new HashMap<Integer, String>();
    // Integer is article ID and String will store the url
    Map<Integer, String> articleTitles = new HashMap<Integer, String>();
    ArrayList<Integer> articleIds = new ArrayList<Integer>();
    SQLiteDatabase articleDB;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    /*

     */
    ArrayList<String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                intent.putExtra("articleUrl", urls.get(pos));
                startActivity(intent);
                Log.i("article URL", urls.get(pos));
            }
        });
        articleDB = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null);
        articleDB.execSQL("DROP TABLE article");
        articleDB.execSQL("CREATE TABLE IF NOT EXISTS article (id INTEGER PRIMARY KEY, articleID INTEGER, url VARCHAR , title VARCHAR, content VARCHAR)");


        DownloadTask newTask = new DownloadTask();
        // Cursor cursor ;

        try {

            String result = newTask.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
            JSONArray jsonArray = new JSONArray(result);
            articleDB.execSQL("DELETE FROM articles");
            for (int i = 0; i < 20; i++) {
                String articleId = jsonArray.getString(i);
                DownloadTask getArticle = new DownloadTask();

                String articleInfo = getArticle.execute("https://hacker-news.firebaseio.com/v0/item/" + jsonArray.getString(i) + ".json?print=pretty").get();
                JSONObject jsonObject = new JSONObject(articleInfo);
                Log.i("jsonObject", jsonObject.toString());
                String articleTitle = jsonObject.getString("title");
                String articleUrl;
                if (jsonObject.has("url"))
                    articleUrl = jsonObject.getString("url");
                else
                    articleUrl = " LINK NOT FOUND";
                articleIds.add(Integer.valueOf(articleId));
                articleTitles.put(Integer.valueOf(articleId), articleTitle);
                articleURLs.put(Integer.valueOf(articleId), articleUrl);
                String inputSQL = "INSERT INTO article (articleID ,url,title)  VALUES(?, ? ,?)";
                SQLiteStatement sqLiteStatement = articleDB.compileStatement(inputSQL);
                sqLiteStatement.bindString(1, articleId);
                sqLiteStatement.bindString(2, articleUrl);
                sqLiteStatement.bindString(3, articleTitle);

                sqLiteStatement.execute();

            }
            Cursor cursor = articleDB.rawQuery("SELECT * FROM article ORDER BY articleID DESC", null);
            cursor.moveToFirst();
            titles.clear();
            urls.clear();
            int articleIdIndex = cursor.getColumnIndex("articleID");
            int urlIndex = cursor.getColumnIndex("url");
            int titleIndex = cursor.getColumnIndex("title");
            cursor.moveToFirst();
            while (cursor.moveToNext()) {

                titles.add(cursor.getString(titleIndex));
                urls.add(cursor.getString(urlIndex));
            }
            arrayAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
// whistles


    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
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
                while (data != -1) {
                    char currentCharacter = (char) data;
                    result += currentCharacter;
                    data = inputStreamReader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}
