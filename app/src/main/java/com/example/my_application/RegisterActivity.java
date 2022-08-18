package com.example.my_application;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends BaseActivity {
    private EditText eEtName;
    private EditText eEtUsername;
    private EditText eEtEmail;
    private EditText eEtPassword;
    private EditText eEtRePassword;
    private Button eBtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
        setTitle("Register");
        setUpToolBar();
    }

    private void initEvent() {
        eBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        eEtName = findViewById(R.id.id_et_name);
        eEtUsername = findViewById(R.id.id_et_username);
        eEtEmail = findViewById(R.id.id_et_email);
        eEtPassword = findViewById(R.id.id_et_password);
        eEtRePassword = findViewById(R.id.id_et_repassword);
    }
}