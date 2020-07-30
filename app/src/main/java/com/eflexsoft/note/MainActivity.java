package com.eflexsoft.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {

    NoteViewModel viewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    TextView c;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout container = findViewById(R.id.fragment_main);

        recyclerView = findViewById(R.id.recycleView);
        toolbar = findViewById(R.id.toolbar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);
        c = findViewById(R.id.c);
        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        setSupportActionBar(toolbar);

        if (container != null && container.getChildCount() < 1) {
            container.addView(new Banner(this), new FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER));
            StartAppAd.showAd(this);
        }
        StartAppSDK.init(this, "206233878", true);
        StartAppAd.showAd(this);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                viewModel.delete(noteAdapter.note(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        viewModel.listLiveData.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNoteList(notes);
                if (notes.isEmpty()) {
                    c.setVisibility(View.VISIBLE);
                } else {
                    c.setVisibility(View.GONE);
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
                viewModel.deleteAll();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        StartAppAd.showAd(this);
    }

    public void openAdd(View view) {
        startActivity(new Intent(this, AddEditNoteActivity.class));
    }
}
