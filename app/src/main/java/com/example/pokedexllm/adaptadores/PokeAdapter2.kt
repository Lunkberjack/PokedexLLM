package com.example.pokedexllm.adaptadores

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedexllm.APIkemon.PokeService
import com.example.pokedexllm.DetallesPoke
import com.example.pokedexllm.MainActivity
import com.example.pokedexllm.R
import com.example.pokedexllm.ServiceGenerator
import com.example.pokedexllm.databinding.ActivityDetallesPokeBinding
import com.example.pokedexllm.databinding.ActivityMainBinding
import com.example.pokedexllm.model.Detalles
import com.example.pokedexllm.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokeAdapter2(private val list: List<Pokemon>?, private var offset: Int): RecyclerView.Adapter<PokeViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.carta_poke, parent, false)
        return PokeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        // Se accede a cada instancia de Pokémon y se retorna su nombre.
        // Nos aseguramos de que el nombre que retorna la PokéAPI no contenga palabras separadas por -,
        // ya que nuestro banco de datos de sonido solo reconoce el nombre base de cada Pokémon.
        if(list?.get(position)?.name?.equals("ho-oh") == true) {
            list[position].name = "hooh"
            holder.txtNombre.text = "Ho-oh"
        } else if(list?.get(position)?.name?.contains("-") == true) {
            list[position].name = list[position].name.split("-")[0] // Primera palabra, el nombre base
            holder.txtNombre.text = "${list[position].name}\n"
        } else {
            holder.txtNombre.text = "${(list?.get(position))?.name}\n" // Añadimos el nombre tal y como está
        }

        holder.txtId.text = "#${position + offset + 1}"
        val urlBaseSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        Glide.with(holder.imageView).asBitmap().load(urlBaseSprite + "${position + offset + 1}.png").into(holder.imageView)

        holder.fondo.setOnClickListener {
            holder.imageView.startAnimation(animacion())
            val mp: MediaPlayer = MediaPlayer()
            // Accedemos a los archivos de audio de esta página web que contiene todos los gritos de Pokémon
            val audioUrl =
                "https://play.pokemonshowdown.com/audio/cries/${list?.get(position)?.name}.ogg"
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
                val intent = Intent(holder.fondo.context, DetallesPoke::class.java).also {
                    it.putExtra("id", position + offset + 1)
                    startActivity(holder.fondo.context, it, null)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    // Devuelve el set de animación para ser usado.
    private fun animacion(): AnimationSet {
        val growTo = 1.2f
        val duration: Long = 1200

        val grow = ScaleAnimation(
            1F, growTo, 1F, growTo,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        grow.duration = duration / 2
        val shrink = ScaleAnimation(
            growTo, 1F, growTo, 1F,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        shrink.duration = duration / 2
        shrink.startOffset = duration / 2
        val growAndShrink = AnimationSet(true)
        growAndShrink.interpolator = LinearInterpolator()
        growAndShrink.addAnimation(grow)
        growAndShrink.addAnimation(shrink)

        return growAndShrink
    }
}

/**
 * Clase que contiene todos los elementos de la Carta Poke.
 */
class PokeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtNombre: TextView = itemView.findViewById(R.id.nombre)
    val txtId: TextView = itemView.findViewById(R.id.numero)
    val imageView: ImageView = itemView.findViewById(R.id.sprite)
    val fondo: LinearLayout = itemView.findViewById(R.id.fondoCarta)
}