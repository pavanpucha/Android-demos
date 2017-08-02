package com.pucha.pavan.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.pucha.pavan.notes", Context.MODE_PRIVATE);
        set = sharedPreferences.getStringSet("notes", null);
        notes.clear();
        if (set != null) {

            notes.addAll(set);
        } else {
            //  notes.clear();
            notes.add("example Note");
            set = new HashSet<String>();
            set.addAll(notes);
            sharedPreferences.edit().putStringSet("notes", set).apply();
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*
            @param position : Position in the view
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(getApplicationContext(), EditYourNote.class);
                i.putExtra("noteId", position);
                startActivity(i);
                // As the item in list view is clicked ,  we automatically get the position,
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int what) {
                                notes.remove(position);
                                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("com.pucha.pavan.notes", Context.MODE_PRIVATE);

                                if (set == null) {
                                    set = new HashSet<String>();
                                } else {
                                    set.clear();
                                }

                                set.addAll(notes);
                                sharedPreferences.edit().remove("notes").apply();
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuID = menuItem.getItemId();
        if (menuID == R.id.Plus) {
            notes.add("");
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.pucha.pavan.notes", Context.MODE_PRIVATE);

            if (set == null) {


                set = new HashSet<String>();
            }
            else {
                set.clear();
            }

            set.addAll(notes);
            arrayAdapter.notifyDataSetChanged();

            sharedPreferences.edit().remove("notes").apply();
            sharedPreferences.edit().putStringSet("notes", set).apply();
            Intent i = new Intent(getApplicationContext(), EditYourNote.class);
            i.putExtra("noteId", notes.size() - 1);
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}

