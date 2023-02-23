package com.example.pokedexllm.adaptador

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexllm.R
import com.example.pokedexllm.model.Pokemon


class PokeAdapter2(val list: List<Pokemon>?, offset: Int): RecyclerView.Adapter<PokeViewHolder> () {
    var offset: Int = offset
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.carta_poke, parent, false)
        return PokeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.fondo.setOnClickListener {
            val mp: MediaPlayer = MediaPlayer()
            // on below line we are creating a variable for our audio url
            var audioUrl =
                "https://play.pokemonshowdown.com/audio/cries/${(list?.get(position))?.name}.ogg"
            mp.setDataSource(audioUrl)
            mp.setAudioAttributes(
                AudioAttributes.Builder()
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build()
            )
            mp.prepare()
            mp.setOnPreparedListener {
                mp.start()
            }
            mp.setOnCompletionListener {
                mp.release()
            }
        }
        // Se accede a cada instancia de Pokémon y se retorna su nombre
        holder.txtNombre.text = "${(list?.get(position))?.name}\n"
        holder.txtId.text = "#${position + offset + 1}"
        val urlBaseSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        Glide.with(holder.imageView).asBitmap().load(urlBaseSprite + "${position + offset + 1}.png").into(holder.imageView)

        /*     Glide
                .with(holder.imageView)
            .asBitmap()
            .load(urlBaseSprite + "${position + 1}.png")
            .into(holder.imageView)

        val palette = Palette.from().generate()
        palette.apply {
            palette.vibrantSwatch?.let { holder.fondo.setBackgroundColor(it.rgb) }
            }
    }*/

    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}


class PokeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtNombre: TextView = itemView.findViewById(R.id.nombre)
    val txtId: TextView = itemView.findViewById(R.id.numero)
    val imageView: ImageView = itemView.findViewById(R.id.sprite)
    val fondo: LinearLayout = itemView.findViewById(R.id.fondoCarta)
}