package com.example.pokedex;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;


import com.airbnb.lottie.LottieAnimationView;
import com.example.pokedex.Interface.ItemClickListener;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonRequest;
import com.example.pokedex.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;

    private int offset;

    private boolean uptopara;
    LottieAnimationView pikachu;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.pokemon_list_recyclerview);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        pikachu = findViewById(R.id.lottie);







         recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();


                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                        Log.i(TAG, "arriver a la fin." );
                        uptopara = false;
                        offset += 20;
                        ObtenirData(offset);
                    }
                }
            }
        });


        retrofit = new  Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        uptopara = true;



        offset = 0;



        //bouton.callOnClick();

        ObtenirData(offset);



    }
    private int[] getLimitOffset(String name){
        switch (name){
            case "gen1": return new int[]{0, 151};
            case "gen2": return new int[]{151, 100};
            case "gen3": return new int[]{251, 135};
            case "gen4": return new int[]{386, 107};
            case "gen5": return new int[]{493, 156};
            case "gen6": return new int[]{649, 72};
            case "gen7": return new int[]{721, 86};
            case "gen8": return new int[]{887, 90};
            default: return null;

        }
    }

    private void ObtenirData(int offset) {

        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRequest> pokemonRequestCall = service.obtenirListPokemon(0, offset);

        pokemonRequestCall.enqueue(new Callback<PokemonRequest>() {
            @Override
            public void onResponse(Call<PokemonRequest> call, Response<PokemonRequest> response) {
                uptopara = true;
                if (response.isSuccessful()){
                    PokemonRequest pokemonRequest = response.body();
                    ArrayList<Pokemon> listPokemon = pokemonRequest.getResults();

                    listPokemonAdapter.addListPokemon(listPokemon);
                    pikachu.setVisibility(View.GONE);


                }else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<PokemonRequest> call, Throwable t) {
                uptopara = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });




    }


}