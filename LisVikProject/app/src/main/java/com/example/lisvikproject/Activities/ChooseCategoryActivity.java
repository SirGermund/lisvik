package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lisvikproject.R;

public class ChooseCategoryActivity extends AppCompatActivity {

    TextView chooseCategory;
    Button astronomy, biology, geography, history, literature, math_phys, movies, music, tales, tech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        chooseCategory=(TextView)findViewById(R.id.chooseCategory);
        astronomy=(Button)findViewById(R.id.Astronomy);
        biology=(Button)findViewById(R.id.Biology);
        geography=(Button)findViewById(R.id.Geography);
        history=(Button)findViewById(R.id.History);
        literature=(Button)findViewById(R.id.Literature);
        math_phys=(Button)findViewById(R.id.Math_Phys);
        movies=(Button)findViewById(R.id.Movies);
        music=(Button)findViewById(R.id.Music);
        tales=(Button)findViewById(R.id.Tales);
        tech=(Button)findViewById(R.id.Tech);


        Intent intent=getIntent();
        final String ageValue=intent.getStringExtra("age");


        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.Astronomy:
                        Intent intent1=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent1.putExtra("category", "Astronomy");
                        intent1.putExtra("age", ageValue);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.Biology:
                        Intent intent2=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent2.putExtra("category", "Biology");
                        intent2.putExtra("age", ageValue);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.History:
                        Intent intent3=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent3.putExtra("category", "History");
                        intent3.putExtra("age", ageValue);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.Literature:
                        Intent intent4=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent4.putExtra("category", "Literature");
                        intent4.putExtra("age", ageValue);
                        startActivity(intent4);
                        break;
                    case R.id.Math_Phys:
                        Intent intent5=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent5.putExtra("category", "Math_Phys");
                        intent5.putExtra("age", ageValue);
                        finish();
                        break;
                    case R.id.Movies:
                        Intent intent6=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent6.putExtra("category", "Movies");
                        intent6.putExtra("age", ageValue);
                        startActivity(intent6);
                        finish();
                        break;
                    case R.id.Music:
                        Intent intent7=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent7.putExtra("category", "Music");
                        intent7.putExtra("age", ageValue);
                        startActivity(intent7);
                        finish();
                        break;
                    case R.id.Tales:
                        Intent intent8=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent8.putExtra("category", "Tales");
                        intent8.putExtra("age", ageValue);
                        startActivity(intent8);
                        finish();
                        break;
                    case R.id.Geography:
                        Intent intent9=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent9.putExtra("category", "Geography");
                        intent9.putExtra("age", ageValue);
                        startActivity(intent9);
                        finish();
                        break;
                    case R.id.Tech:
                        Intent intent10=new Intent(getApplicationContext(), QuestionActivity.class);
                        intent10.putExtra("category", "Tech");
                        intent10.putExtra("age", ageValue);
                        startActivity(intent10);
                        finish();
                        break;
                }
            }
        };

        astronomy.setOnClickListener(onClickListener);
        biology.setOnClickListener(onClickListener);
        geography.setOnClickListener(onClickListener);
        history.setOnClickListener(onClickListener);
        literature.setOnClickListener(onClickListener);
        math_phys.setOnClickListener(onClickListener);
        movies.setOnClickListener(onClickListener);
        music.setOnClickListener(onClickListener);
        tales.setOnClickListener(onClickListener);
        tech.setOnClickListener(onClickListener);

    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(), ChooseAgeActivity.class);
        startActivity(intent);
        finish();
    }
}
