package com.eflexsoft.note.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.note.databinding.NoteItem2Binding;

public class NoteViewHolder extends RecyclerView.ViewHolder {

  public NoteItem2Binding binding;

    public NoteViewHolder(@NonNull NoteItem2Binding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }
}