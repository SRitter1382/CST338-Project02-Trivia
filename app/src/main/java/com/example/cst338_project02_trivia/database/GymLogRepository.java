package com.example.cst338_project02_trivia.database;

import android.app.Application;
import android.util.Log;

import com.example.cst338_project02_trivia.MainActivity;
import com.example.cst338_project02_trivia.database.entities.GymLog;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> allLogs;
    public GymLogRepository(Application application){
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.allLogs = this.gymLogDAO.getAllRecords();
    }

    public ArrayList<GymLog> getAllLogs(){
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(new Callable<ArrayList<GymLog>>() {
            @Override
            public ArrayList<GymLog> call() throws Exception {
                return gymLogDAO.getAllRecords();
            }
        });
        try {
            return future.get();
        } catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem in executor when getting all GymLogs in Repository");
        }
        return null;
    }

    public void insertGymLog(GymLog gymLog){
        GymLogDatabase.databaseWriteExecutor.execute(() ->
                {
                    gymLogDAO.insert(gymLog);
                }
                );
    }
}
