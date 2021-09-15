package com.eflexsoft.note.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.repository.Repository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    Repository repository;
    public LiveData<List<Note>> listLiveData;
    public MutableLiveData<Integer> integerMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> clearSelected = new MutableLiveData<>();

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        listLiveData = repository.getListLiveData();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Note>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<Integer> getIntegerMutableLiveData() {
        return integerMutableLiveData;
    }

    public MutableLiveData<Boolean> getClearSelected() {
        return clearSelected;
    }
}
