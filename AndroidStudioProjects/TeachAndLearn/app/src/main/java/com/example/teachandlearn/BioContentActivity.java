package com.example.teachandlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BioContentActivity extends AppCompatActivity {
    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private int currentQuestionIndex = 0; // Track the current question index
    private String[] questions = {"Question 1? What is the difference between a server and a client in computer networking?", "Question 2? What is the purpose of a protocol in computer networking?", "Question 3? What is the difference between a local area network (LAN) and a wide area network (WAN)?"}; // Replace with your questions
    private String[][] options = {
            {" A server provides resources to a client, while a client requests resources from a server", " A server and a client are the same thing", "A server requests resources from a client, while a client provides resources to a server"}, // Replace with respective options for Question 1
            {"To secure network communication", "To optimize network performance", "To increase network speed "}, // Replace with respective options for Question 2
            {"A LAN is a smaller network, while a WAN is a larger network ", " A LAN is a network that uses wired connections, while a WAN is a network that uses wireless connections ", "A LAN is a network that spans a larger geographic area, while a WAN is a network that spans a smaller geographic area"}  // Replace with respective options for Question 3
    };
    private String[] correctAnswers = {"A server provides resources to a client, while a client requests resources from a server", "To secure network communication", "A LAN is a network that spans a larger geographic area, while a WAN is a network that spans a smaller geographic area"}; // Replace with correct answers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_content);

        Intent intent = getIntent();
        String receivedMessage = intent.getStringExtra("CONTENT_KEY");

        TextView displayMessageTextView = findViewById(R.id.display); // Replace with your TextView's ID

// Set the received message to the TextView
        if (displayMessageTextView != null) {
            displayMessageTextView.setText(receivedMessage);
        }

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);

        displayQuestion();


    }
    public void onNextClick(View view) {
        int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();

        if (selectedOptionId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            String selectedAnswer = selectedRadioButton.getText().toString();

            // Check if the selected answer is correct
            if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
                showToast("Correct!");
                // Handle score or any other action for correct answer
            } else {
                showToast("Incorrect!");
                // Handle action for incorrect answer
            }

            // Move to the next question
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                displayQuestion();
            } else {
                // End of quiz, handle accordingly (e.g., show results)
                displayResults();
                // Add code to display quiz results or navigate to result screen
            }
        } else {
            showToast("Please select an answer.");
        }
    }


    private void displayQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);

        // Clear previous radio button selections
        optionsRadioGroup.clearCheck();
        optionsRadioGroup.removeAllViews();

        // Add radio buttons for the current question's options
        for (String option : options[currentQuestionIndex]) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsRadioGroup.addView(radioButton);
        }
    }

    // private void showToast(String message) {
    //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    //}

    private void displayResults() {
        Intent intent = new Intent(this, ResultActivity.class);
        int totalQuestions = questions.length;
        int correctAnswersCount = calculateCorrectAnswers();
        int incorrectAnswersCount = totalQuestions - correctAnswersCount;

        intent.putExtra("totalQuestions", totalQuestions);
        intent.putExtra("correctAnswersCount", correctAnswersCount);
        intent.putExtra("incorrectAnswersCount", incorrectAnswersCount);

        startActivity(intent);
        finish();
    }
    private int calculateCorrectAnswers() {
        int correctCount = 0;
        RadioGroup optionsRadioGroup = findViewById(R.id.optionsRadioGroup);

        for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) optionsRadioGroup.getChildAt(i);

            if (radioButton.isChecked()) {
                String selectedAnswer = radioButton.getText().toString();
                if (selectedAnswer.equals(correctAnswers[i])) {
                    correctCount++;
                }
            }
        }
        return correctCount;
    }





    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}