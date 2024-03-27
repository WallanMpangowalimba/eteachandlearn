package com.example.teachandlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextInputEditText editTextEmail, getEditTextPassword, editFullName;
    Button btn_register;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textview;
    CheckBox isTeacherBox, isStudentBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        editFullName = findViewById(R.id.fullname);
        editTextEmail = findViewById(R.id.email);
        getEditTextPassword = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        textview = findViewById(R.id.loginNow);
        isTeacherBox = findViewById(R.id.isTeacher);
        isStudentBox = findViewById(R.id.isStudent);


        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // progressBar.setVisibility(view.VISIBLE);
                String email, password, fullname;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(getEditTextPassword.getText() );
                fullname = String.valueOf(editFullName.getText());

                isStudentBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(compoundButton.isChecked()){
                            isTeacherBox.setChecked(false);
                        }
                    }
                });

                isTeacherBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(compoundButton.isChecked()){
                            isStudentBox.setChecked(false);

                        }
                    }
                });


                if(!(isTeacherBox.isChecked() || isStudentBox.isChecked())){
                    Toast.makeText(RegisterActivity.this, "Select the Account Type", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(TextUtils.isEmpty(fullname)) {
                    Toast.makeText(RegisterActivity.this, "Enter FullName", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;

                }
                mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), getEditTextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        Toast.makeText(RegisterActivity.this, "Account created",
                                Toast.LENGTH_SHORT).show();

                        DocumentReference df = fStore.collection("Users").document(user.getUid());
                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("FullName", editFullName.getText().toString());
                        userInfo.put("email", editTextEmail.getText().toString());

                        if(isTeacherBox.isChecked()){
                            userInfo.put("isTeacher", "1");

                        } else if (isStudentBox.isChecked()) {
                            userInfo.put("isStudent", "1");
                        }

                        df.set(userInfo);
                        if(isTeacherBox.isChecked()){
                            Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (isStudentBox.isChecked()) {
                            Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                            startActivity(intent);
                            finish();

                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "authentication failed",
                                Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });
    }
}