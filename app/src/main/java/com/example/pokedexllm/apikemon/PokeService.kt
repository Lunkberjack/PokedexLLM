package com.example.pokedexllm.apikemon

import com.example.pokedexllm.model.Detalles
import com.example.pokedexllm.model.ListaPokemons
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {
    @GET("pokemon")
    fun getPokedex(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<ListaPokemons>

    @GET("pokemon/{id}")
    fun getPokemon(
        @Path("id") id: Int
    ) : Call<Detalles>
}