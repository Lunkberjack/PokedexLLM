package com.example.pokedexllm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexllm.APIkemon.PokeService
import com.example.pokedexllm.adaptador.PokeAdapter2
import com.example.pokedexllm.databinding.ActivityMainBinding
import com.example.pokedexllm.model.ListaPokemons
import com.example.pokedexllm.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var data = arrayListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        val service = ServiceGenerator.buildService(PokeService::class.java)
        val llamada = service.getPokedexCompleta()

        llamada.enqueue(object: Callback<ListaPokemons> {
            override fun onResponse(call:Call<ListaPokemons>, response: Response<ListaPokemons>) {
                if(response.isSuccessful) {
                    Log.e("poke", response.body().toString())
                    val adapter = response.body()?.let { PokeAdapter2(it.results) }
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