package com.t3_rosmery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.t3_rosmery.adapters.PokemonAdapter;
import com.t3_rosmery.clases.Pokemon;
import com.t3_rosmery.services.PokemonServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class List_pokemones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pokemones);
        RecyclerView rv = findViewById(R.id.rvPokemons);
        Button detalle = findViewById(R.id.detalle);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokemonServices service = retrofit.create(PokemonServices.class);
        Call<List<Pokemon>> pokemon = service.getAll();

        pokemon.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if(response.code() == 200){
                    List<Pokemon> pokemons  = response.body();
                    PokemonAdapter adapter = new PokemonAdapter(pokemons);
                    adapter.OnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(List_pokemones.this, Pokemon_Detalle.class);
                            String clase = new Gson().toJson(pokemons.get(rv.getChildAdapterPosition(v)));
                            intent.putExtra("clase",clase);
                            startActivity(intent);
                        }
                    });
                    rv.setAdapter(adapter);
                }else {
                    Log.i("Web","Mala conexion");
                }
            }
            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {
                Log.i("Web","No se puede conectar al servidor");
            }
        });
    }
}