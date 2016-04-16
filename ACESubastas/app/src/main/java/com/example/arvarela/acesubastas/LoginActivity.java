package com.example.arvarela.acesubastas;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickLogin(View v) {
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
       // final String password = etPassword.getText().toString();

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        String name = jsonResponse.getString("name");
                        int age = jsonResponse.getInt("age");

                        Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("age", age);
                        intent.putExtra("username", username);
                        LoginActivity.this.startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(), "Bienvenido " + name, Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Inicio de Sesion Fallido")
                                .setNegativeButton("Reintentar", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }

    public void clickRegistro(View v){
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}