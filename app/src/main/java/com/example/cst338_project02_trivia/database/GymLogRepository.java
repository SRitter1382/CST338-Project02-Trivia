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
    private GymLogDAO gymlogDAO;
    private ArrayList<GymLog> allLogs;

    private static GymLogRepository repository;

    private GymLogRepository(Application application){
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymlogDAO = db.gymlogDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymlogDAO.getAllRecords();
    }

    public ArrayList<GymLog> getAllLogs(){
        Future <ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(new Callable<ArrayList<GymLog>>() {
            @Override
            public ArrayList<GymLog> call() throws Exception {
                return (ArrayList<GymLog>) gymlogDAO.getAllRecords();
            }
        });
        try{
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem when getting all GymLogs in repository");
        }
        return null;
    }

    public static GymLogRepository getRepository (Application application){
        if(repository != null){
            return repository;
        }
        Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<GymLogRepository>() {
                    @Override
                    public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);

                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error");
        }
        return null;
    }
    public void insertGymLog(GymLog gymlog){
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
            gymlogDAO.insert(gymlog);
        });
    }
}
