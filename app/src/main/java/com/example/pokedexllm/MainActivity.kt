package com.example.pokedexllm

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
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
    var offset: Int = 0
    var cargar: Boolean = true
    val manager: GridLayoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        val adapter = PokeAdapter2(null, 0)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        val nestedScrollView = binding.recyclerView
        nestedScrollView.viewTreeObserver?.addOnScrollChangedListener {
            if (!nestedScrollView.canScrollVertically(1)) {
                offset += 20
                obtenerDatos(offset)
            }
        }
*/
        //scrolleo()
        binding.recyclerView.layoutManager = manager
        cargar = true
       //offset = 0
        obtenerDatos(5)
    }

    private fun scrolleo() {
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(dy > 0) {
                    val visibleItemCount: Int = manager.childCount
                    val totalItemCount: Int = manager.itemCount
                    val pastVisibleItems: Int = manager.findFirstVisibleItemPosition()

                    if(cargar) {
                        if((visibleItemCount+pastVisibleItems) >= totalItemCount) {
                            cargar = false
                            offset += 20
                            obtenerDatos(2)
                        }
                    }
                }
            }
        })
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


            // 3-> llamada = service.getPokedex()
            //4-> llamada = service.getPokedex()
            //5-> llamada = service.getPokedex()
            //6-> llamada = service.getPokedex()
        }
        // La primera generación de Pokémon
        llamada.enqueue(object : Callback<ListaPokemons> {
            override fun onResponse(
                call: Call<ListaPokemons>,
                response: Response<ListaPokemons>
            ) {
                cargar = true
                if (response.isSuccessful) {
                    Log.e("poke", response.body().toString())
                    val adapter = response.body()?.let { PokeAdapter2(it.results, offset) }
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