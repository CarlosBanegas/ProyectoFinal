package com.example.arvarela.acesubastas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etAge = (EditText) findViewById(R.id.etAge);

        // Mostrar detalles
        String message = name + " Binvenido a la pantalla detalle";
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etAge.setText(age + "");
    }

    public void clickRegisterCar(View v){
        Intent registerIntent = new Intent(UserAreaActivity.this, RgisterCarActivity.class);
        UserAreaActivity.this.startActivity(registerIntent);
    }

    public void clickVerSubastas(View v){
        Intent intent=new Intent(this, ListCar.class);
        startActivity(intent);
    }


}

