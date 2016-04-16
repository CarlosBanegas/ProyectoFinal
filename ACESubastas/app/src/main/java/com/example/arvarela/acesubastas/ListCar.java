package com.example.arvarela.acesubastas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListCar extends AppCompatActivity {

    private static Context context;
    public static Context getAppContext(){
        return context;
    }
    private EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car);

        this.context = getApplicationContext();
        VolleySingletonActivity vs = VolleySingletonActivity.getInstance();
        Intent detalle = new Intent(getApplicationContext(),DetalleCar.class);

        //String url = "http://api.kivaws.org/v1/loans/newest.json";
        String url = "http://prebasdispositivosmo.comxa.com/ApiApp/vehiculos.php";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = (JSONArray) response.getJSONArray("data");
                            JSONObject[] objetos = new JSONObject[array.length()];

                            for (int i = 0; i < objetos.length; i++) {
                                objetos[i] = (JSONObject) array.get(i);
                            }
                            final JsonAdapterActivity ja = new JsonAdapterActivity(getApplicationContext(), R.layout.complex_activity, objetos);
                            ((ListView) findViewById(R.id.lv)).setAdapter(ja);

                    //Filtro
                    /*        edt=(EditText) findViewById(R.id.editText);

                            edt.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    ja.getFilter().filter(s);
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                       */


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Todo: deshabilitar cuando lo vayan a subir a Playstore
                error.printStackTrace();
            }
        });


        vs.getRequestQueue().add(jor);
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                parent.getAdapter().getItem(position).toString();
                String jsonobj = parent.getAdapter().getItem(position).toString();
                Intent detalle = new Intent(getApplicationContext(),DetalleCar.class);
                detalle.putExtra("jsonObj", jsonobj);
                startActivity(detalle);
            }
        });
    }


}
