package me.ujosue.uapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.ujosue.uapp.R;
import me.ujosue.uapp.models.Comida;

/**
 * Created by HP240 on 16/07/2016.
 */
public class ComidaAdapter extends RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder> implements View.OnClickListener {
    private List<Comida> items;
    private Comida temp;
    private View.OnClickListener listener;

    public static class ComidaViewHolder extends RecyclerView.ViewHolder{
        public ImageView imagen;
        public TextView nombre;

        public ComidaViewHolder(View v){
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagenComida);
            nombre = (TextView) v.findViewById(R.id.nombreComida);
        }



    }


    public ComidaAdapter(List<Comida> items){
        this.items = items;
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    @Override
    public ComidaViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comida_card, viewGroup , false);
        v.setOnClickListener(this);
        return new ComidaViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    @Override
    public void onBindViewHolder(ComidaViewHolder viewHolder, int i){
        Glide.with(viewHolder.imagen.getContext()).load(items.get(i).getFotoUrl()).into(viewHolder.imagen);
        viewHolder.nombre.setText(items.get(i).getNombre());
    }
}
