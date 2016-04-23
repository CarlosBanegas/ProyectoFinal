package com.example.arvarela.acesubastas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
            TextView tvColor=(TextView) findViewById(R.id.tvColor);
            TextView tvAno=(TextView) findViewById(R.id.tvAno);
            TextView tvTipo=(TextView) findViewById(R.id.tvTipo);
            NetworkImageView imagen = (NetworkImageView) findViewById(R.id.view2);

            titulo.setText(jobj.get("marca").toString());
            subtitulo.setText(jobj.get("modelo").toString());
            tvPrecio.setText(jobj.get("precioInicial").toString());
            tvDetalle.setText(jobj.get("descripcion").toString());

            tvColor.setText(jobj.get("color").toString());
            tvAno.setText(jobj.get("ano").toString());
            tvTipo.setText(jobj.get("tipo").toString());

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
        TextView titulo = (TextView) findViewById(R.id.tituloTv2);
        TextView subtitulo = (TextView) findViewById(R.id.subtituloTv2);
        TextView tvPrecio = (TextView) findViewById(R.id.tvPrecio);
        TextView tvDetalle = (TextView) findViewById(R.id.tvDetalle);
        TextView tvColor=(TextView) findViewById(R.id.tvColor);
        TextView tvAno=(TextView) findViewById(R.id.tvAno);
        TextView tvTipo=(TextView) findViewById(R.id.tvTipo);
        NetworkImageView imagen = (NetworkImageView) findViewById(R.id.view2);
        Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap();

        Intent intent = new Intent(getApplicationContext(), Subasta.class);
        intent.putExtra("marca",titulo.getText().toString());
        intent.putExtra("modelo",subtitulo.getText().toString());
        intent.putExtra("precioInicial",tvPrecio.getText().toString());
        intent.putExtra("descripcion",tvDetalle.getText().toString());
        intent.putExtra("color",tvColor.getText().toString());
        intent.putExtra("ano",tvAno.getText().toString());
        intent.putExtra("tipo", tvTipo.getText().toString());
        intent.putExtra("bitmap",bitmap);
        startActivity(intent);
    }

}
