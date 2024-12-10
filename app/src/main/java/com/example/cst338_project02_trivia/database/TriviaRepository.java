package com.example.cst338_project02_trivia.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cst338_project02_trivia.MainActivity;
import com.example.cst338_project02_trivia.database.entities.User;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TriviaRepository {
    private final UserDAO userDAO;
    private static TriviaRepository repository;
    private TriviaRepository(Application application) {
        TriviaDatabase db = TriviaDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
    }

    public static TriviaRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<TriviaRepository> future = TriviaDatabase.databaseWriteExecutor.submit(
                new Callable<TriviaRepository>() {
                    @Override
                    public TriviaRepository call() throws Exception {
                        return new TriviaRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG,"Problem getting TriviaRepository, thread error.");
        }
        return null;
    }

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAlphabetizedUsers();
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int id) {
        return userDAO.getUserByUserId(id);
    }

    public void insert(User... user) {
        TriviaDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

}
