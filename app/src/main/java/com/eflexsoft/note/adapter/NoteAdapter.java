package com.eflexsoft.note.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.eflexsoft.nativeads.NativeTemplateStyle;
import com.eflexsoft.note.NoteDetailActivity;
import com.eflexsoft.note.Utils;
import com.eflexsoft.note.databinding.AdTemplateBinding;
import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.R;
import com.eflexsoft.note.databinding.NoteItem2Binding;
import com.eflexsoft.note.viewholder.AdsViewHolder;
import com.eflexsoft.note.viewholder.NoteViewHolder;
import com.eflexsoft.note.viewmodel.NoteViewModel;
import com.eflexsoft.note.viewmodel.ShowAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteViewHolder> {

    Context context;
    public static final int TYPE_ADS = 0;
    public static final int TYPE_NORMAL = 1;

    int clickCount = 0;
    public int selectedcount = 0;

    List<Note> selectedNote = new ArrayList<>();
    NoteViewModel noteViewModel;

    public NoteAdapter(Context context) {
        super(itemCallback);
        this.context = context;
    }

    static DiffUtil.ItemCallback<Note> itemCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getClass().equals(newItem.getClass());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

//        if (TYPE_ADS == i) {
//            AdTemplateBinding binding = DataBindingUtil.inflate(inflater, R.layout.ad_template, viewGroup, false);
//            return new AdsViewHolder(binding);
//        }

        NoteItem2Binding binding = DataBindingUtil.inflate(inflater, R.layout.note_item2, viewGroup, false);

        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final NoteViewHolder holder, final int position) {

        final ShowAds showAds = new ViewModelProvider((ViewModelStoreOwner) context).get(ShowAds.class);
        noteViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(NoteViewModel.class);

        holder.binding.re.setBackground(Utils.getRandomColor());

        final Note note = (Note) getItem(position);

        if (note != null) {
            holder.binding.setNote(note);
        }

        holder.binding.ripple.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

//                Toast.makeText(context, note.getSubject(), Toast.LENGTH_SHORT).show();
                if (holder.binding.selectedItemIcon.getVisibility() == View.VISIBLE) {
                    selectedcount -= 1;
                    noteViewModel.integerMutableLiveData.setValue(selectedcount);
                    holder.binding.selectedItemIcon.setVisibility(View.GONE);
                    selectedNote.remove(note);

                } else {
                    selectedcount += 1;
                    holder.binding.selectedItemIcon.setVisibility(View.VISIBLE);
                    noteViewModel.integerMutableLiveData.setValue(selectedcount);
                    selectedNote.add(note);

                }
//               notifyItemChanged(position);
                return true;
            }
        });

        holder.binding.ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedcount > 0) {
                    if (holder.binding.selectedItemIcon.getVisibility() == View.VISIBLE) {
                        selectedcount -= 1;
                        noteViewModel.integerMutableLiveData.setValue(selectedcount);
                        holder.binding.selectedItemIcon.setVisibility(View.GONE);
                        selectedNote.remove(note);
                    } else {
                        selectedcount += 1;
                        holder.binding.selectedItemIcon.setVisibility(View.VISIBLE);
                        noteViewModel.integerMutableLiveData.setValue(selectedcount);
                        selectedNote.add(note);
                    }
                } else {

                    if (clickCount == 3) {
                        // show ads
                        showAds.booleanMutableLiveData.setValue(true);
                        clickCount = 0;
                    } else {

                        clickCount += 1;

                        Intent intent = new Intent(context, NoteDetailActivity.class);
                        intent.putExtra("subject", note.getSubject());
                        intent.putExtra("body", note.getBody());
                        intent.putExtra("date", note.getDate());
                        intent.putExtra("priority", note.getPriority());
                        intent.putExtra("id", note.getId());
                        context.startActivity(intent);
                    }
                }
            }
        });

        //when user cancel selection
        noteViewModel.getClearSelected().observe((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    holder.binding.selectedItemIcon.setVisibility(View.GONE);
                    selectedNote.clear();
                } else {
                    holder.binding.selectedItemIcon.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public Note note(int position) {
        return (Note) getItem(position);
    }

    public void deleteSelectedNote() {
        for (Note note : selectedNote) {
            noteViewModel.delete(note);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        NoteItem2Binding binding;

        public ViewHolder(@NonNull NoteItem2Binding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }

    static class NoteDetailsLookUp extends ItemDetailsLookup<Integer> {

        RecyclerView recyclerView;

        public NoteDetailsLookUp(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<Integer> getItemDetails(@NonNull MotionEvent e) {

            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());

            if (view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);

                if (viewHolder instanceof ViewHolder) {

//                    return ((ViewHolder) viewHolder).

                }

            }

            return null;
        }
    }
}
