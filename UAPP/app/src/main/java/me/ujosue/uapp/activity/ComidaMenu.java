package me.ujosue.uapp.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.ujosue.uapp.R;
import me.ujosue.uapp.adapters.ComidaAdapter;
import me.ujosue.uapp.models.Comida;
import me.ujosue.uapp.models.Tarjeta;
import me.ujosue.uapp.models.Usuario;
import me.ujosue.uapp.volley.VolleyController;

public class ComidaMenu extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Usuario usuario;
    Tarjeta tarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_menu);

        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");
        tarjeta = (Tarjeta) getIntent().getExtras().getSerializable("tarjeta");

        inicializarMenu();
    }

    private void inicializarMenu() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, VolleyController.MENU_URL, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                final List<Comida> items = new ArrayList();
                Gson gson = new Gson();
                try {
                    JSONArray comidaArray = response.getJSONArray("comida");
                    items.add(gson.fromJson(comidaArray.get(0).toString(), Comida.class));
                    items.add(gson.fromJson(comidaArray.get(1).toString(), Comida.class));
                    /*for (int i = 0; i<comidaArray.length();i++){
                        items.add(gson.fromJson(comidaArray.get(i).toString(), Comida.class));
                    }*/
                    //items.add(new Comida(1,"Whopper", "Whooperizate!",30, "https://ujosue.files.wordpress.com/2016/07/whopper.jpg?w=1000"));
                    recyclerView = (RecyclerView) findViewById(R.id.reciclador);
                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(recyclerView.getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    ComidaAdapter comidaAdapter = new ComidaAdapter(items);
                    comidaAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Comida toDetails = items.get(recyclerView.getChildPosition(v));
                            //Snackbar.make(recyclerView.getRootView(),"tapped " + recyclerView.getChildPosition(v), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            Intent intent = new Intent(ComidaMenu.this, Detalles.class);
                            intent.putExtra("usuario", usuario);
                            intent.putExtra("tarjeta", tarjeta);
                            intent.putExtra("comida",toDetails);
                            startActivity(intent);
                        }
                    });
                    adapter = comidaAdapter;

                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Snackbar.make(recyclerView.getRootView(),"Error al inflar", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(recyclerView.getRootView(),error.toString(), Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                Log.e("Volley", error.toString());
            }
        });
        VolleyController.getInstance(this).addToRequestQueue(request);
    }
}
