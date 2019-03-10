package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lisvikproject.R;

public class ScoresActivity extends AppCompatActivity {

    TextView achievements, scores, toChange;
    ScrollView scrollView;

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

        Intent intent=getIntent();
        String category = intent.getStringExtra("category");
        String score = intent.getStringExtra("score");

        if(category!=null&&score!=null)
            toChange.append("\n"+category+":   "+score+"/10");

    }
}
