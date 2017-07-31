package com.pucha.pavan.languagepreferenceshared;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
     TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        final SharedPreferences newSharedPreferences = this.getSharedPreferences("com.pucha.pavan.languagepreferenceshared", Context.MODE_PRIVATE);
        String languageSelected = newSharedPreferences.getString("language", "");
        if (languageSelected == "") {
            showalert();

        } else {
            textView.setText(languageSelected);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change_language) {
            showalert();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showalert() {
        final SharedPreferences newSharedPreferences = this.getSharedPreferences
                ("com.pucha.pavan.languagepreferenceshared", Context.MODE_PRIVATE);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("What language ?")
                .setMessage("English or spanish")
                .setPositiveButton("English", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Change language to english
                        newSharedPreferences.edit().putString("language", "english").apply();
                        textView.setText("English");
                    }
                })
                .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newSharedPreferences.edit().putString("language", "spanish").apply();
                        textView.setText("spanish");
                    }
                })
                .show();
    }
}
