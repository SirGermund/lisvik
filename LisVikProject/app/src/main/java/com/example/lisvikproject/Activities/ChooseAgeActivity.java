package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lisvikproject.R;

public class ChooseAgeActivity extends AppCompatActivity{

    private Button above7,under7;
    private TextView chooseAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_age);

        above7=(Button)findViewById(R.id.above7);
        under7=(Button)findViewById(R.id.under7);
        chooseAge=(TextView)findViewById(R.id.chooseAgeText);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.under7:
                        Intent intent1=new Intent(getApplicationContext(), ChooseCategoryActivity.class);
                        intent1.putExtra("age", "under7");
                        startActivity(intent1);
                        break;
                    case R.id.above7:
                        Intent intent2=new Intent(getApplicationContext(), ChooseCategoryActivity.class);
                        intent2.putExtra("age", "above7");
                        startActivity(intent2);
                        break;
                    case R.id.default_activity_button:
                        break;
                }
            }
        };

        under7.setOnClickListener(onClickListener);
        above7.setOnClickListener(onClickListener);

//        above7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), ChooseCategoryActivity.class);
//                intent.putExtra("age", "above7");
//                startActivity(intent);
//            }
//        });
//
//        under7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(), ChooseCategoryActivity.class);
//                intent.putExtra("age", "under7");
//                startActivity(intent);
//            }
//        });
    }


}
