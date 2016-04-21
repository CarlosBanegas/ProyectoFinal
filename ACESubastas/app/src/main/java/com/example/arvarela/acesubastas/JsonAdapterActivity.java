package com.example.arvarela.acesubastas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ARVARELA on 16/04/2016.
 */
public class JsonAdapterActivity extends ArrayAdapter<JSONObject> {
    public JsonAdapterActivity(Context context, int resource, JSONObject[] objects){
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.complex_activity, null);
        }
        JSONObject p = getItem(position);
        if (p != null){
            TextView titulo = (TextView) v.findViewById(R.id.tituloTv1);
            TextView subtitulo = (TextView) v.findViewById(R.id.subtituloTv1);

            NetworkImageView imagen = (NetworkImageView) v.findViewById(R.id.iv);
            try {
                titulo.setText(p.get("marca").toString());
                subtitulo.setText(p.get("modelo").toString());

                imagen.setImageUrl(String.valueOf(p.getString("foto")), VolleySingletonActivity.getInstance().getImageLoader());
                //IMAGEN

              /*  String UrlImagen = String.format("http://www.kiva.org/img/120/%d.jpg"
                        ,p.getJSONObject("image").getInt("id"));

                imagen.setImageUrl(UrlImagen,VolleySingletonActivity.getInstance().getImageLoader());
                */

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return v;
    }
}
