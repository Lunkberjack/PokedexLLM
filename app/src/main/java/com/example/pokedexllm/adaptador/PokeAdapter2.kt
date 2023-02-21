package com.example.pokedexllm.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexllm.R
import com.example.pokedexllm.model.Pokemon

class PokeAdapter2(val list: List<Pokemon>): RecyclerView.Adapter<PokeViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.carta_poke, parent, false)
        return PokeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        // Se accede a cada instancia de Pokémon y se retorna su nombre
        holder.txtNombre.text = list[position].name
        holder.txtId.text = "ID: ${position + 1}"
        val urlBaseSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        Glide.with(holder.imageView).load(urlBaseSprite + "${position + 1}.png").into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class PokeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtNombre: TextView = itemView.findViewById(R.id.nombre)
    val txtId: TextView = itemView.findViewById(R.id.numero)
    val imageView: ImageView = itemView.findViewById(R.id.sprite)
}