package com.example.pokedexllm.model

data class ListaPokemons (
    // Importante que tenga el mismo nombre que devuelve la API
    // (en este caso, el conjunto de todos los pokémons se llama
    // results, y devuelve 20 por cada llamada)
    public val results: List<Pokemon>
    )