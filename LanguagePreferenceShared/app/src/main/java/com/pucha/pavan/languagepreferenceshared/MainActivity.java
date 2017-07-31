package com.pucha.pavan.languagepreferenceshared;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences newSharedPreferences = this.getSharedPreferences("com.pucha.pavan.languagepreferenceshared", Context.MODE_PRIVATE);
        String languageSelected = newSharedPreferences.getString("language","");
        if(languageSelected =="") {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("What language ?")
                    .setMessage("English or spanish")
                    .setPositiveButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Change language to english
                            newSharedPreferences.edit().putString("language", "english").apply();
                        }
                    })
                    .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            newSharedPreferences.edit().putString("language", "spanish").apply();
                        }
                    })
                    .show();

        }
    }
}
