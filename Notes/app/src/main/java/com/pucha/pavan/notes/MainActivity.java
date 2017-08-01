package com.pucha.pavan.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes= new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static  Set<String> set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.pucha.pavan.notes", Context.MODE_PRIVATE);
        set = sharedPreferences.getStringSet("notes",null);
        if(set!=null){
            notes.clear();
            notes.addAll(set);
        }
        else
        {
          //  notes.clear();
            notes.add("example Note");
            set = new HashSet<String>();
            set.addAll(notes);
            sharedPreferences.edit().putStringSet("notes",set).apply();
        }
               
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*
            @param position : Position in the view
             */
             @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getApplicationContext(),EditYourNote.class);
                 i.putExtra("noteId", position);
                 startActivity(i);
                // As the item in list view is clicked ,  we automatically get the position,
            }
        });
    }
}
