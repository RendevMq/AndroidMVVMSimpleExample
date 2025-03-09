package com.rensystem.a06_simple_mvvm_arquitecture.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase.GetQuotesUseCase
import com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase.GetRandomQuoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {

    //Se crea un StateFlow que contiene el QuoteModel inicializado como null
    private val _quoteModel = MutableStateFlow<QuoteModel?>(null)

    // Exposición del StateFlow como un StateFlow inmutable
    // permitiendo que otros componentes solo lean el valor y no lo modifiquen directamente.
    val quoteModel: StateFlow<QuoteModel?> = _quoteModel

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val getQuotesUseCase = GetQuotesUseCase()
    val getRandomQuoteUseCase = GetRandomQuoteUseCase()

    fun onCreateMe() {

        viewModelScope.launch {
            _isLoading.value = true
            val result = getQuotesUseCase()

            if (!result.isNullOrEmpty()) {
                _quoteModel.value = result[0]
            }
            _isLoading.value = false // Esto debe ejecutarse siempre al finalizar la carga
        }
    }

    // Función que genera una nueva cita aleatoria y actualiza el valor de _quoteModel.
    fun randomQuote() {
        //val currentQuote = QuoteProvider.random()
        //_quoteModel.value = currentQuote // Actualiza el valor de _quoteModel con la nueva cita.
        viewModelScope.launch {
            _isLoading.value = true
            val quote = getRandomQuoteUseCase()
            if (quote != null) {
                _quoteModel.value = quote
            }
            _isLoading.value = false
        }
    }
}