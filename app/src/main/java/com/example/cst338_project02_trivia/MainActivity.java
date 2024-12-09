package com.example.cst338_project02_trivia;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338_project02_trivia.database.GymLogRepository;
import com.example.cst338_project02_trivia.database.entities.GymLog;
import com.example.cst338_project02_trivia.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private GymLogRepository repository;
    public final static String TAG = "DAC_GYMLOG";
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        /**
         binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

         binding.logButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        //Toast.makeText(MainActivity.this, "It worked", Toast.LENGTH_SHORT).show();
        getInformationFromDisplay();
        insertGymLogRecord();
        updateDisplay();
        }
        });*/
    }

    private void updateDisplay() {
        String currentInfo = binding.logDisplayTextView.getText().toString();
        String newDisplay = String.format(Locale.US, "Exercise:%s%nWeight:%.2f%nReps:%d%n=-=-=-=%n%s",
                mExercise, mWeight, mReps, currentInfo);
        binding.logDisplayTextView.setText(newDisplay);
    }


    /**
    private void getInformationFromDisplay() {
        mExercise = binding.exerciseInputEditText.getText().toString();

        try {
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from weight edit text");
        }

        try {
            mReps = Integer.parseInt(binding.repsInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from reps edit text");
        }
    }&/

    private void insertGymLogRecord(){
        GymLog log = new GymLog(mExercise,mWeight,mReps);
        Log.d(TAG, "mExercise: " + mExercise);
        Log.d(TAG, "mWeight: " + mWeight);
        Log.d(TAG, "mReps: " + mReps);
        Log.d(TAG,"Log: " + log.toString());
        //Error here
        repository.insertGymLog(log);
    }*/
}