package com.example.arvarela.acesubastas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class RgisterCarActivity extends AppCompatActivity {
    private String app_directory="myPictureApp/";
    private String media_directory=app_directory+"media";
    private String temporal_picture_name="temporal.jpg";

    private final int foto_codigo=100;
    private final int selecccionar_foto=200;
    private ImageView imageview;

    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister_car);

        imageview =(ImageView)findViewById(R.id.fotocarro);

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

    public void bfoto(View view){
        final CharSequence[] options={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Elige una opcion :D");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int seleccion) {
                if (options[seleccion]=="Tomar Foto"){
                    openCamera();

                }else if (options[seleccion]=="Elegir de Galeria"){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Selecciona app de imagen"),selecccionar_foto);

                }else if(options[seleccion]=="Cancelar"){
                    dialog.dismiss();
                }
            }
        });
        builder.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case foto_codigo:
                if(resultCode==RESULT_OK){
                    String dir=Environment.getExternalStorageDirectory()+File.separator
                            + media_directory+File.separator+temporal_picture_name;
                    decodeBitmap(dir);
                }
                break;
            case selecccionar_foto:
                if(resultCode==RESULT_OK){
                    Uri path=data.getData();
                    imageview.setImageURI(path);
                }

        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap= BitmapFactory.decodeFile(dir);
        imageview.setImageBitmap(bitmap);

    }

    private void openCamera() {
        File file=new File(Environment.getExternalStorageDirectory(), media_directory);
        file.mkdirs();
        String path=Environment.getExternalStorageDirectory()+File.separator+
                media_directory+File.separator+temporal_picture_name;
        File newfile=new File(path);

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newfile));
        startActivityForResult(intent,foto_codigo);

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