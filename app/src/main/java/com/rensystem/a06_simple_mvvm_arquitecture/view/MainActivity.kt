package com.rensystem.a06_simple_mvvm_arquitecture.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rensystem.a06_simple_mvvm_arquitecture.databinding.ActivityMainBinding
import com.rensystem.a06_simple_mvvm_arquitecture.viewmodel.QuoteViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Se usa el delegado viewModels para obtener el ViewModel
    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Al hacer clic en el contenedor, generamos una nueva cita aleatoria
        binding.viewContainer.setOnClickListener {
            quoteViewModel.randomQuote()
        }
    }
}