package com.owais.roomdbexercise.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.owais.roomdbexercise.R;

public class AddActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail,editTextCountry;
    private Button buttonSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editTextName=findViewById(R.id.name);
        editTextEmail=findViewById(R.id.email);
        editTextCountry=findViewById(R.id.country);
        buttonSubmit=findViewById(R.id.submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTextName.getText().toString();
                String email=editTextEmail.getText().toString();
                String country=editTextCountry.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(AddActivity.this, "plz enter your name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(AddActivity.this, "plz enter your email ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(country)) {
                    Toast.makeText(AddActivity.this, "plz enter your country ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("name",name);
                returnIntent.putExtra("email",email);
                returnIntent.putExtra("country",country);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });
    }
}
