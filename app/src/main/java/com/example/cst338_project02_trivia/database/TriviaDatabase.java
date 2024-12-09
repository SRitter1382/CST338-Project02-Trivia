package com.example.cst338_project02_trivia.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cst338_project02_trivia.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class TriviaDatabase extends RoomDatabase {

    public static final String USER_TABLE = "user_table" ;
    private static final String DATABASE_NAME = "trivia_database";
    public abstract UserDAO userDAO();
    private static volatile TriviaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TriviaDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TriviaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TriviaDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
