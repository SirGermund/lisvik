package com.example.lisvikproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lisvikproject.Models.Question;
import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class QuestionActivity extends AppCompatActivity {
    Button ans1, ans2, ans3;
    TextView questionText, questionNumber, timer;
    int questNumberInt = 0, total = 0, correct = 0;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionNumber = (TextView) findViewById(R.id.questionNumber);
        questionText = (TextView) findViewById(R.id.questionText);
        timer = (TextView) findViewById(R.id.timer);
        ans1 = (Button) findViewById(R.id.answer1);
        ans2 = (Button) findViewById(R.id.answer2);
        ans3 = (Button) findViewById(R.id.answer3);

        //чтобы получить результаты выбора с предыдущих двух активити
        Intent intent = getIntent();
        //получить выбранный возраст
        String ageValue = intent.getStringExtra("age");
        //получить выбранную категорию
        String categoryValue = intent.getStringExtra("category");

        //обновление вопроса
        updateQuestion(ageValue, categoryValue);
    }

    private void updateQuestion(final String ageValue, final String categoryValue) {
        //номер вопроса(начиная с 1)
        total++;
        if (total > 10) {
            //end of the test
            showAlertDialogOfInfo();
        } else {
            //пытаюсь обратиться к вопросу из определенной категории и возраста
            db = FirebaseFirestore.getInstance();
            db.collection("quizzes").document(ageValue).collection(categoryValue).document(String.valueOf(total)).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Map<String, Object> d = documentSnapshot.getData();
                            final Question question = new Question(d.get("Question").toString(), d.get("0").toString(), d.get("1").toString(), d.get("2").toString(), d.get("0").toString());

                            ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
                            ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
                            ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                            ans1.setText(question.getAnswer1());
                            ans2.setText(question.getAnswer2());
                            ans3.setText(question.getAnswer3());
                            questionText.setText(question.getQuestion());
                            questionNumber.setText("Вопрос номер "+total);


                            ans1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(ans1.getText().toString().equals(question.getCorrectAnswer()))
                                    {
                                        ans1.setBackgroundColor(Color.GREEN);
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));

                                                updateQuestion(ageValue, categoryValue);
                                            }
                                        }, 1500);
                                    }else{
                                        //answer is wrong...
                                        ans1.setBackgroundColor(Color.RED);
                                        if(ans2.getText().toString().equals(question.getCorrectAnswer()))
                                        {
                                            ans2.setBackgroundColor(Color.GREEN);
                                        }else if(ans3.getText().toString().equals(question.getCorrectAnswer()))
                                        {
                                            ans3.setBackgroundColor(Color.GREEN);
                                        }

                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                                updateQuestion(ageValue, categoryValue);
                                            }
                                        },1500);
                                    }
                                }
                            });

                            ans2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(ans2.getText().toString().equals(question.getCorrectAnswer()))
                                    {
                                        ans2.setBackgroundColor(Color.GREEN);
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                                updateQuestion(ageValue, categoryValue);
                                            }
                                        }, 1500);
                                    }else{
                                        //answer is wrong...
                                        ans2.setBackgroundColor(Color.RED);
                                        if(ans1.getText().toString().equals(question.getCorrectAnswer()))
                                        {
                                            ans1.setBackgroundColor(Color.GREEN);
                                        }else if(ans1.getText().toString().equals(question.getCorrectAnswer()))
                                        {
                                            ans1.setBackgroundColor(Color.GREEN);
                                        }

                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                                updateQuestion(ageValue, categoryValue);
                                            }
                                        },1500);
                                    }
                                }
                            });


                            ans3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(ans3.getText().toString().equals(question.getCorrectAnswer()))
                                    {
                                        ans3.setBackgroundColor(Color.GREEN);
                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                                updateQuestion(ageValue, categoryValue);
                                            }
                                        }, 1500);
                                    }else{
                                        //answer is wrong...
                                        ans3.setBackgroundColor(Color.RED);
                                        if(ans2.getText().toString().equals(question.getCorrectAnswer()))
                                        {
                                            ans2.setBackgroundColor(Color.GREEN);
                                        }else if(ans1.getText().toString().equals(question.getCorrectAnswer()))
                                        {
                                            ans1.setBackgroundColor(Color.GREEN);
                                        }

                                        Handler handler=new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                                updateQuestion(ageValue, categoryValue);
                                            }
                                        },1500);
                                    }
                                }
                            });


                        }
                    });
        }
    }

    /**
     * To show the results of the test
     */
    public void showAlertDialogOfInfo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Результаты викторины!");
        builder.setMessage("Ты молодец! \nКоличество правильных ответов: \n"+correct+"/10");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent toTheHomePage=new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(toTheHomePage);
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
