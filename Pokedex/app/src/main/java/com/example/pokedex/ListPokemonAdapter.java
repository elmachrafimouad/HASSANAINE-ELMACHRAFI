package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.example.pokedex.Interface.ItemClickListener;
import com.example.pokedex.models.Pokemon;

import java.util.ArrayList;

import javax.sql.CommonDataSource;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataset;
    private Context context;


    public ListPokemonAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);

        Glide.with(context)
                .load("https://pokeres.bastionbot.org/images/pokemon/"+ p.getNumber() + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photoImageView);
        int apasser = p.getNumber();
        String name = p.getName();

     holder.setiTemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(context,"Click at Pokemon: "+dataset.get(position).getName(),Toast.LENGTH_LONG).show();
                Intent DetailPokemon = new Intent(context, DetailPokemon.class);
                DetailPokemon.putExtra("position", apasser);
                DetailPokemon.putExtra("name",name);
                context.startActivity(DetailPokemon);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addListPokemon(ArrayList<Pokemon> listPokemon) {
        dataset.addAll(listPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photoImageView;

        ItemClickListener iTemClickListener;


        public void setiTemClickListener(ItemClickListener iTemClickListener) {
            this.iTemClickListener = iTemClickListener;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            photoImageView =(ImageView) itemView.findViewById(R.id.photoImageView);


            itemView.setOnClickListener(this);

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

        @Override
        public void onClick(View view) {
            iTemClickListener.onClick(view,getAdapterPosition());
        }
    }
}

