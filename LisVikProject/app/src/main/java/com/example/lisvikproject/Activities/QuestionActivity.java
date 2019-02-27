package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisvikproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    int questionNumberInt = 0;

    Button ans1, ans2, ans3;
    TextView questionText, questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionNumber = (TextView)findViewById(R.id.questionNumber);
        questionText = (TextView)findViewById(R.id.questionText);
        ans1 = (Button)findViewById(R.id.answer1);
        ans2 = (Button)findViewById(R.id.answer2);
        ans3 = (Button)findViewById(R.id.answer3);

        //updateQuestion();
    }
}
