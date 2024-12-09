package com.example.cst338_project02_trivia.database;

import android.app.Application;
import android.util.Log;

import com.example.cst338_project02_trivia.MainActivity;
import com.example.cst338_project02_trivia.database.entities.GymLog;
import com.example.cst338_project02_trivia.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> allLogs;

    private UserDAO userDAO;

    private static GymLogRepository repository;
    private GymLogRepository(Application application){
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
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
            future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting log repository, thread error");
        }
        return null;
    }

    public ArrayList<GymLog> getAllLogs(){
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
            @Override
            public ArrayList<GymLog> call() throws Exception {
                return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
            }
        });
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem in executor when getting all GymLogs in Repository");
        }
        return null;
    }

    //Error doesn't come here
    public void insertGymLog(GymLog gymLog){
        Log.i(MainActivity.TAG, "GymLog: " + gymLog.toString());
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
                    gymLogDAO.insert(gymLog);
                }
                );
    }

    public void insertUser(User... user){
        GymLogDatabase.databaseWriteExecutor.execute(() -> {
                    userDAO.insert(user);
                }
        );
    }
}
