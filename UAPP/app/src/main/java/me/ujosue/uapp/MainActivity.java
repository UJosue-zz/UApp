package me.ujosue.uapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ujosue.uapp.activity.ComidaMenu;
import me.ujosue.uapp.activity.Registro;
import me.ujosue.uapp.models.Tarjeta;
import me.ujosue.uapp.models.Usuario;
import me.ujosue.uapp.volley.VolleyController;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.etCorreoLogin)
    EditText etCorreoLogin;
    @Bind(R.id.etContrasenaLogin)
    EditText etContrasenaLogin;
    @Bind(R.id.txtRegistrar)
    TextView txtRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void handleClickLogin(){
        if(!etContrasenaLogin.getText().toString().equals("") && !etCorreoLogin.getText().toString().equals("")){
            final String loginUrl = VolleyController.login + "/?correo=" + etCorreoLogin.getText().toString() + "&contrasena=" + etContrasenaLogin.getText().toString();
            JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, loginUrl, new JSONObject(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray resArray = response.getJSONArray("usuario");
                        Gson gson = new Gson();
                        Usuario currentUser = gson.fromJson(resArray.getJSONObject(0).toString(), Usuario.class);
                        navigateToComida(currentUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(btnLogin.getRootView(),"Login fallo :( " + loginUrl, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            });
            VolleyController.getInstance(btnLogin.getContext()).addToRequestQueue(peticion);
        }else if(etContrasenaLogin.getText().toString().equals("") && !etCorreoLogin.getText().toString().equals("")) {
            //Falta contrasena
            Snackbar.make(btnLogin.getRootView(),"Falta contraseña", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }else if(!etContrasenaLogin.getText().toString().equals("") && etCorreoLogin.getText().toString().equals("")) {
            //Falta correo
            Snackbar.make(btnLogin.getRootView(),"Falta correo", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }else{
            //Falta usuario y contrasena
            Snackbar.make(btnLogin.getRootView(),"Falta usuario y contraseña", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        }
    }

    @OnClick(R.id.txtRegistrar)
    public void handleClickRegistrar(){
        navigateToRegistrar();
    }

    public void navigateToComida(Usuario user){
        Intent intent = new Intent(this, ComidaMenu.class);
        intent.putExtra("usuario", user);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }


    public void navigateToRegistrar(){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
}