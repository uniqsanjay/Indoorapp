package com.example.college_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextInputLayout uname, pass;
    Button login;
    TextView sign_up;
    Configurations config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        config = new Configurations();
        uname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        sign_up = findViewById(R.id.sign_up);
        login = findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = uname.getEditText().getText().toString();
                String password = pass.getEditText().getText().toString();
                if(name.equalsIgnoreCase("Admin") && password.equalsIgnoreCase("admin")){
                    startActivity(new Intent(Login.this, Add_Becons.class));
                }else{
                    config.login(Login.this, name, password);
                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Sign_Up.class));
            }
        });
    }
}
