package com.example.arvarela.acesubastas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class Subasta extends AppCompatActivity {
    Button buttonOfertar;
    TextView textView, et2,et3;
    int resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subasta);
       consulta(); //Funcion para conlsutar la informacion del auto a subastar
        buttonOfertar=(Button) findViewById(R.id.buttonOfertarSubasta);
        textView=(TextView) findViewById(R.id.textViewCronometroSubasta);
        textView.setText("00:00:10");
        TextView precio = (TextView) findViewById(R.id.textViewPrecioInicialSubasta);
        TextView valorAcumulado = (TextView) findViewById(R.id.textViewValorAcumulado);
        TextView valorOfertar = (TextView) findViewById(R.id.textViewValorOfertar);
        valorOfertar.setText("100");
        valorAcumulado.setText(precio.getText());


        final CounterClass timer= new CounterClass(10000,1000);
        buttonOfertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                Suma();

            }
        });
    }

    public void Suma(){

        et3 = (TextView) findViewById(R.id.textViewValorAcumulado);
        et2 = (TextView) findViewById(R.id.textViewValorOfertar);

        int aux3 = Integer.valueOf(et3.getText().toString());
        int aux2 = Integer.valueOf(et2.getText().toString());

        resultado = aux2 + aux3;
        et3.setText(String.valueOf(resultado));

    } //Funcion para ofertar por el vehiculo

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture,long countDownInterval){
            super(millisInFuture,countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            long millis=millisUntilFinished;
            String hms=String.format("%02d:%02d:%02d", TimeUnit.MICROSECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.print(hms);
            textView.setText(hms);
        }
        @Override
        public void onFinish() {
            textView.setText("Has Ganado la Subasta en: "+resultado);
            buttonOfertar.setEnabled(false);
        }
    } //Funcion para el cronometro

    public void consulta(){
        try{
          //  JSONObject jobj = new JSONObject((getIntent().getExtras().getString("jsonObj")));
            //JSONObject jobj = new JSONObject(getIntent().getExtras().getString("JsonObj"));
            TextView marca = (TextView) findViewById(R.id.textViewMarcaSubasta);
            TextView modelo = (TextView) findViewById(R.id.textViewModeloSubasta);
            TextView descripcion = (TextView) findViewById(R.id.textViewDescripcionSubasta);
            TextView año = (TextView) findViewById(R.id.textViewAñoSubasta);
            TextView precio = (TextView) findViewById(R.id.textViewPrecioInicialSubasta);
            TextView tipo = (TextView) findViewById(R.id.textViewTipoVehiculoSubasta);
            TextView color = (TextView) findViewById(R.id.textViewColorSubasta);

            NetworkImageView imagen = (NetworkImageView) findViewById(R.id.view2);

            Bundle bundle=getIntent().getExtras();

            marca.setText(bundle.getString("marca"));
            modelo.setText(bundle.getString("modelo"));
            año.setText(bundle.getString("ano"));
            descripcion.setText(bundle.getString("descripcion"));
            precio.setText(bundle.getString("precioInicial"));
            tipo.setText(bundle.getString("tipo"));
            color.setText(bundle.getString("color"));



            Intent intent = getIntent();
            Bitmap bitmap = intent.getParcelableExtra("bitmap");
            imagen.setImageBitmap(bitmap);


        /*    marca.setText(jobj.get("marca").toString());
            modelo.setText(jobj.get("modelo").toString());
            millaje.setText(jobj.get("millaje").toString());
            año.setText(jobj.get("ano").toString());
            precio.setText(jobj.get("precioInicial").toString());
            valorAcumulado.setText(jobj.get("precioInicial").toString());


            imagen.setImageUrl(String.valueOf(jobj.getString("foto")), VolleySingletonActivity.getInstance().getImageLoader());
            String UrlImagen = String.format("http://www.kiva.org/img/120/%d.jpg",
                    jobj.getJSONObject("image").getInt("id"));

            imagen.setImageUrl(UrlImagen,VolleySingletonActivity.getInstance().getImageLoader());
            */
        } catch (Exception e){
            e.printStackTrace();
        }
    } //Funcion para consultar la informacion del vehiculo a subastar
}
