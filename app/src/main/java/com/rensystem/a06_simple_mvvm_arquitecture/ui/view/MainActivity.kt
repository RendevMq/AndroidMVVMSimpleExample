package com.rensystem.a06_simple_mvvm_arquitecture.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.rensystem.a06_simple_mvvm_arquitecture.databinding.ActivityMainBinding
import com.rensystem.a06_simple_mvvm_arquitecture.ui.viewmodel.QuoteViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Se usa el delegado viewModels para obtener el ViewModel
    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //La idea es que al inicar la aplicacion sera recuperar las citas para guardarlas en memoria
        quoteViewModel.onCreateMe()


        // Usamos lifecycleScope para manejar la coroutine de forma segura dentro del ciclo de vida
        lifecycleScope.launch {
            quoteViewModel.quoteModel.collect{ quote ->
                // Si la cita (quote) no es nula, actualizamos la UI con los datos
                quote?.let {
                    binding.tvQuote.text = it.quote
                    binding.tvAuthor.text = it.author
                }
            }
        }

        lifecycleScope.launch {
            // Observar el estado de carga
            quoteViewModel.isLoading.collect { state ->
                binding.progress.isVisible = state // Mostrar o esconder el ProgressBar según el estado
            }
        }

        // Al hacer clic en el contenedor, generamos una nueva cita aleatoria
        binding.viewContainer.setOnClickListener {
            quoteViewModel.randomQuote()
        }
    }
}