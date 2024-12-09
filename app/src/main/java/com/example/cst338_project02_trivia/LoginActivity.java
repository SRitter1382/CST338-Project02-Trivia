package com.example.cst338_project02_trivia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338_project02_trivia.database.Database;
import com.example.cst338_project02_trivia.database.entities.UserEntity;
import com.example.cst338_project02_trivia.databinding.ActivityLoginBinding;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private Database database;
    private ActivityLoginBinding binding;

    private static UserEntity userEntity;

    private ArrayList<UserEntity> allUserInfo;

    private ArrayList<UserEntity> userInfoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {
        String username = binding.usernameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();
        int incorrectUsernameCount = 1;
        int incorrectPasswordCount = 1;

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Username or password can't be blank!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            userInfoArrayList = new ArrayList<>();
            database = new Database(LoginActivity.this);

            userInfoArrayList = database.readUserEntities();

            if(userInfoArrayList.isEmpty()){
                Toast.makeText(this, "ArrayList is empty", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "ArrayList size " + userInfoArrayList.size(), Toast.LENGTH_SHORT).show();
                Log.d(MainActivity.TAG, "For Loop begin");
                //Crashing on for-loop if password is incorrect
                for (int i = 1; i <= userInfoArrayList.size(); i++){
                    if(username.equals(userInfoArrayList.get(i).getUsername())){
                        if(password.equals(userInfoArrayList.get(i).getPassword())){
                            //Toast.makeText(this, "$$$ LOGIN SUCCESSFUL $$$", Toast.LENGTH_SHORT).show();
                            Intent intent = TriviaMainMenu.triviaMenuIntentFactory(getApplicationContext());
                            startActivity(intent);
                        } else {
                            incorrectPasswordCount += 1;
                        }
                    } else{
                        incorrectUsernameCount += 1;
                    }
                }
                Log.d(MainActivity.TAG, "For Loop end");
                if (incorrectUsernameCount == userInfoArrayList.size() ||
                        incorrectPasswordCount == userInfoArrayList.size()){
                    Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                    startActivity(intent);
                }
            }
        }
    }

    static Intent loginIntentFactory (Context context){
        return new Intent(context, LoginActivity.class);
    }
}