package com.example.lisvikproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lisvikproject.R;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button startTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        startTest=(Button)findViewById(R.id.toTest);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.myAchivements:
                        Intent intent = new Intent(getApplicationContext(), ScoresActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.myNews:
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pg/LisVikGame/events/?ref=page_internal"));
                        startActivity(browserIntent);
                        break;
                    case R.id.rules:
                        showAlertDialogOfInfo();
                        break;
                }
                return true;
            }
        });

        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseSubject=new Intent(getApplicationContext(), ChooseAgeActivity.class);
                startActivity(chooseSubject);
                finish();
            }
        });
    }

    /**
     * чтобы при нажатии на back на панели экрана запрашивалось подтверждение выхода
     */
    @Override
    public void onBackPressed() {
        showAlertDialogExit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)|| super.onOptionsItemSelected(item);
    }

    /**
     * To show to user the info about the test
     */
    public void showAlertDialogOfInfo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Правила викторины");
        builder.setMessage("Привет! Хочешь заработать монетки? \n" +
                "Тогда скорее отвечай на вопросы викторины \"ЛИСВИК\"!\n" +
                "Все просто! \n" +
                "Выбери свой возраст и интересную тему.\n" +
                "Далее ответь на 10 вопросов. Но будь внимателен!\n" +
                "На ответ дано всего лишь 60 секунд.\n" +
                "За каждый провильный вопрос тебе начисляется одна монетка.\n" +
                "Отвечай на вопросы правильно и зарабатывай как можно\n" +
                "больше монеток, чтобы потратить их на развлечения!\n" +
                "Скорее начинай и удачи!!!");
        builder.setCancelable(true);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * To show to user the info about the test
     */
    public void showAlertDialogExit(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Вы действительно хотите выйти?");
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}