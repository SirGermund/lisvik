package com.example.lisvikproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.lisvikproject.R;

public class ChooseAgeActivity extends AppCompatActivity {

    private Button above7,under7;
    private TextView chooseAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_age);

        above7=(Button)findViewById(R.id.above7);
        under7=(Button)findViewById(R.id.under7);
        chooseAge=(TextView)findViewById(R.id.chooseAgeText);
    }
}
