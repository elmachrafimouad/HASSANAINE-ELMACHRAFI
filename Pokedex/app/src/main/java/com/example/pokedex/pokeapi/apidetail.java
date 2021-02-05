package com.example.pokedex.pokeapi;

import com.example.pokedex.models.PokemonRequest;
import com.example.pokedex.models.detail;
import com.example.pokedex.models.detailrequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apidetail {


    @GET("pokemon/{indice}")
    Call<detailrequest> obtenirdetailPokemon(@Path("indice") int indice);


}
