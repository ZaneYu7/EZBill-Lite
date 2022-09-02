package com.example.my_application.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.my_application.R;
import com.example.my_application.beam.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private TextView title;
    private EditText editTextFullName, editTextUserName, editTextEmail, editTextPassword, editTextRePassword;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        title = (TextView) findViewById(R.id.title);
        title.setOnClickListener(this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullname);
        editTextUserName = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextRePassword = (EditText) findViewById(R.id.rePassword);

       // initView();
       // initEvent();
        //setTitle("Register");
        setUpToolBar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_register:
                btn_register();
                break;
        }
    }

    private void btn_register() {
        String fullName = editTextFullName.getText().toString().trim();
        String userName = editTextUserName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String rePassword = editTextRePassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if (userName.isEmpty()) {
            editTextUserName.setError("Use name is required!");
            editTextUserName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        if (rePassword.isEmpty()) {
            editTextRePassword.setError("You need to enter your password again!");
            editTextRePassword.requestFocus();
            return;
        }

        if (!rePassword.equals(password)) {
            editTextRePassword.setError("Please enter the correct password!");
            editTextRePassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(fullName, userName, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}