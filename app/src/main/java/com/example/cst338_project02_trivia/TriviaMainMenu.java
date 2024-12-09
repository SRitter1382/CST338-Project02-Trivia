package com.example.cst338_project02_trivia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338_project02_trivia.databinding.ActivityTriviaMainMenuBinding;

public class TriviaMainMenu extends AppCompatActivity {

    ActivityTriviaMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTriviaMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    static Intent triviaMenuIntentFactory (Context context){
        return new Intent(context, TriviaMainMenu.class);
    }
}