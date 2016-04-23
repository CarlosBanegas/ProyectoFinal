package com.example.arvarela.acesubastas;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(true);




    }

    public void clickCancelar(View v){
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        etAge.setText("");
        etName.setText("");
        etUsername.setText("");
        etPassword.setText("");
        etName.requestFocus();
    }

    public void clickRegisterUser(View v) {
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        if ((TextUtils.isEmpty(etAge.getText())) || (TextUtils.isEmpty(etName.getText())) || (TextUtils.isEmpty(etUsername.getText())) || (TextUtils.isEmpty(etPassword.getText()))) {
            etAge.setError("Campo Obligatorio");
            etName.setError("Campo Obligatorio");
            etUsername.setError("Campo Obligatorio");
            etPassword.setError("Campo Obligatorio");
            Toast.makeText(getApplicationContext(), "Debe llenar los campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {

            final String name = etName.getText().toString();
            final String username = etUsername.getText().toString();
            final int age = Integer.parseInt(etAge.getText().toString());
            final String password = etPassword.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Registro Satisfactorio", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Registro Fallido")
                                    .setNegativeButton("Reintentar", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        }
    }
}