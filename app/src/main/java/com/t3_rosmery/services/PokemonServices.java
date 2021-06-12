package com.t3_rosmery.services;

import com.t3_rosmery.clases.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokemonServices {
    @GET("pokemons/N00033835")
    Call<List<Pokemon>> getAll();

    @POST("pokemons/N00033835/crear")
    Call<Pokemon> create(@Body Pokemon pokemon);
}
