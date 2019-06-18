package com.example.jpa.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        //CHECK IF USER IS ALREADY LOGGED IN
        if(firebaseAuth.getCurrentUser() !=null){
        //profile Activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }


        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);


        progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
    }
private void userLogin() {
    String email = editTextEmail.getText().toString().trim();
    String password = editTextPassword.getText().toString().trim();

    if (TextUtils.isEmpty(email)) {
        //email is empty!!
        Toast.makeText(this, "Please Enter your email", Toast.LENGTH_SHORT).show();
        //stop the function
        return;
    }
    if (TextUtils.isEmpty(password)) {
        //password is empty!!
        Toast.makeText(this, "Please Enter your password", Toast.LENGTH_SHORT).show();
        //stop the function
        return;
    }
    //if everything is okay
    // User will be registered
    progressDialog.setMessage("Logging In...");
    progressDialog.show();

    firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {//start profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    }
                }
            });
}
    @Override
    public void onClick(View view) {
    if (view == buttonLogin){
        userLogin();
    }
    if (view == textViewRegister){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
    }
}
