package com.example.lisvikproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoresActivity extends AppCompatActivity {

    int countOfNull = 0;
    ImageView gold, silver, bronse;
    TextView achievements, scores, t1, t2, t3, t4, t5;
    String score;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email = mAuth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        achievements = (TextView) findViewById(R.id.achievementsText);
        scores = (TextView) findViewById(R.id.scoresRes);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        gold = findViewById(R.id.gold);
        silver = findViewById(R.id.silver);
        bronse = findViewById(R.id.bronse);

        startLoading();
        loadScore();
        loadLast();
    }
    /**
     * time to concentrate
     */
    private void startLoading() {
        final ProgressDialog progresRing = ProgressDialog.show(ScoresActivity.this, " ", "Сейчас ты увидишь результаты последних игр!", true);
        progresRing.setCancelable(false);
        progresRing.setCanceledOnTouchOutside(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1800);
                } catch (Exception e) {

                }
                progresRing.dismiss();
            }
        }).start();
    }

    /*
    load scores from database
     */
    public void loadScore() {
        db.collection("users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getData().containsKey("Score")) {
                            score = documentSnapshot.getData().get("Score").toString();
                            scores.setText("Всего заработано очков: " + score);
                        } else {
                            scores.setText("Всего заработано очков: " + 0);
                        }
                    }
                });
    }

    /*
    load results of last 5 games
     */
    public void loadLast() {
        countOfNull = 0;
        db.collection("users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> map = documentSnapshot.getData();
                        String tmp;
                        for (int i = 0; i < 5; ++i) {
                            if (documentSnapshot.getData().containsKey(String.valueOf(i)) && (map.get(String.valueOf(i))) != null) {
                                tmp = map.get(String.valueOf(i)).toString();
                                String[] array = getElements(tmp);
                                String s;
                                if (array[0].equals("7-"))
                                    s = "Младше 7";
                                else
                                    s = "Старше 7";
                                switch (i) {
                                    case 0:
                                        t1.setText(" (" + s + ")" + array[1] + ":   " + array[2]);
                                        break;
                                    case 1:
                                        t2.setText(" (" + s + ")" + array[1] + ":   " + array[2]);
                                        break;
                                    case 2:
                                        t3.setText(" (" + s + ")" + array[1] + ":   " + array[2]);
                                        break;
                                    case 3:
                                        t4.setText(" (" + s + ")" + array[1] + ":   " + array[2]);
                                        break;
                                    case 4:
                                        t5.setText(" (" + s + ")" + array[1] + ":   " + array[2]);
                                        break;
                                }
                            } else {
                                countOfNull++;
                                continue;
                            }
                        }
                        if (countOfNull == 5)
                            t1.setText("У тебя еще не сыграно ни одной игры!\nСкорее начинай :)");
                        else
                            loadMedals();
                    }
                });
    }

    /*
    parse string with result and subject information into array
     */
    public String[] getElements(String str) {
        String[] array = null;
        array = str.split(" ");
        return array;
    }

    /*
    back button event handler
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    set medals based on game results
     */
    private void loadMedals() {
        if (Integer.parseInt(score) > 50)
            bronse.setImageResource(R.drawable.medal_bronze);
        if (Integer.parseInt(score) > 100)
            silver.setImageResource(R.drawable.medal_silver);
        if (Integer.parseInt(score) > 150)
            gold.setImageResource(R.drawable.medal_gold);
    }
}
