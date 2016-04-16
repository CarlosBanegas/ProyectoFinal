package com.example.arvarela.acesubastas;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RgisterCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister_car);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Turismo");
        list.add("Camioneta");
        list.add("Pick - Up");
        list.add("Busito");
        list.add("Otro");

        final Spinner spinner=(Spinner)findViewById(R.id.spinnerTipoVehiculo);
        final EditText etTipo = (EditText) findViewById(R.id.etTipo);
        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, list);
        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(adp);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                switch (arg2) {

                    case 0:
                        etTipo.setText("Turismo");
                        break;
                    case 1:
                        etTipo.setText("Camioneta");
                        break;
                    case 2:
                        etTipo.setText("Pick - Up");
                        break;
                    case 3:
                        etTipo.setText("Busito");
                        break;
                    case 4:
                        etTipo.setText("Otro");
                        break;
                    default:
                        etTipo.setText("Turismo");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }








    public void clickCancelar(View v){
        final EditText etMarca = (EditText) findViewById(R.id.etMarca);
        final EditText etModelo = (EditText) findViewById(R.id.etModelo);
        final EditText etColor = (EditText) findViewById(R.id.etColor);
        final EditText etAno = (EditText) findViewById(R.id.etAno);
        final EditText etFoto = (EditText) findViewById(R.id.etFoto);
        final EditText etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        final EditText etPrecioInicial = (EditText) findViewById(R.id.etPrecioInicial);

        etMarca.setText("");
        etModelo.setText("");
        etColor.setText("");
        etAno.setText("");
        etFoto.setText("");
        etDescripcion.setText("");
        etPrecioInicial.setText("");
        etMarca.requestFocus();
    }

    public void clickNuevaPublicacion(View v) {
        final EditText etMarca = (EditText) findViewById(R.id.etMarca);
        final EditText etModelo = (EditText) findViewById(R.id.etModelo);
        final EditText etColor = (EditText) findViewById(R.id.etColor);
        final EditText etAno = (EditText) findViewById(R.id.etAno);
        final EditText etTipo = (EditText) findViewById(R.id.etTipo);
        final EditText etFoto = (EditText) findViewById(R.id.etFoto);
        final EditText etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        final EditText etPrecioInicial = (EditText) findViewById(R.id.etPrecioInicial);

        if ((TextUtils.isEmpty(etMarca.getText())) || (TextUtils.isEmpty(etModelo.getText())) || (TextUtils.isEmpty(etColor.getText())) || (TextUtils.isEmpty(etAno.getText())) || (TextUtils.isEmpty(etTipo.getText()) || (TextUtils.isEmpty(etPrecioInicial.getText().toString())))) {
            etMarca.setError("Campo Obligatorio");
            etModelo.setError("Campo Obligatorio");
            etColor.setError("Campo Obligatorio");
            etAno.setError("Campo Obligatorio");
            etTipo.setError("Campo Obligatorio");
            etPrecioInicial.setError("Campo Obligatorio");

            Toast.makeText(getApplicationContext(), "Debe llenar los campos obligatorios", Toast.LENGTH_SHORT).show();
        } else {

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
    }
}