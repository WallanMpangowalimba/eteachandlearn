package com.example.teachandlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView resultTextView = findViewById(R.id.resultTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);

        Intent intent = getIntent();
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        int correctAnswersCount = intent.getIntExtra("correctAnswersCount", 0);
        int incorrectAnswersCount = intent.getIntExtra("incorrectAnswersCount", 0);

        double percentage = ((double) correctAnswersCount / totalQuestions) * 100;

        // Update resultTextView to display the number of correct answers out of total questions
        resultTextView.setText(getString(R.string.quiz_results, correctAnswersCount, totalQuestions));

        // Update scoreTextView to display the score along with the percentage
        String scoreText = "Score: " + correctAnswersCount + " / " + totalQuestions + " (" + String.format("%.2f", percentage) + "%)";
        scoreTextView.setText(scoreText);

        redirectToFinalActivity(correctAnswersCount, totalQuestions, percentage);
    }
    private void redirectToFinalActivity(final int correctAnswersCount, final int totalQuestions, final double percentage) {
        // Redirect to FinalActivity after a 3-second delay (adjust the delay as needed)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ResultActivity.this, FinalActivity.class);
                intent.putExtra("correctAnswersCount", correctAnswersCount);
                intent.putExtra("totalQuestions", totalQuestions);
                intent.putExtra("percentage", percentage);
                startActivity(intent);
                finish(); // Finish the ResultsActivity
            }
        }, 3000); // 3 seconds delay
    }

}