package com.eflexsoft.note;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.eflexsoft.note.databinding.NoteItem2Binding;
import com.eflexsoft.note.databinding.NoteItemBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.ViewHolder> {

//    List<Note> noteList = new ArrayList<>();

    Context context;

    protected NoteAdapter(Context context) {
        super(noteItemCallback);
        this.context = context;
    }

//    public NoteAdapter(Context context) {
//        super();
//        this.context = context;
//    }

//    public void setNoteList(List<Note> noteList) {
//        this.noteList = noteList;
//        notifyDataSetChanged();
//    }


    static DiffUtil.ItemCallback<Note> noteItemCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId()
                    && oldItem.getSubject().equals(newItem.getSubject())
                    && oldItem.getBody().equals(newItem.getBody())
                    && oldItem.getDate().equals(newItem.getDate())
                    && oldItem.getPriority() == newItem.getPriority();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {

            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        NoteItem2Binding binding = DataBindingUtil.inflate(inflater, R.layout.note_item2, viewGroup, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        final Note note = getItem(position);

        if (note != null) {
            viewHolder.binding.setNote(note);
        }

//        viewHolder.date.setText(note.getDate());
//        viewHolder.body.setText(note.getBody());
//        viewHolder.subject.setText(note.getSubject());
//        viewHolder.priority.setText(String.valueOf(note.getPriority()));

//        viewHolder.binding.re.setBackground(Utils.getRandomColor());
//        viewHolder.binding.card.setBackground(Utils.getRandomColor());
//        viewHolder.binding.card.setRadius(5f);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteDetailActivity.class);
                intent.putExtra("subject", note.getSubject());
                intent.putExtra("body", note.getBody());
                intent.putExtra("date", note.getDate());
                intent.putExtra("priority", note.getPriority());
                intent.putExtra("id", note.getId());
                context.startActivity(intent);
            }
        });

//        if (position == noteList.size() - 1) {

//            CardView.LayoutParams layoutParams = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 350);
//            layoutParams.setMargins(0, 30, 0, 30);
//
//            viewHolder.binding.card.setLayoutParams(layoutParams);
//            viewHolder.binding.card.setPadding(0,0,0,80);

//        }

    }

//    @Override
//    public int getItemCount() {
//        return noteList.size();
//    }

    public Note note(int position) {
        return getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

//        TextView subject;
//        TextView body;
//        TextView date;
////        TextView priority;

        NoteItem2Binding binding;

        public ViewHolder(@NonNull NoteItem2Binding binding) {
            super(binding.getRoot());

            this.binding = binding;

//            subject = itemView.findViewById(R.id.subject);
//            body = itemView.findViewById(R.id.body);
//            date = itemView.findViewById(R.id.date);
//            priority = itemView.findViewById(R.id.priority);

        }
    }
}
