package com.eflexsoft.note.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PriorityPickerViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> priorityNumber = new MutableLiveData<>();

    public PriorityPickerViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Integer> getPriorityNumber() {
        return priorityNumber;
    }

}
