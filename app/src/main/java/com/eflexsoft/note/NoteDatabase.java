package com.eflexsoft.note;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDatabase;
    private static final String dbName = "NoteDatabase";
    abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Application application) {
        if (noteDatabase == null) {
            noteDatabase = Room.databaseBuilder(application, NoteDatabase.class,dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return noteDatabase;
    }

}
