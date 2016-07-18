package me.ujosue.uapp.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ujosue.uapp.R;
import me.ujosue.uapp.models.Usuario;
import me.ujosue.uapp.volley.VolleyController;

public class Registro extends AppCompatActivity {
    @Bind(R.id.etNombreRegistro)
    EditText etNombreRegistro;
    @Bind(R.id.etCorreoRegistro)
    EditText etCorreoRegistro;
    @Bind(R.id.etContrasenaRegistro)
    EditText etContrasenaRegistro;
    @Bind(R.id.etDireccionRegistro)
    EditText etDireccionRegistro;
    @Bind(R.id.etTarjetaRegistro)
    EditText etTarjetaRegistro;
    @Bind(R.id.etZipRegistro)
    EditText etZipRegistro;
    @Bind(R.id.btnRegistrar)
    Button btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegistrar)
    public void handleClickRegistrar(){
        if(!etNombreRegistro.getText().toString().equals("") && !etCorreoRegistro.getText().toString().equals("")
                && !etContrasenaRegistro.getText().toString().equals("") && !etDireccionRegistro.getText().toString().equals("")
                && !etTarjetaRegistro.getText().toString().equals("") && !etZipRegistro.getText().toString().equals("")){
            Map<String,String> params=new HashMap<String, String>();
            params.put("nombre", etNombreRegistro.getText().toString());
            params.put("correo", etCorreoRegistro.getText().toString());
            params.put("contrasena", etContrasenaRegistro.getText().toString());
            params.put("direccion", etDireccionRegistro.getText().toString());
            params.put("rol", "2");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, VolleyController.login, new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String loginUrl = VolleyController.login + "?correo=" + etCorreoRegistro.getText().toString()
                            + "&contrasena=" + etContrasenaRegistro.getText().toString();
                    JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, loginUrl, new JSONObject(), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject res) {
                            try {
                                JSONArray usuarioLisResponse = res.getJSONArray("usuario");
                                JSONObject usuarioRes = usuarioLisResponse.getJSONObject(0);
                                String usuarioString = usuarioRes.toString();
                                Gson gson = new Gson();
                                final Usuario user;
                                user = gson.fromJson(usuarioString, Usuario.class);
                                //Petición para guardar la tarjeta
                                Map<String, String> parametros = new HashMap<String, String>();
                                parametros.put("usuario",String.valueOf(user.getId_Usuario()));
                                parametros.put("numero", etTarjetaRegistro.getText().toString());
                                parametros.put("zip", etZipRegistro.getText().toString());
                                parametros.put("titular", user.getNombre());
                                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, VolleyController.TARJETA_URL, new JSONObject(parametros), new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        navigateToComida(user);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                VolleyController.getInstance(btnRegistrar.getContext()).addToRequestQueue(req);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    VolleyController.getInstance(btnRegistrar.getContext()).addToRequestQueue(peticion);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(btnRegistrar.getRootView(),"Falló la conexión de red, por favor revise su Router", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            });
            VolleyController.getInstance(btnRegistrar.getContext()).addToRequestQueue(request);
        }else{
            Snackbar.make(btnRegistrar.getRootView(),"Debe completar todos los campos", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }
    }

    private void navigateToComida(Usuario user) {
        //Snackbar.make(btnRegistrar.getRootView(),"Usuario" + user.getId_Usuario(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        Intent intent = new Intent(this, ComidaMenu.class);
        intent.putExtra("usuario", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
