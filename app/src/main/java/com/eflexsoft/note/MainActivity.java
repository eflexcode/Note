package com.eflexsoft.note;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.eflexsoft.note.adapter.NoteAdapter;
import com.eflexsoft.note.databinding.ActivityMainBinding;
import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.viewmodel.NoteViewModel;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    NoteViewModel viewModel;
    NoteAdapter noteAdapter;

    List<Object> objectList = new ArrayList<>();
    AdLoader adLoader;
    boolean isFistTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recycleView.setHasFixedSize(true);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL);

        binding.recycleView.setLayoutManager(layoutManager);
        binding.recycleView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter(this);
        binding.recycleView.setAdapter(noteAdapter);
//        c = findViewById(R.id.c);
        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        setSupportActionBar(binding.toolbar);
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                viewModel.delete(noteAdapter.note(viewHolder.getAdapterPosition()));
//            }
//        }).attachToRecyclerView(binding.recycleView);

        adLoader = new AdLoader.Builder(this, "ca-app-pub-9552597639357298/7138206538")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

//                        if (isDestroyed()) {
//                            unifiedNativeAd.destroy();
//                        } else {
                        objectList.add(unifiedNativeAd);

                        if (!adLoader.isLoading()) {
                            noteAdapter.submitList(objectList);
                            noteAdapter.notifyItemInserted(objectList.size() - 1);
                        }
//                        }
                    }
                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        noteAdapter.submitList(objectList);
                    }
                }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();

        adLoader.loadAds(new AdRequest.Builder().build(), 4);

        viewModel.listLiveData.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                if (isFistTime) {
                    objectList.addAll(notes);
                    noteAdapter.submitList(objectList);
                    isFistTime = false;
                } else {

                    objectList.clear();
                    objectList.addAll(notes);
                    adLoader.loadAds(new AdRequest.Builder().build(), 4);

                    noteAdapter.submitList(objectList);
                }

                if (notes.isEmpty()) {
                    binding.c.setVisibility(View.VISIBLE);
                } else {
                    binding.c.setVisibility(View.GONE);
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Confirm Delete")
                        .setMessage("are you sure want to delete all items from your Notes? for this cannot be undone")
                        .setPositiveButton("Nope", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yap", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.deleteAll();
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
}
