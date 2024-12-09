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
import com.example.cst338_project02_trivia.database.entities.User;
import com.example.cst338_project02_trivia.database.typeConverters.LocalDataTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@TypeConverters(LocalDataTypeConverter.class)
@Database(entities = {GymLog.class, User.class}, version = 1, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {

    public static final String GYM_LOG_TABLE = "gymlogtable";
    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "GymLogdatabase";
    private static volatile GymLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static GymLogDatabase getDatabase(final Context context){
        Log.d(MainActivity.TAG, "What is going on");
        if(INSTANCE == null){
            synchronized (GymLogDatabase.class){
                if(INSTANCE == null){
                    Log.d(MainActivity.TAG, "What is going on");
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
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testuser1 = new User("testuser1", "testuser1");
                dao.insert(testuser1);
            });
        }
    };

    public abstract GymLogDAO gymLogDAO();

    public abstract UserDAO userDAO();
}
