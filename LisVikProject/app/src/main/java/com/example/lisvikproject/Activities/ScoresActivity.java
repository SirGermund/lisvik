package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoresActivity extends AppCompatActivity {

    TextView achievements, scores, toChange;
    ScrollView scrollView;
    String score;
    List<String> last = new ArrayList<>();
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

        achievements=(TextView)findViewById(R.id.achievementsText);
        scores=(TextView)findViewById(R.id.scoresRes);
        toChange=(TextView)findViewById(R.id.textToChange);
        scrollView=(ScrollView)findViewById(R.id.scrollView);

        loadScore(); // если есть счет то он лежит в score, далее подгружаются последние викторины, затем mainMethod
        // задержка чтобы все успевало прогрузится
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // по хорошему здесь можно сделать проверку ( например score == null )
        // которая будет говорить что нихрена юзер не играл
    }
    public void mainMethod() //TODO: Настя это твой метод, если ты в нем значит у тебя есть что выводить
    {
        // а именно score в котором лежит счет
        // лист last с 5 или меньше последними викторинами
    }

    public void loadScore()
    {
        db.collection("users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.getData().containsKey("Score")) {
                            score = documentSnapshot.getData().get("Score").toString();
                            loadLast();
                        }
                    }
                });
    }

    public void loadLast()
    {
        db.collection("users")
                .document(email)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> map = documentSnapshot.getData();
                        String tmp;
                        for (int i = 0; i < 5; ++i)
                        {
                            tmp = map.get(String.valueOf(i)).toString();
                            if (tmp == null)
                                break;
                            last.add(tmp);
                        }
                        mainMethod();
                    }
                });
    }
}
