package com.example.cst338_project02_trivia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338_project02_trivia.database.TriviaRepository;
import com.example.cst338_project02_trivia.database.entities.User;
import com.example.cst338_project02_trivia.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.cst338_project02_trivia.MAIN_ACTIVITY_USER_ID";
    public static final String TAG = "DAC_GYMLOG";
    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;
    private TriviaRepository repository;
    private int loggedInUserId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = TriviaRepository.getRepository(getApplication());
        // TODO: Would want to do a login function here

        // TODO: Sending us to login screen immediately for testing CHANGE THIS TO CONTINUE
        if(loggedInUserId == LOGGED_OUT) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
    }

    static Intent MainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;

    }
}