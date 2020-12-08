package com.eflexsoft.note.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.eflexsoft.nativeads.NativeTemplateStyle;
import com.eflexsoft.note.NoteDetailActivity;
import com.eflexsoft.note.databinding.AdTemplateBinding;
import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.R;
import com.eflexsoft.note.databinding.NoteItem2Binding;
import com.eflexsoft.note.viewholder.AdsViewHolder;
import com.eflexsoft.note.viewholder.NoteViewHolder;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class NoteAdapter extends ListAdapter<Object, RecyclerView.ViewHolder> {

    Context context;
    public static final int TYPE_ADS = 0;
    public static final int TYPE_NORMAL = 1;

    public NoteAdapter(Context context) {
        super(itemCallback);
        this.context = context;
    }

    static DiffUtil.ItemCallback<Object> itemCallback = new DiffUtil.ItemCallback<Object>() {
        @Override
        public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return oldItem.getClass().equals(newItem.getClass());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return oldItem.equals(newItem);
        }
    };

//    static DiffUtil.ItemCallback<Note> noteItemCallback = new DiffUtil.ItemCallback<Note>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
//            return oldItem.getId() == newItem.getId()
//                    && oldItem.getSubject().equals(newItem.getSubject())
//                    && oldItem.getBody().equals(newItem.getBody())
//                    && oldItem.getDate().equals(newItem.getDate())
//                    && oldItem.getPriority() == newItem.getPriority();
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
//
//            return oldItem.equals(newItem);
//        }
//    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (TYPE_ADS == i) {
            AdTemplateBinding binding = DataBindingUtil.inflate(inflater, R.layout.ad_template, viewGroup, false);
            return new AdsViewHolder(binding);
        }

        NoteItem2Binding binding = DataBindingUtil.inflate(inflater, R.layout.note_item2, viewGroup, false);

        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        if (viewType == TYPE_ADS) {
            AdsViewHolder adsViewHolder = (AdsViewHolder) holder;
            NativeTemplateStyle nativeTemplateStyle = new NativeTemplateStyle.Builder()
                    .withMainBackgroundColor(new ColorDrawable(Color.parseColor("#171717")))
                    .withTertiaryTextTypefaceColor(Color.GRAY)
                    .withSecondaryTextTypefaceColor(Color.GRAY)
                    .withPrimaryTextTypefaceColor(Color.WHITE)
                    .build();

            adsViewHolder.binding.myTemplate.setStyles(nativeTemplateStyle);
            adsViewHolder.setUnifiedNativeAds((UnifiedNativeAd) getItem(position));
            return;
        }

        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
        final Note note = (Note) getItem(position);
        if (note != null) {
            noteViewHolder.binding.setNote(note);
        }
        noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof UnifiedNativeAd ? TYPE_ADS : TYPE_NORMAL;
    }

    //    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
//
//        final Note note = getItem(position);
//
//        if (note != null) {
//            viewHolder.binding.setNote(note);
//        }
//
////        viewHolder.date.setText(note.getDate());
////        viewHolder.body.setText(note.getBody());
////        viewHolder.subject.setText(note.getSubject());
////        viewHolder.priority.setText(String.valueOf(note.getPriority()));
//
////        viewHolder.binding.re.setBackground(Utils.getRandomColor());
////        viewHolder.binding.card.setBackground(Utils.getRandomColor());
////        viewHolder.binding.card.setRadius(5f);
//
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, NoteDetailActivity.class);
//                intent.putExtra("subject", note.getSubject());
//                intent.putExtra("body", note.getBody());
//                intent.putExtra("date", note.getDate());
//                intent.putExtra("priority", note.getPriority());
//                intent.putExtra("id", note.getId());
//                context.startActivity(intent);
//            }
//        });

//        if (position == noteList.size() - 1) {

//            CardView.LayoutParams layoutParams = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 350);
//            layoutParams.setMargins(0, 30, 0, 30);
//
//            viewHolder.binding.card.setLayoutParams(layoutParams);
//            viewHolder.binding.card.setPadding(0,0,0,80);

//        }

//    }

//    @Override
//    public int getItemCount() {
//        return noteList.size();
//    }

    public Note note(int position) {
        return (Note) getItem(position);
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
