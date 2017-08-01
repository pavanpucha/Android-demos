package com.pucha.pavan.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import static com.pucha.pavan.notes.MainActivity.notes;

public class EditYourNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_note);
        EditText editText = (EditText) findViewById(R.id.editText);
        //getting content from intent
        Intent i = getIntent();
        int noteId = i.getIntExtra("noteId",-1);
        if(noteId != -1){
           editText.setText(MainActivity.notes.get(noteId));

        }
    }

}
