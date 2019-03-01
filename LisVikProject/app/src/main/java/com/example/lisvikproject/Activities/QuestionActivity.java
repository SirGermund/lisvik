package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisvikproject.Models.Question;
import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuestionActivity extends AppCompatActivity {
    Button ans1, ans2, ans3;
    TextView questionText, questionNumber, timer;
    int questNumberInt=0, total=0, correct=0;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionNumber = (TextView)findViewById(R.id.questionNumber);
        questionText = (TextView)findViewById(R.id.questionText);
        timer=(TextView)findViewById(R.id.timer);
        ans1 = (Button)findViewById(R.id.answer1);
        ans2 = (Button)findViewById(R.id.answer2);
        ans3 = (Button)findViewById(R.id.answer3);

        //чтобы получить результаты выбора с предыдущих двух активити
        Intent intent=getIntent();
        //получить выбранный возраст
        String ageValue=intent.getStringExtra("age");
        //получить выбранную категорию
        String categoryValue=intent.getStringExtra("category");

        //обновление вопроса
        updateQuestion(ageValue, categoryValue);
    }

    private void updateQuestion(final String ageValue, final String categoryValue)
    {
        //номер вопроса(начиная с 1)
        total++;
        if(total>10)
        {
            //end of the test

        }else{
            //пытаюсь обратиться к вопросу из определенной категории и возраста
            final DocumentReference quest=db.collection("quizzes").document(ageValue).collection(categoryValue).document(String.valueOf(total));
        }
    }
}
