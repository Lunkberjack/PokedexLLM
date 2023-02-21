package com.example.pokedexllm.APIkemon

import com.example.pokedexllm.model.ListaPokemons
import com.example.pokedexllm.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeService {
    @GET("pokemon")
    fun getPokedexCompleta(): Call<ListaPokemons>
}