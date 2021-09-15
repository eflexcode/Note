package com.eflexsoft.note.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eflexsoft.note.R;
import com.eflexsoft.note.databinding.FragmentPickPriorityBinding;
import com.eflexsoft.note.viewmodel.PriorityPickerViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;


public class PickPriorityFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    FragmentPickPriorityBinding binding;
    PriorityPickerViewModel viewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pick_priority, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(PriorityPickerViewModel.class);

        binding.priorityPick.setMaxValue(100);
        binding.priorityPick.setMinValue(0);

        binding.apply.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.apply) {
            int newPriority = binding.priorityPick.getValue();
            viewModel.priorityNumber.setValue(newPriority);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, 2000);


        }
    }
}