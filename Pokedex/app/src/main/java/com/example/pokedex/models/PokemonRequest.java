package com.example.pokedex.models;

import java.util.ArrayList;

public class PokemonRequest {

    private ArrayList<Pokemon> results;

    public PokemonRequest(){

    }

    public PokemonRequest(ArrayList<Pokemon> results) {
        this.results = results;
    }

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
     }
}
