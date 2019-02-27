package com.example.lisvikproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lisvikproject.R;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button startTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.myInformation:
                        Toast.makeText(HomeActivity.this, "Моя информация", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.myAchivements:
                        Toast.makeText(HomeActivity.this, "Достижения", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myNews:
                        Toast.makeText(HomeActivity.this, "Новости", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.exit:
                        Toast.makeText(HomeActivity.this, "Выйти", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rules:
                        Toast.makeText(HomeActivity.this, "Правила", Toast.LENGTH_SHORT).show();
                        showAlertDialogOfInfo();
                        break;
                    default:
                        Toast.makeText(HomeActivity.this, "Something goes wrong...", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
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
//чтобы при нажатии на назад на панели экрана закрывалась боковая панель
//    @Override
//    public void onBackPressed() {
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }else{
//            super.onBackPressed();
//        }
//    }

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
        builder.setMessage("СООБЩЕНИЕ.........");
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
}