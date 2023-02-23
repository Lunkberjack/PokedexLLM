package com.example.pokedexllm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexllm.APIkemon.PokeService
import com.example.pokedexllm.adaptador.PokeAdapter2
import com.example.pokedexllm.databinding.ActivityMainBinding
import com.example.pokedexllm.databinding.ToolBarBinding
import com.example.pokedexllm.model.ListaPokemons
import com.example.pokedexllm.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingBarra: ToolBarBinding

    var data = arrayListOf<Pokemon>()
    var offset: Int = 0
    var cargar: Boolean = true
    val manager: GridLayoutManager = GridLayoutManager(this, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        val adapter = PokeAdapter2(null, 0)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.btn1.setOnClickListener {
            obtenerDatos(1)
        }
        binding.btn2.setOnClickListener {
            obtenerDatos(2)
        }
        binding.btn3.setOnClickListener {
            obtenerDatos(3)
        }
        binding.btn4.setOnClickListener {
            obtenerDatos(4)
        }
        binding.btn5.setOnClickListener {
            obtenerDatos(5)
        }
        binding.btn6.setOnClickListener {
            obtenerDatos(6)
        }
        binding.btn7.setOnClickListener {
            obtenerDatos(7)
        }

        binding.recyclerView.layoutManager = manager
        cargar = true
       //offset = 0
        obtenerDatos(1)
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

        /*        val nestedScrollView = binding.recyclerView
        nestedScrollView.viewTreeObserver?.addOnScrollChangedListener {
            if (!nestedScrollView.canScrollVertically(1)) {
                offset += 20
                obtenerDatos(offset)
            }
        }
*/
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
                cargar = true
                if (response.isSuccessful) {
                    // Para comprobar que se han recibido los datos
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