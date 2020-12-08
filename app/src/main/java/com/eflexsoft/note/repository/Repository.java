package com.eflexsoft.note.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.eflexsoft.note.model.Note;
import com.eflexsoft.note.room.NoteDao;
import com.eflexsoft.note.room.NoteDatabase;

import java.util.List;

public class Repository {

    NoteDao dao;

    LiveData<List<Note>> listLiveData;

    public Repository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        dao = noteDatabase.noteDao();
        listLiveData = dao.getAll();
    }

    public void insert(Note note) {
        new InsertASync(dao).execute(note);
    }

    public void update(Note note) {
        new UpdateAsync(dao).execute(note);
    }

    public void delete(Note note) {
        new DeleteAsync(dao).execute(note);
    }

    public void deleteAll() {
        new DeleteAllAsync(dao).execute();
    }

    public LiveData<List<Note>> getListLiveData() {
        return listLiveData;
    }

    class InsertASync extends AsyncTask<Note, Void, Void> {

        NoteDao dao;

        public InsertASync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(notes[0]);
            return null;
        }
    }

    class UpdateAsync extends AsyncTask<Note, Void, Void> {
        NoteDao dao;

        public UpdateAsync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.update(notes[0]);
            return null;
        }

    }

    class DeleteAsync extends AsyncTask<Note, Void, Void> {
        NoteDao dao;

        public DeleteAsync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.delete(notes[0]);
            return null;
        }

    }

    class DeleteAllAsync extends AsyncTask<Void, Void, Void> {
        NoteDao dao;

        public DeleteAllAsync(NoteDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

}
