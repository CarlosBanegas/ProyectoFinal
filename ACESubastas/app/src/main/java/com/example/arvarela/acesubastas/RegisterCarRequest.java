package com.example.arvarela.acesubastas;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterCarRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://prebasdispositivosmo.comxa.com/ApiApp/RegisterCar.php";
    private Map<String, String> params;

    public RegisterCarRequest(String marca, String modelo, String color, String ano, String tipo, String foto, String descripcion, float precioInicial, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("marca", marca);
        params.put("modelo", modelo);
        params.put("color", color);
        params.put("ano", ano);
        params.put("tipo", tipo);
        params.put("foto", foto);
        params.put("descripcion", descripcion);
        params.put("precioInicial", precioInicial+"");


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
