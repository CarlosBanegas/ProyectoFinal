package com.example.arvarela.acesubastas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetalleCar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_car);


        try{
            JSONObject jobj = new JSONObject((getIntent().getExtras().getString("jsonObj")));
            //JSONObject jobj = new JSONObject(getIntent().getExtras().getString("JsonObj"));
            TextView titulo = (TextView) findViewById(R.id.tituloTv2);
            TextView subtitulo = (TextView) findViewById(R.id.subtituloTv2);
            TextView tvPrecio = (TextView) findViewById(R.id.tvPrecio);
            TextView tvDetalle = (TextView) findViewById(R.id.tvDetalle);
            NetworkImageView imagen = (NetworkImageView) findViewById(R.id.view2);

            titulo.setText(jobj.get("marca").toString());
            subtitulo.setText(jobj.get("modelo").toString());
            tvPrecio.setText(jobj.get("precioInicial").toString());
            tvDetalle.setText(jobj.get("descripcion").toString());

            imagen.setImageUrl(String.valueOf(jobj.getString("foto")), VolleySingletonActivity.getInstance().getImageLoader());
           /* String UrlImagen = String.format("http://www.kiva.org/img/120/%d.jpg",
                    jobj.getJSONObject("image").getInt("id"));

            imagen.setImageUrl(UrlImagen,VolleySingletonActivity.getInstance().getImageLoader());
            */
        } catch (JSONException e){
            e.printStackTrace();
        }
    }



    public void clickEntrar(View v){
        Intent intentSubasta=new Intent(this, Subasta.class);
        startActivity(intentSubasta);
    }

}
