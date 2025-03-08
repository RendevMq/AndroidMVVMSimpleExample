package com.rensystem.a06_simple_mvvm_arquitecture.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rensystem.a06_simple_mvvm_arquitecture.databinding.ActivityMainBinding
import com.rensystem.a06_simple_mvvm_arquitecture.viewmodel.QuoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Delegado viewModels que crea y vincula el ViewModel a la actividad
    private val quoteViewModel: QuoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Oservamos los cambios en LiveData y actualizamos la UI
        quoteViewModel.quoteModel.observe(this) { quote ->
            // Si la cita no es nula, actualizamos la UI con los datos
            quote?.let {
                binding.tvQuote.text = it.quote
                binding.tvAuthor.text = it.author
            }
        }

        // Al hacer clic en el contenedor, generamos una nueva cita aleatoria
        binding.viewContainer.setOnClickListener {
            quoteViewModel.randomQuote()
        }
    }
}