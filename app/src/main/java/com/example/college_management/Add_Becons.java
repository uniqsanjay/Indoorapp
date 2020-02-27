package com.example.college_management;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Add_Becons extends AppCompatActivity {

    TextInputLayout name, place;
    Button add;
    Configurations config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__becons);
        config = new Configurations();
        name = findViewById(R.id.becon_name);
        place = findViewById(R.id.becon_place);
        add = findViewById(R.id.add_becons);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bname = name.getEditText().getText().toString();
                String bplace = place.getEditText().getText().toString();
                config.addBecons(Add_Becons.this, bname, bplace);
            }
        });
    }
}
