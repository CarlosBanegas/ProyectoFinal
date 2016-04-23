package com.example.arvarela.acesubastas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class RgisterCarActivity extends AppCompatActivity {

    private int someVariable;

    public int getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(int someVariable) {
        this.someVariable = someVariable;
    }

    private String app_directory="myPictureApp/";
    private String media_directory=app_directory+"media";
    private String temporal_picture_name="temporal.jpg";
    int RESULT_LOAD_IMAGE=9002;

    private final int foto_codigo=100;
    private final int selecccionar_foto=200;
    private ImageView imageview;

    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister_car);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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
    try{
        ImageView fotoCarro=(ImageView)findViewById(R.id.fotocarro);
        switch (view.getId()){
            case R.id.fotocarro:
                Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
        }


    }catch (Exception e){
        e.printStackTrace();

    }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){

            //recuperar tamaño dej JSon

            RequestQueue requestQueue;
            requestQueue= Volley.newRequestQueue(context);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, "http://prebasdispositivosmo.comxa.com/ApiApp/vehiculos.php", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONArray array = (JSONArray) response.getJSONArray("data");
                                JSONObject[] objetos = new JSONObject[array.length()];

                                for (int i = 0; i < objetos.length; i++) {
                                    objetos[i] = (JSONObject) array.get(i);

                                }
                                int x = objetos.length;
                                someVariable= x+1;
                                Toast.makeText(getApplicationContext(), "Id de la imagen: " + someVariable, Toast.LENGTH_SHORT).show();
                                EditText etFoto = (EditText) findViewById(R.id.etFoto);
                                etFoto.setText("http://prebasdispositivosmo.comxa.com/pictures/" + someVariable + ".JPG");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //...
                        }
                    });
            requestQueue.add(request);

            //devolve la imagen al imageview
            Uri selctedImage=data.getData();
            ImageView fotoCarro=(ImageView)findViewById(R.id.fotocarro);
            fotoCarro.setImageURI(selctedImage);
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
        startActivityForResult(intent, foto_codigo);

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

            final String descripcion = etDescripcion.getText().toString();
            final float precioInicial = Float.parseFloat(etPrecioInicial.getText().toString());


            try{
                ImageView fotoCarro=(ImageView)findViewById(R.id.fotocarro);
                Bitmap image=((BitmapDrawable) fotoCarro.getDrawable()).getBitmap();
                new UploadImage(image, someVariable+"").execute();
            }catch (Exception e){

            }

            final String foto = etFoto.getText().toString();

            //formulario

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
    private  class UploadImage extends AsyncTask<Void, Void, Void> {
        Bitmap image;
        String name;
        public UploadImage(Bitmap image, String name){
            this.image=image;
            this.name=name;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend=new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodImage));
            dataToSend.add(new BasicNameValuePair("name", name));

            HttpParams httpRequestParams=getHttpRequestParams();

            HttpClient client=new DefaultHttpClient(httpRequestParams);
            HttpPost post=new HttpPost("http://prebasdispositivosmo.comxa.com/SavePicture.php");
            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }catch (Exception e){

                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"Imagen Guardada", Toast.LENGTH_SHORT).show();
        }


    }

    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams=new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000*30);
        return httpRequestParams;
    }
}