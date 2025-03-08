package com.rensystem.a06_simple_mvvm_arquitecture.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rensystem.a06_simple_mvvm_arquitecture.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.model.QuoteProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QuoteViewModel : ViewModel() {

    //Se crea un StateFlow que contiene el QuoteModel inicializado como null
    private val _quoteModel = MutableStateFlow<QuoteModel?>(null)

    // Exposición del StateFlow como un StateFlow inmutable
    // permitiendo que otros componentes solo lean el valor y no lo modifiquen directamente.
    val quoteModel: StateFlow<QuoteModel?> = _quoteModel

    // Función que genera una nueva cita aleatoria y actualiza el valor de _quoteModel.
    fun randomQuote() {
        val currentQuote = QuoteProvider.random()
        _quoteModel.value = currentQuote // Actualiza el valor de _quoteModel con la nueva cita.
    }

}