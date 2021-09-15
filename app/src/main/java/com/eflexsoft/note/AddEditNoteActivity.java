package com.eflexsoft.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.eflexsoft.note.databinding.ActivityAddEditNoteBinding;
import com.eflexsoft.note.fragment.PickPriorityFragment;
import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.viewmodel.NoteViewModel;
import com.eflexsoft.note.viewmodel.PriorityPickerViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class AddEditNoteActivity extends AppCompatActivity {


    Intent intent;
    String updateSubject;
    String updateBody;
    int updatePriority;
    String date;
    int id;
    ActivityAddEditNoteBinding binding;
    PriorityPickerViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_note);

        setSupportActionBar(binding.toolbar);
        intent = getIntent();
        updateSubject = intent.getStringExtra("subject");
        updateBody = intent.getStringExtra("body");
        updatePriority = intent.getIntExtra("priority", 0);
//        date = intent.getStringExtra("date");
        id = intent.getIntExtra("id", -1);

        viewModel = new ViewModelProvider(this).get(PriorityPickerViewModel.class);

        viewModel.getPriorityNumber().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                updatePriority = integer;
            }
        });

        if (updateSubject != null) {
            binding.editSubject.setText(updateSubject);
            binding.editBody.setText(updateBody);
            viewModel.priorityNumber.setValue(updatePriority);
        }

//        Toast.makeText(this, " "+updatePriority, Toast.LENGTH_SHORT).show();

        if (updateSubject == null) {
            binding.title.setText("New Note");
        } else {
            binding.title.setText("Update Note");
        }
        binding.toolbar.setNavigationIcon(R.drawable.ic_left_arrow);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        if (container != null && container.getChildCount() < 1) {
//            container.addView(new Banner(this), new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER));
//            StartAppAd.showAd(this);
//        }
//        StartAppSDK.init(this, "206233878", true);
//        StartAppAd.showAd(this);

    }

    public void saveUpdate(View view) {
        Calendar calendar = Calendar.getInstance();

        String getDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        String getSubject = binding.editSubject.getText().toString();
        String getBody = binding.editBody.getText().toString();
//        int getPriority = binding.priorityPick.getValue();

        NoteViewModel viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        if (getSubject.trim().isEmpty()) {
            Snackbar.make(view, "Hey there you haven't written your notes 'Title' ", Snackbar.LENGTH_INDEFINITE).setAction("got it", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
            return;
        }

        if (getBody.trim().isEmpty()) {
            Snackbar.make(view, "Hey there you haven't written your notes 'Details' ", Snackbar.LENGTH_INDEFINITE).setAction("got it", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
            return;
        }

        if (updateSubject == null) {
            viewModel.insert(new Note(getSubject, getBody, getDate, updatePriority));
            finish();
        } else {
            Note note = new Note(getSubject, getBody, getDate, updatePriority);
            note.setId(id);
            viewModel.update(note);
            Intent intent = new Intent();
            intent.putExtra("subject", getSubject);
            intent.putExtra("body", getBody);
            intent.putExtra("date", getDate);
            intent.putExtra("priority", updatePriority);
            intent.putExtra("id", id);
            setResult(RESULT_OK, intent);
            finish();
        }


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.save_note, menu);
//        return true;
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.saveAsFile:
//
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                    }
//                } else {
//
//                    Toast.makeText(this, "saving", Toast.LENGTH_SHORT).show();
//                    try {
//                        String getSubject = binding.editSubject.getText().toString();
//                        String getBody = binding.editBody.getText().toString();
//                        int getPriority = binding.priorityPick.getValue();
//                        if (getBody.isEmpty() || getSubject.isEmpty()) {
//                            Toast.makeText(this, "a file is empty", Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
//                            if (!root.exists()) {
//                                root.mkdirs();
//                            }
//                            File file = new File(root, getSubject + ".txt");
//
//                            FileWriter fileWriter = new FileWriter(file, true);
//
//                            fileWriter.append(getBody + "/n priority " + getPriority);
//                            fileWriter.flush();
//                            fileWriter.close();
//                            Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
//
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.d(TAG, e.getMessage());
//                    }
//                }
//                break;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        return true;
//    }

    private static final String TAG = "AddEditNoteActivity";

    public void openPriorityPicker(View view) {
        PickPriorityFragment priorityFragment = new PickPriorityFragment();
        priorityFragment.show(getSupportFragmentManager(), "y");
    }

//    public void finish(View view) {
//        finish();
//    }
}
