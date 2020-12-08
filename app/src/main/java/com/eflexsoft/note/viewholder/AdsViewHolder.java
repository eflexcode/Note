package com.eflexsoft.note.viewholder;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eflexsoft.nativeads.NativeTemplateStyle;
import com.eflexsoft.note.R;
import com.eflexsoft.note.databinding.AdTemplateBinding;
import com.eflexsoft.note.databinding.NoteItem2Binding;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class AdsViewHolder extends RecyclerView.ViewHolder {

  public AdTemplateBinding binding;

    public AdsViewHolder(@NonNull AdTemplateBinding binding) {
        super(binding.getRoot());

        this.binding = binding;



    }

    public void setUnifiedNativeAds(UnifiedNativeAd ads){
        binding.myTemplate.setNativeAd(ads);
    }

}