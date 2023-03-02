package com.example.pokedexllm

import android.R.attr.radius
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.view.ViewCompat
import androidx.core.view.marginLeft
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pokedexllm.apikemon.PokeService
import com.example.pokedexllm.apikemon.ServiceGenerator
import com.example.pokedexllm.databinding.ActivityDetallesPokeBinding
import com.example.pokedexllm.model.Detalles
import com.example.pokedexllm.model.Types
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DetallesPoke : AppCompatActivity() {
    private lateinit var bindingDetalles: ActivityDetallesPokeBinding
    private var idActual: Int = 335 // Zangoose es el default, por decisión arbitraria
    private var shiny: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDetalles = ActivityDetallesPokeBinding.inflate(layoutInflater)
        idActual = intent.getIntExtra("id", 335)
        setContentView(bindingDetalles.root) // Importante

        /**
         * Termina la actividad y vuelve al sitio en el que nos quedamos en la anterior
         * (no se pierde el scroll).
         */
        bindingDetalles.patras.setOnClickListener {
            finish()
        }

        /**
         * Detecta si el Pokémon se está visualizando en modo shiny o normal y lo cambia
         * al pulsar el botón.
         */
        bindingDetalles.shinyToggle.setOnClickListener {
            if(!shiny) {
                shiny = true
                val baseUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/"
                glideSprite(baseUrl, null) // Usamos el mismo método para no repetir código
            } else {
                shiny = false
                val baseUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
                glideSprite(baseUrl, null)
            }
        }

        obtenerDetalles()
    }

    private fun obtenerDetalles() {
        val service = ServiceGenerator.buildService(PokeService::class.java)
        // Recogemos el id del Intent que pasamos en la otra Activity (default: Zangoose):
        val llamada = service.getPokemon(intent.getIntExtra("id", 335))

        llamada.enqueue(object : Callback<Detalles> {
            override fun onResponse(call: Call<Detalles>, response: Response<Detalles>) {
                if (response.isSuccessful) {
                    idActual = response.body()?.id ?: 335
                    val baseUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

                    glideSprite(baseUrl, response)
                }
            }

            override fun onFailure(call: Call<Detalles>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString())
            }
        })
    }

    /**
     * Crea un bitmap desde una imagen en la url y además una paleta con los colores
     * más representativos de dicho bitmap.
     *
     * Si no se le pasa respuesta (viene de shinyToggle), ignora la última
     * instrucción gracias a los Nullable de Kotlin.
     */
    private fun glideSprite(baseUrl: String, response: Response<Detalles>?) {
        Glide.with(bindingDetalles.sprite)
            .asBitmap()
            .load("$baseUrl${idActual}.png")
            .into(object : CustomTarget<Bitmap?>() {
                override fun onLoadCleared(placeholder: Drawable?) {  }
                @SuppressLint("ResourceAsColor", "ResourceType")
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    bindingDetalles.sprite.setImageBitmap(resource)
                    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(resource).generate()
                    val paleta = createPaletteSync(resource)
                    bindingDetalles.fondo.setBackgroundColor(paleta.getLightVibrantColor(
                        R.color.white // Si ocurre algún problema, el fondo es blanco
                    ))

                    // Se mete dentro de onResourceReady para que no haya un pequeño instante en que el fondo
                    // aún no ha cargado, pero el texto sí.
                    if (response != null) {
                        "#${idActual.toString()}".also { bindingDetalles.id.text = it }
                        // Reemplaza el primer carácter (en minúscula) con el equivalente en mayúscula:
                        bindingDetalles.nombre.text = response.body()?.name.toString().replaceFirstChar { it.titlecase() }
                        "Altura:\n${((response.body()?.height)?.toDouble()?.div(10))} m".also { bindingDetalles.altura.text = it }
                        "Peso:\n${((response.body()?.weight)?.toDouble()?.div(10))} kg".also { bindingDetalles.peso.text = it }

                        for(tipo in response.body()?.types!!) {
                            val txtTipo = TextView(bindingDetalles.cajaTipos.context)
                            traduccion(tipo, txtTipo)
                            txtTipo.setBackgroundResource(R.drawable.borde_tipo)
                            bindingDetalles.cajaTipos.addView(txtTipo)
                        }
                    }
                }
            })
    }

    private fun traduccion(tipo: Types, txtTipo: TextView) {
        // Traducción:
        when(tipo.type.name.uppercase(Locale.ROOT)) {
            "WATER" -> txtTipo.text = "     AGUA     "
            "FIRE" -> txtTipo.text = "     FUEGO     "
            "GRASS" -> txtTipo.text = "     PLANTA     "
            "NORMAL" -> txtTipo.text = "     NORMAL     "
            "FLYING" -> txtTipo.text = "     VOLADOR     "
            "FIGHTING" -> txtTipo.text = "     LUCHA     "
            "POISON" -> txtTipo.text = "     VENENO     "
            "ELECTRIC" -> txtTipo.text = "     ELÉCTRICO     "
            "GROUND" -> txtTipo.text = "     TIERRA     "
            "ROCK" -> txtTipo.text = "     ROCA     "
            "PSYCHIC" -> txtTipo.text = "     PSÍQUICO     "
            "ICE" -> txtTipo.text = "     HIELO     "
            "BUG" -> txtTipo.text = "     BICHO     "
            "GHOST" -> txtTipo.text = "     FANTASMA     "
            "STEEL" -> txtTipo.text = "     ACERO     "
            "DRAGON" -> txtTipo.text = "     DRAGÓN     "
            "DARK" -> txtTipo.text = "     SINIESTRO     "
            "FAIRY" -> txtTipo.text = "     HADA     "
        }
    }
}