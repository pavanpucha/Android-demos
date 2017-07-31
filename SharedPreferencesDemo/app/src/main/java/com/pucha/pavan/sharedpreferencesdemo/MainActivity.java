package com.pucha.pavan.sharedpreferencesdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.add){
            Log.i("Action button tapped", "Add");
            return true;
        }
      return super.onOptionsItemSelected(menuItem);
    }

}
