package com.example.pokedexllm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.pokedexllm.APIkemon.PokeService
import com.example.pokedexllm.databinding.ActivityDetallesPokeBinding
import com.example.pokedexllm.databinding.ActivityMainBinding
import com.example.pokedexllm.model.Detalles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetallesPoke : AppCompatActivity() {
    private lateinit var bindingDetalles: ActivityDetallesPokeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDetalles = ActivityDetallesPokeBinding.inflate(layoutInflater)
        setContentView(bindingDetalles.root) // Importante

        obtenerDetalles()
    }

    private fun obtenerDetalles() {
        val service = ServiceGenerator.buildService(PokeService::class.java)
        // Recogemos el dato del Intent que pasamos en la otra Activity:
        val llamada = service.getPokemon(intent.getIntExtra("id", 335))

        llamada.enqueue(object : Callback<Detalles> {
            override fun onResponse(call: Call<Detalles>, response: Response<Detalles>) {
                if (response.isSuccessful) {
                    val baseUrl: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
                    bindingDetalles.nombre.text = response.body()?.name.toString()
                    Glide
                        .with(bindingDetalles.sprite)
                        .asBitmap()
                        .load("$baseUrl${response.body()?.id}.png")
                        .into(bindingDetalles.sprite)
                }
            }

            override fun onFailure(call: Call<Detalles>, t: Throwable) {
                t.printStackTrace()
                Log.e("Error", t.message.toString())
            }
        })
    }
}