package com.pucha.pavan.sharedpreferencesdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.pucha.pavan.sharedpreferencesdemo", Context.MODE_PRIVATE);
        // ModePrivate allows only your app to access the data
        sharedPreferences.edit().putString("username","pavan").apply();
        // Saves username with a value of pavan.
        String username = sharedPreferences.getString("username","");
        // we need to provide a default value
        Log.i("username",username);
    }
}
