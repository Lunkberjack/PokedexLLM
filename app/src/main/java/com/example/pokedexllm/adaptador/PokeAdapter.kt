package com.example.pokedexllm.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pokedexllm.R
import com.example.pokedexllm.databinding.CartaPokeBinding
import com.example.pokedexllm.model.Pokemon

class ListaPokemonAdapter(context: Context) :
    RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>() {
    // El nombre del recurso de layout que queremos vincular (en CamelCase)
    private lateinit var binding: CartaPokeBinding
    private val dataset: ArrayList<Pokemon>
    private val context: Context

    init {
        this.context = context
        dataset = ArrayList()
        //binding = CartaPokeBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.carta_poke, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = dataset[position]
        binding.nombre.text = p.name
        Glide.with(context)
            .load(
                //"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.number()
                  //  .toString() + ".png"
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/591.png"
            )
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.sprite)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun adicionarListaPokemon(listaPokemon: ArrayList<Pokemon>?) {
        dataset.addAll(listaPokemon!!)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fotoImageView: ImageView
        private val nombreTextView: TextView

        init {
            fotoImageView = binding.sprite
            nombreTextView = binding.nombre
            // Poner el id
        }
    }
}