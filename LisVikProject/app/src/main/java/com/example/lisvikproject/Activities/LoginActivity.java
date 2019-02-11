package com.example.lisvikproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText userMail, userPassword;
    private Button btnLogin;
    private ProgressBar loginProgressBar;
    private TextView loginRegistration;

    private FirebaseAuth myAuth;

    private Intent registerActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail=(EditText)findViewById(R.id.loginMail);
        userPassword=(EditText)findViewById(R.id.loginPassword);
        loginProgressBar=(ProgressBar)findViewById(R.id.loginProgressBar);
        btnLogin=(Button)findViewById(R.id.loginButton);
        loginRegistration=(TextView)findViewById(R.id.loginRegistration);

        myAuth=FirebaseAuth.getInstance();

        registerActivity=new Intent(this, com.example.lisvikproject.Activities.RegisterActivity.class);

        loginProgressBar.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgressBar.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);

                final String mail=userMail.getText().toString();
                final String password=userPassword.getText().toString();

                if(mail.isEmpty()||password.isEmpty()){
                    showMessage("Не введен логин или пароль.");
                    loginProgressBar.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                }else{
                    signIn(mail, password);
                }
            }
        });

        loginRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerActivity);
                finish();
            }
        });
    }

    private void signIn(String mail, String password) {

        myAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    loginProgressBar.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    updateUI();

                }else{

                    loginProgressBar.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    showMessage(task.getException().getMessage());
                }
            }
        });
    }

    private void updateUI() {

        Intent homeActivity=new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
}
