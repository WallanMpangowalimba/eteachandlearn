package com.example.teachandlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        TextView finalResultTextView = findViewById(R.id.finalResultTextView);

        // Retrieve data passed from ResultsActivity via Intent extras
        Intent intent = getIntent();
        int correctAnswersCount = intent.getIntExtra("correctAnswersCount", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        double percentage = intent.getDoubleExtra("percentage", 0.0);

        // Display or process the received data in FinalActivity
        String finalResultText = "Final Result:\nCorrect Answers: " + correctAnswersCount +
                "\nTotal Questions: " + totalQuestions +
                "\nPercentage: " + String.format("%.2f", percentage) + "%";

        finalResultTextView.setText(finalResultText);

        Button button = findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinalActivity.this, FindCoursesActivity.class));
            }
        });
    }
}