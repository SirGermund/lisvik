package com.example.lisvikproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    Button ans1, ans2, ans3;
    TextView questionText, questionNumber, timer;
    List<Question> qlist;
    int questNumberInt = 0, total = 0, correct = 0;


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

        // чтение вопросов
        qlist = new ArrayList<>();
        readQuestions(ageValue, categoryValue);
        //обновление вопроса
        updateQuestion(0);
    }

    private void updateQuestion(final int i) {
        //номер вопроса(начиная с 1)
        while (qlist.size() < i + 1); // если вдруг залагает чтение из бд
        total++;
        if (total > 10) {
            //end of the test
            showAlertDialogOfInfo();
        } else {
            //пытаюсь обратиться к вопросу из определенной категории и возраста


            ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
            ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
            ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

            ans1.setText(qlist.get(i).getAnswer1());
            ans2.setText(qlist.get(i).getAnswer2());
            ans3.setText(qlist.get(i).getAnswer3());
            questionText.setText(qlist.get(i).getQuestion());
            questionNumber.setText("Вопрос номер " + total);


            ans1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ans1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                        ans1.setBackgroundColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion(i + 1);
                            }
                        }, 1500);
                    } else {
                        //answer is wrong...
                        ans1.setBackgroundColor(Color.RED);
                        if (ans2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            ans2.setBackgroundColor(Color.GREEN);
                        } else if (ans3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            ans3.setBackgroundColor(Color.GREEN);
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion(i + 1);
                            }
                        }, 1500);
                    }
                }
            });

            ans2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ans2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                        ans2.setBackgroundColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion(i + 1);
                            }
                        }, 1500);
                    } else {
                        //answer is wrong...
                        ans2.setBackgroundColor(Color.RED);
                        if (ans1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            ans1.setBackgroundColor(Color.GREEN);
                        } else if (ans1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            ans1.setBackgroundColor(Color.GREEN);
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion(i + 1);
                            }
                        }, 1500);
                    }
                }
            });


            ans3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ans3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                        ans3.setBackgroundColor(Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                correct++;
                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion(i + 1);
                            }
                        }, 1500);
                    } else {
                        //answer is wrong...
                        ans3.setBackgroundColor(Color.RED);
                        if (ans2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            ans2.setBackgroundColor(Color.GREEN);
                        } else if (ans1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                            ans1.setBackgroundColor(Color.GREEN);
                        }

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ans1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                ans2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                ans3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion(i + 1);
                            }
                        }, 1500);
                    }
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

    public void readQuestions(String age, String subject)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("quizzes")
                .document(age)
                .collection(subject)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Log.d("success", "  reading documents");
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        Collections.shuffle(list);
                        Map<String, Object> d;
                        for (int i = 0; i < 10; ++i) {
                            d = list.get(i).getData();
                            qlist.add(new Question(d.get("Question").toString(), d.get("0").toString(), d.get("1").toString(), d.get("2").toString()));
                        }

                    }
                });


    }
}
