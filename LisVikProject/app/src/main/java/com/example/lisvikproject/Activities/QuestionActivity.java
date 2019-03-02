package com.example.lisvikproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lisvikproject.Models.Question;
import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {
    Button ans1, ans2, ans3;
    TextView questionText, questionNumber, timer;
    List<Question> qlist;
    int questNumberInt = 0, total = 0, correct = 0, ifTimeIsFinished=0;

    String ageValue, categoryValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        questionNumber = (TextView) findViewById(R.id.questionNumber);
        questionText = (TextView) findViewById(R.id.questionText);
        timer = (TextView) findViewById(R.id.timer);
        ans1 = (Button) findViewById(R.id.answer1);
        ans2 = (Button) findViewById(R.id.answer2);
        ans3 = (Button) findViewById(R.id.answer3);

        //чтобы получить результаты выбора с предыдущих двух активити
        Intent intent = getIntent();
        //получить выбранный возраст
        ageValue = intent.getStringExtra("age");
        //получить выбранную категорию
        categoryValue = intent.getStringExtra("category");

        // чтение вопросов
        qlist = new ArrayList<>();
        readQuestions(ageValue, categoryValue);
    }

    private void updateQuestion(final int i) {
        //номер вопроса(начиная с 1)
        total++;
        ifTimeIsFinished++;
        if (total > 10) {
            //end of the test
            showAlertDialogOfResults(categoryValue, String.valueOf(correct));
        } else {

            ans1.setEnabled(true);
            ans2.setEnabled(true);
            ans3.setEnabled(true);

            //пытаюсь обратиться к вопросу из определенной категории и возраста

            ans1.setText(qlist.get(i).getAnswer1());
            ans2.setText(qlist.get(i).getAnswer2());
            ans3.setText(qlist.get(i).getAnswer3());
            questionText.setText(qlist.get(i).getQuestion());
            questionNumber.setText("Вопрос номер " + total);

            ans1.setBackgroundResource(R.drawable.button_simple);
            ans2.setBackgroundResource(R.drawable.button_simple);
            ans3.setBackgroundResource(R.drawable.button_simple);

            View.OnClickListener onClickListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId())
                    {
                        case R.id.answer1:
                            ans1.setEnabled(false);
                            ans2.setEnabled(false);
                            ans3.setEnabled(false);

                            if (ans1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                ans1.setBackgroundResource(R.drawable.button_correct);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        ans1.setBackgroundResource(R.drawable.button_correct);

                                        updateQuestion(i + 1);
                                    }
                                }, 1500);
                            } else {
                                //answer is wrong...
                                ans1.setBackgroundResource(R.drawable.button_wrong);
                                if (ans2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                    ans2.setBackgroundResource(R.drawable.button_correct);
                                } else if (ans3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                    ans3.setBackgroundResource(R.drawable.button_correct);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        ans1.setBackgroundResource(R.drawable.button_simple);
                                        ans2.setBackgroundResource(R.drawable.button_simple);
                                        ans3.setBackgroundResource(R.drawable.button_simple);
                                        updateQuestion(i + 1);
                                    }
                                }, 1500);
                            }
                            break;
                        case R.id.answer2:
                            ans1.setEnabled(false);
                            ans2.setEnabled(false);
                            ans3.setEnabled(false);

                            if (ans2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                ans2.setBackgroundResource(R.drawable.button_correct);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        ans2.setBackgroundResource(R.drawable.button_correct);

                                        updateQuestion(i + 1);
                                    }
                                }, 1500);
                            } else {
                                //answer is wrong...
                                ans2.setBackgroundResource(R.drawable.button_wrong);
                                if (ans1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                    ans1.setBackgroundResource(R.drawable.button_correct);
                                } else if (ans3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                    ans3.setBackgroundResource(R.drawable.button_correct);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ans1.setBackgroundResource(R.drawable.button_simple);
                                        ans2.setBackgroundResource(R.drawable.button_simple);
                                        ans3.setBackgroundResource(R.drawable.button_simple);

                                        updateQuestion(i + 1);
                                    }
                                }, 1500);
                            }
                            break;
                        case R.id.answer3:
                            ans1.setEnabled(false);
                            ans2.setEnabled(false);
                            ans3.setEnabled(false);

                            if (ans3.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                ans3.setBackgroundResource(R.drawable.button_correct);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        ans3.setBackgroundResource(R.drawable.button_correct);

                                        updateQuestion(i + 1);
                                    }
                                }, 1500);
                            } else {
                                //answer is wrong...
                                ans3.setBackgroundResource(R.drawable.button_wrong);
                                if (ans2.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                    ans2.setBackgroundResource(R.drawable.button_correct);
                                } else if (ans1.getText().toString().equals(qlist.get(i).getCorrectAnswer())) {
                                    ans1.setBackgroundResource(R.drawable.button_correct);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ans1.setBackgroundResource(R.drawable.button_simple);
                                        ans2.setBackgroundResource(R.drawable.button_simple);
                                        ans3.setBackgroundResource(R.drawable.button_simple);

                                        updateQuestion(i + 1);
                                    }
                                }, 1500);
                            }
                            break;
                    }
                }
            };

            ans1.setOnClickListener(onClickListener);
            ans2.setOnClickListener(onClickListener);
            ans3.setOnClickListener(onClickListener);
        }
    }

    /**
     * To show the results of the test
     */
    public void showAlertDialogOfResults(final String  category,final String score){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Результаты викторины!");
        builder.setMessage("Ты молодец! \nКоличество правильных ответов: \n"+correct+"/10");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("category", category);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void readQuestions(String age, String subject) {
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
                        //обновление вопроса
                        updateQuestion(0);
                    }
                });
    }



    public void reverseTimer(final TextView tv)
    {
        new CountDownTimer( 60000, 1000)
        {
            public void onTick(long l)
            {
                tv.setText(""+l/1000);
            }

            public void onFinish()
            {
                tv.setText("Время истекло!");
                updateQuestion(ifTimeIsFinished);
            }
        }.start();

    }
}
