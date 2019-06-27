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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordRepeat;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() !=null){
            //profile Activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordRepeat= (EditText) findViewById(R.id.editTextPasswordRepeat);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }
    private void registerUser() {
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
        if (editTextPassword.getText().toString().equals(editTextPasswordRepeat.getText().toString())) {
            //if everything is okay
            // User will be registered
            progressDialog.setMessage("Registering User...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Dismisses Dialog Box
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                //user is registered, and logged in
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(MainActivity.this, "Registering Failed!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else{
            //passwords do not match
            Toast.makeText(this, "The Passwords Do Not Match!", Toast.LENGTH_SHORT).show();
            //stop the function
            return;
        }
    }
    @Override
    public void onClick (View view){
    if (view == buttonRegister){
        registerUser();
    }
    if (view == textViewSignin) {
        startActivity(new Intent(this,LoginActivity.class));
    }
    }
}

