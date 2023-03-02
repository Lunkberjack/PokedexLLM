package com.example.pokedexllm.adaptadores

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
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
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pokedexllm.DetallesPoke
import com.example.pokedexllm.R
import com.example.pokedexllm.model.Pokemon


class PokeAdapter(private val list: List<Pokemon>?, private var offset: Int): RecyclerView.Adapter<PokeViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.carta_poke, parent, false)
        return PokeViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        // Se accede a cada instancia de Pokémon y se retorna su nombre.
        // Nos aseguramos de que el nombre que retorna la PokéAPI no contenga palabras separadas por -,
        // ya que nuestro banco de datos de sonido solo reconoce el nombre base de cada Pokémon, y del
        // sonido depende que se pueda llamar a la nueva actividad de los detalles.
        if((position + offset + 1) == 29) { //  -------------------------------------- Casos especiales
            list?.get(position)?.name = "nidoranf"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 32) {
            list?.get(position)?.name = "nidoranm"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 122) {
            list?.get(position)?.name = "mrmime"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 439) {
            list?.get(position)?.name = "mimejr"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 250) {
            list?.get(position)?.name = "hooh"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 772) {
            list?.get(position)?.name = "typenull"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 782) {
            list?.get(position)?.name = "jangmoo"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 783) {
            list?.get(position)?.name = "hakamoo"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 784) {
            list?.get(position)?.name = "kommoo"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 785) {
            list?.get(position)?.name = "tapukoko"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 786) {
            list?.get(position)?.name = "tapulele"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 787) {
            list?.get(position)?.name = "tapubulu"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if((position + offset + 1) == 788) {
            list?.get(position)?.name = "tapufini"
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it }
        } else if(list?.get(position)?.name?.contains("-") == true) {
            list[position].name = list[position].name.split("-")[0] // ---- Primera palabra, el nombre base
            "${list[position].name}\n".also { holder.txtNombre.text = it }
        } else {
            "${(list?.get(position))?.name}\n".also { holder.txtNombre.text = it } // -- Añadimos el nombre tal y como está
        }

        "#${position + offset + 1}".also { holder.txtId.text = it }
        val urlBaseSprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        //Glide.with(holder.imageView).asBitmap().load(urlBaseSprite + "${position + offset + 1}.png").into(holder.imageView)

        Glide.with(holder.imageView)
            .asBitmap()
            .load(urlBaseSprite + "${position + offset + 1}.png")
            .into(object : CustomTarget<Bitmap?>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    holder.imageView.setImageBitmap(resource)
                    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(resource).generate()
                    holder.txtNombre.setTextColor(createPaletteSync(resource).getLightVibrantColor(
                        0xFFFFFFFF.toInt() // Si ocurre algún problema, la letra es blanca
                    ))
                }
            })

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
                // Llama a la nueva actividad (cuando acaba el grito del Pokémon) y pasa una variable
                // en el Intent (el id) que permitirá a la siguiente actividad modificar el parámetro
                // del método GET que se realizará a la API.
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