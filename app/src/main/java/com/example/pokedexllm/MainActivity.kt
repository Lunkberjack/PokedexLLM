package com.example.pokedexllm

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedexllm.apikemon.PokeService
import com.example.pokedexllm.apikemon.ServiceGenerator
import com.example.pokedexllm.adaptadores.PokeAdapter
import com.example.pokedexllm.databinding.ActivityMainBinding
import com.example.pokedexllm.model.ListaPokemons
import com.example.pokedexllm.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private val POKEMON_TOTALES = 808
    private lateinit var binding: ActivityMainBinding
    private var generacionActual = 1

    var data = arrayListOf<Pokemon>()
    private val manager: GridLayoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        val adapter = PokeAdapter(null, 0)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.btn1.setOnClickListener {
            generacionActual = 1
            obtenerDatos(generacionActual)
        }
        binding.btn2.setOnClickListener {
            generacionActual = 2
            obtenerDatos(generacionActual)
        }
        binding.btn3.setOnClickListener {
            generacionActual = 3
            obtenerDatos(generacionActual)
        }
        binding.btn4.setOnClickListener {
            generacionActual = 4
            obtenerDatos(generacionActual)
        }
        binding.btn5.setOnClickListener {
            generacionActual = 5
            obtenerDatos(generacionActual)
        }
        binding.btn6.setOnClickListener {
            generacionActual = 6
            obtenerDatos(generacionActual)
        }
        binding.btn7.setOnClickListener {
            generacionActual = 7
            obtenerDatos(generacionActual)
        }

        // Un Pokémon random de cualquier generación al pulsar la Pokéball.
        binding.random.setOnClickListener {
            val rnd = Random().nextInt(POKEMON_TOTALES)+1
            val mp: MediaPlayer = MediaPlayer.create(binding.random.context, R.raw.pokeball)
            mp.start()
            mp.setOnCompletionListener {
                mp.release()
            }
            val intent = Intent(binding.random.context, DetallesPoke::class.java).also {
                it.putExtra("id", rnd)
                ContextCompat.startActivity(binding.random.context, it, null)
            }
        }

        // Ayuda (el Unown flotando libre a la derecha de la pantalla)
        binding.ayuda.setOnClickListener {
            val intent = Intent(binding.random.context, AyudaInteractiva::class.java).also {
                ContextCompat.startActivity(binding.ayuda.context, it, null)
            }
        }

        binding.recyclerView.layoutManager = manager
        obtenerDatos(generacionActual)
    }

    /**
     * Se encarga de las llamadas a la API y su manejo.
     */
    private fun obtenerDatos(generacion: Int) {
        var offset: Int = 0
        val service = ServiceGenerator.buildService(PokeService::class.java)
        var llamada = service.getPokedex(151, 0)

        when (generacion) {
            1 -> {
                offset = 0
                llamada = service.getPokedex(151, offset)
            }
            2 -> {
                offset = 151
                llamada = service.getPokedex(100, offset)
            }
            3-> {
                offset = 251
                llamada = service.getPokedex(135, 251)
            }
            4-> {
                offset = 386
                llamada = service.getPokedex(107, offset)
            }
            5-> {
                offset = 493
                llamada = service.getPokedex(156, offset)
            }
            6-> {
                offset = 649
                llamada = service.getPokedex(72, offset)
            }
            7-> {
                offset = 721
                llamada = service.getPokedex(88, offset)
            }
        }

        llamada.enqueue(object : Callback<ListaPokemons> {
            override fun onResponse(
                call: Call<ListaPokemons>,
                response: Response<ListaPokemons>
            ) {
                if (response.isSuccessful) {
                    // Para comprobar que se han recibido los datos
                    // Log.e("poke", response.body().toString())
                    val adapter = response.body()?.let { PokeAdapter(it.results, offset) }
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ListaPokemons>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString())
            }
        })
    }
}