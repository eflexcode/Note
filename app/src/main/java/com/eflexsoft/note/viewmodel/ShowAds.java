package com.eflexsoft.note.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.security.PublicKey;

public class ShowAds extends AndroidViewModel {

    public MutableLiveData<Boolean> booleanMutableLiveData = new MutableLiveData<>();

    public ShowAds(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getBooleanMutableLiveData() {
        return booleanMutableLiveData;
    }
}
