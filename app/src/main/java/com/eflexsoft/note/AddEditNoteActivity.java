package com.eflexsoft.note;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditNoteActivity extends AppCompatActivity {
    TextView subject;
    TextView body;
    NumberPicker priority;

    Intent intent;
    String updateSubject;
    String updateBody;
    int updatePriority;
    String date;
    int id;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        subject = findViewById(R.id.edit_subject);
        body = findViewById(R.id.edit_body);
        priority = findViewById(R.id.priority_pick);
        toolbar = findViewById(R.id.toolbar);
        priority.setMaxValue(100);
        priority.setMinValue(0);
        setSupportActionBar(toolbar);
        setTitle("");
        intent = getIntent();
        updateSubject = intent.getStringExtra("subject");
        updateBody = intent.getStringExtra("body");
        updatePriority = intent.getIntExtra("priority", 0);
//        date = intent.getStringExtra("date");
        id = intent.getIntExtra("id", -1);

        if (updateSubject != null) {
            subject.setText(updateSubject);
            body.setText(updateBody);
            priority.setValue(updatePriority);
        }

    }

    public void saveUpdate(View view) {
        Calendar calendar = Calendar.getInstance();

        String getDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        String getSubject = subject.getText().toString();
        String getBody = body.getText().toString();
        int getPriority = priority.getValue();

        NoteViewModel viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        if (getBody.isEmpty() || getSubject.isEmpty()) {
            Toast.makeText(this, "a filed is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (updateSubject == null) {
            viewModel.insert(new Note(getSubject, getBody, getDate, getPriority));
            finish();
        } else {
            Note note = new Note(getSubject, getBody, getDate, getPriority);
            note.setId(id);

            viewModel.update(note);
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_note, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveAsFile:

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                } else {

                    Toast.makeText(this, "saving", Toast.LENGTH_SHORT).show();
                    try {
                        String getSubject = subject.getText().toString();
                        String getBody = body.getText().toString();
                        int getPriority = priority.getValue();
                        if (getBody.isEmpty() || getSubject.isEmpty()) {
                            Toast.makeText(this, "a file is empty", Toast.LENGTH_SHORT).show();
                        } else {

                            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                            if (!root.exists()) {
                                root.mkdirs();
                            }
                            File file = new File(root, getSubject + ".txt");

                            FileWriter fileWriter = new FileWriter(file, true);

                            fileWriter.append(getBody + "/n priority " + getPriority);
                            fileWriter.flush();
                            fileWriter.close();
                            Toast.makeText(this, "file saved successfully", Toast.LENGTH_SHORT).show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.getMessage());
                    }
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private static final String TAG = "AddEditNoteActivity";

    public void finish(View view) {
        finish();
    }
}
