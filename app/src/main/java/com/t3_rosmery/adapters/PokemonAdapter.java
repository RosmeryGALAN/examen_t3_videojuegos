package com.t3_rosmery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.t3_rosmery.R;
import com.t3_rosmery.clases.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>
        implements View.OnClickListener {

    List<Pokemon> mData;
    private View.OnClickListener listener;

    public PokemonAdapter(List<Pokemon> mData) {
        this.mData = mData;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        v.setOnClickListener(this);
        PokemonAdapter.PokemonViewHolder viewHolder = new PokemonAdapter.PokemonViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull PokemonAdapter.PokemonViewHolder holder, int position) {
        TextView name = holder.itemView.findViewById(R.id.nombre);
        TextView tipe = holder.itemView.findViewById(R.id.tipo);
        ImageView image = holder.itemView.findViewById(R.id.imageAnime);

        String nombre = String.valueOf(mData.get(position).getNombre());
        String tipo = String.valueOf(mData.get(position).getTipo());
        String imagen = String.valueOf(mData.get(position).getUrl_imagen());

        name.setText(nombre);
        tipe.setText(tipo);
        Picasso.get().load(imagen).into(image);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void OnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder{
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
