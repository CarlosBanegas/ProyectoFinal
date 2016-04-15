package com.example.arvarela.acesubastas;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RgisterCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister_car);
        final EditText etMarca = (EditText) findViewById(R.id.etMarca);
        final EditText etModelo = (EditText) findViewById(R.id.etModelo);
        final EditText etColor = (EditText) findViewById(R.id.etColor);
        final EditText etAno = (EditText) findViewById(R.id.etAno);
        final EditText etTipo = (EditText) findViewById(R.id.etTipo);
        final EditText etFoto = (EditText) findViewById(R.id.etFoto);
        final EditText etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        final EditText etPrecioInicial = (EditText) findViewById(R.id.etPrecioInicial);
        final Button bSalvar = (Button) findViewById(R.id.bSalvar);
        final Button bCancelar = (Button) findViewById(R.id.bCancelar);

        bSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String marca = etMarca.getText().toString();
                final String modelo = etModelo.getText().toString();
                final String color = etColor.getText().toString();
                final String ano = etAno.getText().toString();
                final String tipo = etTipo.getText().toString();
                final String foto = etFoto.getText().toString();
                final String descripcion = etDescripcion.getText().toString();
                final float precioInicial = Float.parseFloat(etPrecioInicial.getText().toString());


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                // Intent intent = new Intent(RgisterCarActivity.this, UserAreaActivity.class);
                                //RgisterCarActivity.this.startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Registro Satisfactorio", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RgisterCarActivity.this);
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

                RegisterCarRequest registerRequest = new RegisterCarRequest(marca, modelo, color, ano, tipo, foto, descripcion, precioInicial, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RgisterCarActivity.this);
                queue.add(registerRequest);
            }
        });

    }

}