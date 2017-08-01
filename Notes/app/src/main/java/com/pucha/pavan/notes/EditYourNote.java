package com.pucha.pavan.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

import static android.os.Build.VERSION_CODES.M;
import static com.pucha.pavan.notes.MainActivity.notes;
import static com.pucha.pavan.notes.MainActivity.set;

public class EditYourNote extends AppCompatActivity implements TextWatcher {
    int noteId;
   // SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_note);
        EditText editText = (EditText) findViewById(R.id.editText);
        //getting content from intent
        Intent i = getIntent();
        int noteId = i.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));

        }

        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        MainActivity.notes.set(noteId, String.valueOf(charSequence));
        MainActivity.arrayAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.pucha.pavan.notes", Context.MODE_PRIVATE);
        if(MainActivity.set == null){
            MainActivity.set = new HashSet<String>();
        }else
            MainActivity.set.clear();

        MainActivity.set.addAll(MainActivity.notes);
        sharedPreferences.edit().putStringSet("notes", set).apply();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}