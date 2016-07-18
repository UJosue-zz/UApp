package me.ujosue.uapp.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
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
import me.ujosue.uapp.models.Comida;
import me.ujosue.uapp.models.Tarjeta;
import me.ujosue.uapp.models.Usuario;
import me.ujosue.uapp.volley.VolleyController;

public class Detalles extends AppCompatActivity {

    @Bind(R.id.ivFotoDetalles)
    ImageView ivFotoDetalles;
    @Bind(R.id.tvNombreDetalles)
    TextView tvNombreDetalles;
    @Bind(R.id.tvPrecioDetalles)
    TextView tvPrecioDetalles;
    @Bind(R.id.tvDescripcionDetalles)
    TextView tvDescripcionDetalles;
    @Bind(R.id.etCantidadDetalles)
    EditText etCantidadDetalles;
    @Bind(R.id.btnMasDetalles)
    Button btnMasDetalles;
    @Bind(R.id.btnMinDetalles)
    Button btnMinDetalles;
    @Bind(R.id.btnComprarDetalles)
    Button btnComprarDetalles;
    @Bind(R.id.tvTotalDetalles)
    TextView tvTotalDetalles;

    Comida comida;
    Usuario usuario;
    //Tarjeta tarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        ButterKnife.bind(this);

        etCantidadDetalles.setText("1");

        comida = (Comida) getIntent().getExtras().getSerializable("comida");
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        //tarjeta = (Tarjeta) getIntent().getExtras().getSerializable("tarjeta");
        //obtenerTarjeta();

        Glide.with(this).load(comida.getFotoUrl()).into(ivFotoDetalles);
        tvNombreDetalles.setText(comida.getNombre());
        tvPrecioDetalles.setText("Precio: Q" +comida.getPrecio() + ".00");
        tvTotalDetalles.setText("Total Q" + comida.getPrecio() + ".00");
        tvDescripcionDetalles.setText(comida.getDescripcion());
    }

    /*private void obtenerTarjeta() {
        String url = "http://192.168.137.252:3000/api/v1/tarjeta?usuario=" + usuario.getId_Usuario();
        JsonObjectRequest re = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray tarArr = response.getJSONArray("tarjeta");
                    Gson gson = new Gson();
                    tarjeta = gson.fromJson(tarArr.getJSONObject(0).toString(), Tarjeta.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyController.getInstance(this).addToRequestQueue(re);
    }*/

    @OnClick(R.id.btnMasDetalles)
    public void handleClickMas(){
        int cantidad = Integer.parseInt(etCantidadDetalles.getText().toString()) + 1;
        etCantidadDetalles.setText("" + cantidad);
        tvTotalDetalles.setText("Total Q" + cantidad * comida.getPrecio() + ".00");
    }

    @OnClick(R.id.btnMinDetalles)
    public void handleClickMin(){
        if(Integer.parseInt(etCantidadDetalles.getText().toString())>0){
            int cantidad = Integer.parseInt(etCantidadDetalles.getText().toString()) - 1;
            etCantidadDetalles.setText(""  + cantidad);
            tvTotalDetalles.setText("Total Q" + cantidad * comida.getPrecio() + ".00");
        }
    }

    @OnClick(R.id.btnComprarDetalles)
    public void handleClickComprar(){
        //Snackbar.make(btnComprarDetalles.getRootView(),"Usuario:"+usuario.getId_Usuario()+"  Tarjeta: " + comida.getId_Comida() + "Cantidad: " + etCantidadDetalles.getText().toString(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
        String urlPedir = VolleyController.PEDIR_URL;
        Map<String, String> params = new HashMap<String, String>();
        params.put("usuario", String.valueOf(usuario.getId_Usuario()));
        params.put("comida", String.valueOf(comida.getId_Comida()));
        params.put("cantidad", etCantidadDetalles.getText().toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlPedir, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                navigateToMenu();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(btnComprarDetalles.getRootView(),"Saldo insuficiente :(", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });
        VolleyController.getInstance(this).addToRequestQueue(request);
    }

    private void navigateToMenu() {
        Intent intent = new Intent(this, ComidaMenu.class);
        intent.putExtra("usuario", usuario);
        //intent.putExtra("tarjeta", tarjeta);
        startActivity(intent);
    }
}
