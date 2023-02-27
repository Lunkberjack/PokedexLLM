package com.example.pokedexllm.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexllm.R
import com.example.pokedexllm.model.Detalles
import com.example.pokedexllm.model.Pokemon

class DetallesAdapter(private val detalles: Detalles?): RecyclerView.Adapter<DetallesHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetallesHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.activity_detalles_poke, parent, false)
        return DetallesHolder(layout)
    }

    override fun onBindViewHolder(holder: DetallesHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}

class DetallesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtNombre: TextView = itemView.findViewById(R.id.nombre)
    val txtId: TextView = itemView.findViewById(R.id.numero)
    val imageView: ImageView = itemView.findViewById(R.id.sprite)
    val fondo: LinearLayout = itemView.findViewById(R.id.fondoCarta)
}