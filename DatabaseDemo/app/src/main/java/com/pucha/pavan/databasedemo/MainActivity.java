package com.pucha.pavan.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntegerRes;
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

            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INTEGER(3), id INTEGER PRIMARY KEY)");
            myDatabase.execSQL("INSERT INTO newUsers(name,age) VALUES ('BB King',45)");
            myDatabase.execSQL("INSERT INTO newUsers(name,age) VALUES ('Albert King',42)");

            Cursor cursor = myDatabase.rawQuery("SELECT * FROM newUsers", null);
            /*
            Main Part, usually in other software  we dont need to deal with indexes

             */

            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            int idIndex = cursor.getColumnIndex("id");
            cursor.moveToFirst();
            while (cursor != null) {
                Log.i("Name", cursor.getString(nameIndex));
                Log.i("Age", Integer.toString(cursor.getInt(ageIndex)));
                Log.i("ID", Integer.toString(cursor.getInt(idIndex)));
                cursor.moveToNext();

                /*
                Now lets try to delete an element


                 */

            }

        } catch (Exception e) {

        }
    }
}
