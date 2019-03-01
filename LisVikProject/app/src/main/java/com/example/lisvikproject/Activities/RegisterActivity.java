package com.example.lisvikproject.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lisvikproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.*;

public class RegisterActivity extends AppCompatActivity {

    static int PReqCode = 1;
    static int REQUESTCODE=1;
    Uri pickedImgUri = Uri.parse("android.resource://com.example.lisvikproject/" + R.drawable.userphoto);
    ImageView imgUserPhoto;

    private EditText userEmail, userPassword, userPassword2, userName, userAge;
    private ProgressBar loadingProgress;
    private Button regBtn;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //ini views
        userEmail=(EditText)findViewById(R.id.regEmail);
        userPassword=(EditText)findViewById(R.id.regPassword);
        userPassword2=(EditText)findViewById(R.id.regPassword2);
        userName=(EditText)findViewById(R.id.regName);
        userAge=(EditText)findViewById(R.id.regAge);
        loadingProgress=(ProgressBar) findViewById(R.id.regProgressBar);
        regBtn=(Button)findViewById(R.id.regButton);

        imgUserPhoto = (ImageView) findViewById(R.id.regUserPhoto);

        loadingProgress.setVisibility(View.INVISIBLE);

        mAuth=FirebaseAuth.getInstance();

        imgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();
                }else{
                    openGallery();
                }


            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);
                final String email=userEmail.getText().toString();
                final String password=userPassword.getText().toString();
                final String password2=userPassword2.getText().toString();
                final String name=userName.getText().toString();
                final String age=userAge.getText().toString();

                if(email.isEmpty()||name.isEmpty()||age.isEmpty()||password.isEmpty()|| password.isEmpty()||password2.isEmpty()){

                    //something goes wrong: all fields must be filled
                    //we need to display an error message
                    showMessage("Заполнены не все поля!");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                }else{
                    if(!password.equals(password2)){
                        showMessage("Пароли не совпадают!");
                        regBtn.setVisibility(View.VISIBLE);
                        loadingProgress.setVisibility(View.INVISIBLE);
                    }else {

                        //everything is OK and all fields are filled, now we cat start creating user account
                        //createUserAccount method will try to create the user, if the email is valid

                        сreateUserAccount(email, name, age, password);
                    }
                }

            }
        });
    }

    private void сreateUserAccount(String email, final String name, final String age, String password) {

        //this method creates user account with specific email and password

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //user account created successfully
                            showMessage("Аккаунт успешно создан!");
                            //after we created user account we need to update his profile picture and name
                            updateUserInfo(name, pickedImgUri, age, mAuth.getCurrentUser());

                        }else{

                            //account creation failed
                            showMessage("При создании аккаунта возникла ошибка!"+task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);

                        }
                    }
                });
    }

    //update user photo, age and name
    private void updateUserInfo(final String name, Uri pickedImgUri, String age, final FirebaseUser currentUser) {

        // Делаем мапу юзера
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Age", age);
        user.put("Email", currentUser.getEmail());


//        //////////////////////---------------------------------///////////////////////
//        Intent intent=new Intent(getApplicationContext(), InformationActivity.class);
//        intent.putExtra("email", currentUser.getEmail());
//        ///////////////////////--------------------------------///////////////////////



        // создаем документ соответсвующий ID юзера и добавляем туда самого юзера
        db.collection("users").document(currentUser.getEmail()).set(user);

        //first we need to upload user photo to firebase storage and get url
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //image uploaded successfully
                //now we can get our image url
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        //uri contain user image url
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();
                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            //user info updated successfully
                                            showMessage("Регистрация завершена!");
                                            updateUI();
                                        }

                                    }
                                });

                    }
                });
            }
        });

    }

    private void updateUI() {

        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivity);
        finish();

    }

    //simple method to show toast message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==REQUESTCODE && data!=null){
            //the user has successfully picked an image
            //we need to save it's reference
            pickedImgUri = data.getData();

            imgUserPhoto.setImageURI(pickedImgUri);
        }


    }

    /*
        open gallery intent and wait for user to pick up an image
         */
    private void openGallery() {
        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivity.this, "Пожалуйста, разрешите доступ.", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(RegisterActivity.this,
                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    PReqCode);
            }
        }else{
            openGallery();
        }


    }
}
