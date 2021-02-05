package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pokedex.models.detail;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonRequest;
import com.example.pokedex.models.detail;
import com.example.pokedex.models.detail2;
import com.example.pokedex.models.detailrequest;
import com.example.pokedex.pokeapi.PokeapiService;
import com.example.pokedex.pokeapi.apidetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailPokemon extends AppCompatActivity {

    private TextView nom, detail, taille, poids, type;
    private ImageView imagepokemon;
    private CardView card;

    private static final String TAG = "POKEDEX";
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);

        imagepokemon = (ImageView) findViewById(R.id.photo);
        nom = (TextView) findViewById(R.id.name);
        detail = (TextView) findViewById(R.id.detail);
        taille = (TextView) findViewById(R.id.taille);
        poids = (TextView) findViewById(R.id.poids);
        type = (TextView) findViewById(R.id.type);
        card =(CardView)findViewById(R.id.card_view1);

        Intent detailPokemon = getIntent();
        if (detailPokemon != null) {
            int indice;
            String nompok;
            if (detailPokemon.hasExtra("position")) { // vérifie qu'une valeur est associée à la clé “edittext”
                indice = detailPokemon.getIntExtra("position", 1); // on récupère la valeur associée à la clé
                //Pokemon p = dataset.get(indice);
                nompok = detailPokemon.getStringExtra("name");

                Glide.with(DetailPokemon.this)
                        .load("https://pokeres.bastionbot.org/images/pokemon/" + indice + ".png")
                        .centerCrop()
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imagepokemon);
                nom.setText(nompok);

                retrofit = new Retrofit.Builder()
                        .baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apidetail service = retrofit.create(apidetail.class);

                service.obtenirdetailPokemon(indice).enqueue(new Callback<detailrequest>() {
                    @Override
                    public void onResponse(Call<detailrequest> call, Response<detailrequest> response) {

                        if (response.isSuccessful()){
                            detailrequest poke = response.body();

                            List<detail> listPoke = poke.getTypes();

                              detail p = listPoke.get(0);

                                  detail2 aicha = p.getType();
                                  String mouad = aicha.getName();
                                  if(mouad.equals("water")){
                                      card.setCardBackgroundColor(0xff0000ff);
                                  }
                                  if(mouad.equals("grass")){
                                      card.setCardBackgroundColor(0xFF00FF00);
                                  }
                                  if(mouad.equals("poison")){
                                      card.setCardBackgroundColor(0xff888888);
                                  }
                                  if (mouad.equals("fire")){
                                      card.setCardBackgroundColor(0xffff0000);
                                  }
                                  if (mouad.equals("electric")){
                                      card.setCardBackgroundColor(0xffffff00);
                                  }
                                  if (mouad.equals("bug")){
                                      card.setCardBackgroundColor(0xff000000);
                                  }
                                  if (mouad.equals("fairy")){
                                      card.setCardBackgroundColor(0xff444444);
                                  }
                                  if (mouad.equals("ground")){
                                      card.setCardBackgroundColor(0xffff00ff);
                                  }

                                  type.setText(mouad);

                            int a = poke.getWeight();
                            int b = poke.getHeight();
                            poids.setText(Integer.toString(a)+" kg");
                            taille.setText(Integer.toString(b)+" m");

                        }else {
                            Log.e(TAG, "onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<detailrequest> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        }
    }


}