package com.example.college_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class Sign_Up extends AppCompatActivity {

    TextInputLayout uname, umail, umob, upass, con_pass;
    Button sign_up;
    Configurations config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        config = new Configurations();
        uname = findViewById(R.id.uname);
        umail = findViewById(R.id.umail);
        umob = findViewById(R.id.umobile);
        upass = findViewById(R.id.upass);
        con_pass = findViewById(R.id.conf_upass);
        sign_up = findViewById(R.id.register);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = uname.getEditText().getText().toString();
                String mail = umail.getEditText().getText().toString();
                String mob = umob.getEditText().getText().toString();
                String pass = upass.getEditText().getText().toString();
                String conf_pass = con_pass.getEditText().getText().toString();

                config.addUser(Sign_Up.this, name, mail, mob, pass, conf_pass);
            }
        });
    }
}
