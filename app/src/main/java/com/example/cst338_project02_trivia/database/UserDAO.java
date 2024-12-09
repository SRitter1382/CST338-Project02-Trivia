package com.example.cst338_project02_trivia.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338_project02_trivia.database.entities.User;

import java.util.List;

public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);
    // TODO: Do we need ability to delete?
//    @Delete
//    void delete(User user);
//    @Query("DELETE FROM " + TriviaDatabase.USER_TABLE)
//    void deleteAll();
    @Query("SELECT * FROM " + TriviaDatabase.USER_TABLE + " ORDER BY username ASC")
    LiveData<List<User>> getAlphabetizedUsers();

    @Query("SELECT * FROM " + TriviaDatabase.USER_TABLE + " WHERE username = :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + TriviaDatabase.USER_TABLE + " WHERE userid = :userId")
    LiveData<User> getUserByUserId(int userId);

}
