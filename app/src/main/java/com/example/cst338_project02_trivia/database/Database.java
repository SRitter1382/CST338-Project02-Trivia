package com.example.cst338_project02_trivia.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cst338_project02_trivia.database.entities.UserEntity;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "users";
    private static final String DB_NAME = "userdb";
    private static final int DB_VERSION = 1;
    private static final String USER_ID = "userid";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static UserEntity userEntity;


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_NAME + " ( " +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USERNAME + " TEXT, " +
                PASSWORD + " TEXT )";

        db.execSQL(query);
    }

    public void addNewUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(USERNAME, username);
        values.put(PASSWORD, password);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<UserEntity> readUserEntities(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCursors = db.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        ArrayList<UserEntity> userEntityArrayList = new ArrayList<>();

        if(cursorCursors.moveToFirst()){
            do {
                userEntityArrayList.add(new UserEntity(cursorCursors.getString(1),
                        cursorCursors.getString(2)));
            } while (cursorCursors.moveToNext());
        }
        cursorCursors.close();
        //Log.i(MainActivity.TAG, "Is it null: " + userEntityArrayList.toString());
        return userEntityArrayList;
    }
}
