package com.eflexsoft.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.eflexsoft.note.databinding.ActivityNoteDetailBinding;
import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.viewmodel.NoteViewModel;

public class NoteDetailActivity extends AppCompatActivity {


    Intent intent;
    String updateSubject;
    String updateBody;
    int updatePriority;
    String date;
    int id;
    Note note;
    NoteViewModel noteViewModel;
    ActivityNoteDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_note_detail);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_detail);

        setSupportActionBar(binding.toolbar);
        setTitle("");

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        binding.toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        intent = getIntent();
        updateSubject = intent.getStringExtra("subject");
        updateBody = intent.getStringExtra("body");
        updatePriority = intent.getIntExtra("priority", 0);
        date = intent.getStringExtra("date");
        id = intent.getIntExtra("id", -1);
//        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        note = new Note(updateSubject, updateBody, date, updatePriority);
//        note.setId(id);
        binding.setNote(note);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.details_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("are you sure want to delete this item from your Notes? for this cannot be undone")
                        .setPositiveButton("Nope", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yap", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                note.setId(id);
                                noteViewModel.delete(note);
                                finish();
                            }
                        });

                AlertDialog dialog = alertDialog.create();

                dialog.show();

                break;
            case R.id.edit:

                Intent intent = new Intent(NoteDetailActivity.this, AddEditNoteActivity.class);
                intent.putExtra("subject", note.getSubject());
                intent.putExtra("body", note.getBody());
                intent.putExtra("date", note.getDate());
                intent.putExtra("priority", note.getPriority());
                intent.putExtra("id", id);
                startActivityForResult(intent, 3);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK) {

            String subject = data.getStringExtra("subject");
            String body = data.getStringExtra("body");
            int priority = data.getIntExtra("priority", 0);
            String date = data.getStringExtra("date");
            int id = data.getIntExtra("id", -1);

//            Toast.makeText(this, subject, Toast.LENGTH_SHORT).show();

            note = new Note(subject, body, date, priority);
            note.setId(id);



            binding.setNote(note);
//            binding.body.setText();
        }
    }
}