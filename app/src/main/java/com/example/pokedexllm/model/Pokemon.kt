package com.example.pokedexllm.model
/**
 * En una data class de Kotlin no es necesario crear
 * los getters y setters: éstos se crean de manera
 * automática. Los valores son los que devuelve PokéAPI
 * al consultar [base-url]/pokemon
 */
    data class Pokemon (
    val id: String,
    var name: String,
    val url: String
    )