package com.eflexsoft.note;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;



import com.eflexsoft.note.adapter.NoteAdapter;
import com.eflexsoft.note.databinding.ActivityMainBinding;
import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.viewmodel.NoteViewModel;
import com.eflexsoft.note.viewmodel.ShowAds;
import com.google.android.gms.ads.LoadAdError;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NoteViewModel viewModel;
    NoteAdapter noteAdapter;
    ShowAds showAds;

    private ActionMode actionMode;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recycleView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ((position % 7) == 0) {
                    return 2;
                } else return 1;
            }
        });

        binding.recycleView.setLayoutManager(layoutManager);
        binding.recycleView.setHasFixedSize(false);

        noteAdapter = new NoteAdapter(this);
        binding.recycleView.setAdapter(noteAdapter);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        showAds = new ViewModelProvider(this).get(ShowAds.class);

        setSupportActionBar(binding.toolbar);

        viewModel.listLiveData.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.submitList(notes);

                if (notes.isEmpty()) {
                    binding.c.setVisibility(View.VISIBLE);
                } else {
                    binding.c.setVisibility(View.GONE);
                }
            }
        });

        showAds.getBooleanMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {


                }
            }
        });

        viewModel.getIntegerMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                enableActionMood();
                if (integer == 0) {
                    actionMode.finish();
                } else {
                    actionMode.setTitle(String.valueOf(integer));
                    actionMode.invalidate();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_All:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.Dialog)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure want to delete all items from your Notes? for this cannot be undone")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                viewModel.deleteAll();
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = alertDialog.create();

                dialog.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        adLoader.loadAds(new AdRequest.Builder().build(), 4);
    }

    public void openAdd(View view) {
        startActivity(new Intent(this, AddEditNoteActivity.class));

    }

    private void enableActionMood() {
        if (actionMode == null) {
            actionMode = startSupportActionMode(callback);
        }
    }

    private final ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.delete_selected, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.delete_selected) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.Dialog)
                        .setTitle("Confirm Delete")
                        .setMessage("Are you sure want to delete selected items from your Notes? for this cannot be undone")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                noteAdapter.deleteSelectedNote();
                                mode.finish();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = alertDialog.create();

                dialog.show();

                return true;

            } else return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            noteAdapter.selectedcount = 0;
            viewModel.clearSelected.setValue(true);
        }
    };

}


