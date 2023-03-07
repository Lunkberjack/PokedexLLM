package com.example.pokedexllm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedexllm.databinding.ActivityAyudaInteractivaBinding
import com.example.pokedexllm.databinding.ActivityDetallesPokeBinding

class AyudaInteractiva : AppCompatActivity() {
    private lateinit var bindingAyuda: ActivityAyudaInteractivaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAyuda = ActivityAyudaInteractivaBinding.inflate(layoutInflater)
        setContentView(bindingAyuda.root)

        bindingAyuda.atras.setOnClickListener {
            finish()
        }
    }
}