package com.eflexsoft.note;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<Note> noteList = new ArrayList<>();

    Context context;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        final Note note = noteList.get(position);

        viewHolder.date.setText(note.getDate());
        viewHolder.body.setText(note.getBody());
        viewHolder.subject.setText(note.getSubject());
        viewHolder.priority.setText(String.valueOf(note.getPriority()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AddEditNoteActivity.class);
                intent.putExtra("subject",note.getSubject());
                intent.putExtra("body",note.getBody());
                intent.putExtra("priority",note.getPriority());
                intent.putExtra("id",note.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public Note note(int position){
        return noteList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject;
        TextView body;
        TextView date;
        TextView priority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.subject);
            body = itemView.findViewById(R.id.body);
            date = itemView.findViewById(R.id.date);
            priority = itemView.findViewById(R.id.priority);

        }
    }
}
