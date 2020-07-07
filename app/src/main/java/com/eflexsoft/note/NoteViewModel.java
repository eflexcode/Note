package com.eflexsoft.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    Repository repository;
    LiveData<List<Note>> listLiveData;

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

}
