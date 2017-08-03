package com.pucha.pavan.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            /*
            @param Users in openOrCreateDatabase is the name of the tablef
             */
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            /*
            Imagine it being stored in an excel-esque sheet

             */
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR , age INT(3))");
            // Create a table if it exists
            // Creats name with varchar datatype and age with int(of 3 characters);
            // Android studio doesn't tell if the sql command is typed correctly or wrongly

            /*
            Now Let's insert into the table

             */
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES ('Ralph',34)");
            myDatabase.execSQL("INSERT INTO users(name,age) VALUES ('Lauren',36)");

            // Now Let's retrive the data
            // Cursor allows us to loop data
            Cursor cursor = myDatabase.rawQuery("SELECT * FROM users", null);
            /*
            Main Part, usually in other software  we dont need to deal with indexes

             */

            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");
            cursor.moveToFirst();
            while (cursor != null) {
                Log.i("Name", cursor.getString(nameIndex));
                Log.i("Age",Integer.toString(cursor.getInt(ageIndex)));
                cursor.moveToNext();
            }

        } catch (Exception e) {

        }
    }
}
