package com.example.teachandlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class TeacherActivity extends AppCompatActivity {
    Button button;
    Button logoutButton;
    EditText contentEditText;
    Button uploadButton;
    private EditText noteEditText;
    private EditText nameEditText;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        button = findViewById(R.id.btn_logout);
        // Write a message to the database

        noteEditText = (EditText) findViewById(R.id.editTextText);
        nameEditText = (EditText) findViewById(R.id.editTextText2);
        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = noteEditText.getText().toString();
                String name = nameEditText.getText().toString();

                if(note.isEmpty()){
                    noteEditText.setError("must no be empty");
                    return;
                }
                if(name.isEmpty()){
                    nameEditText.setError("must no be empty");
                    return;

                }
                addToDb(note, name);
            }
        });

    }

    private void addToDb(String note, String name) {
        HashMap<String, Object> noteHashmap = new HashMap<>();
        noteHashmap.put("note", note);
        noteHashmap.put("name", name);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notesRef = database.getReference("notes");
        String key = notesRef.push().getKey();
        noteHashmap.put("key", key);
        notesRef.child(key).setValue(noteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(TeacherActivity.this, "added to database", Toast.LENGTH_SHORT).show();
                noteEditText.getText().clear();
                nameEditText.getText().clear();

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}