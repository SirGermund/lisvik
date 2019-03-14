package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lisvikproject.Models.UserInfo;
import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class InformationActivity extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        Intent intent=getIntent();
        String email=intent.getStringExtra("email");


        db = FirebaseFirestore.getInstance();
        db.collection("users").document(email).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> d = documentSnapshot.getData();
                        final UserInfo userInfo = new UserInfo(d.get("Name").toString(), d.get("Age").toString(), d.get("Email").toString());
                    }
                });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
