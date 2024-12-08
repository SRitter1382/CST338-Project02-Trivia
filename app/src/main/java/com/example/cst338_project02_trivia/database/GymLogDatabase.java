package com.example.cst338_project02_trivia.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cst338_project02_trivia.MainActivity;
import com.example.cst338_project02_trivia.database.entities.GymLog;
import com.example.cst338_project02_trivia.database.typeConverters.LocalDataTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {GymLog.class}, version = 1, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {
    public static final String GYM_LOG_TABLE = "GymLogTable";
    
    private static final String DATABASE_NAME = "GymLog_database";
    private static volatile GymLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static GymLogDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (GymLogDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GymLogDatabase.class
                    , DATABASE_NAME).fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED");
        }
    };

    public abstract GymLogDAO gymLogDAO();
}
