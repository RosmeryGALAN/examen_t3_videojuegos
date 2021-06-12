package com.t3_rosmery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.t3_rosmery.clases.Pokemon;

public class Pokemon_Detalle extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_pokemon);
        ImageView imagen = findViewById(R.id.imagenPokemon);
        TextView nombre = findViewById(R.id.nombreP);
        TextView tipo = findViewById(R.id.tipoP);
        TextView codigo = findViewById(R.id.codigoP);
        TextView latitud = findViewById(R.id.latitude);
        TextView longitud = findViewById(R.id.longitude);

        Intent intent = getIntent();
        String clase = intent.getStringExtra("clase");
        Pokemon pokemon = new Gson().fromJson(clase,Pokemon.class);

        String latitude = String.valueOf(pokemon.getLatitude());
        String longitude = String.valueOf(pokemon.getLongitude());

        nombre.setText(pokemon.getNombre());
        tipo.setText(pokemon.getTipo());
        codigo.setText(pokemon.getCodigo());
        latitud.setText(latitude);
        longitud.setText(longitude);
        Picasso.get().load( pokemon.getUrl_imagen()).into(imagen);
    }
}
