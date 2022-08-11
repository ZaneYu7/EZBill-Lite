package com.example.my_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText eEtUsername;
    private EditText eEtPassword;
    private Button eBtnLogin;
    private TextView eTvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initEvent() {
        eBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        eTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        eEtUsername = findViewById(R.id.id_et_username);
        eEtPassword = findViewById(R.id.id_et_password);
        eBtnLogin = findViewById(R.id.id_btn_login);
        eTvRegister = findViewById(R.id.id_tv_register);
    }
}