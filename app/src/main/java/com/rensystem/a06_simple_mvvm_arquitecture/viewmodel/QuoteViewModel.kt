package com.rensystem.a06_simple_mvvm_arquitecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rensystem.a06_simple_mvvm_arquitecture.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.model.QuoteProvider

class QuoteViewModel : ViewModel() {

    //MutableLiveData qu contiene la cita actual
    private val _quoteModel = MutableLiveData<QuoteModel>()

    //LiveData inmutable expuesto
    val quoteModel: LiveData<QuoteModel> = _quoteModel

    fun randomQuote(){
        val currentQuote = QuoteProvider.random()
        _quoteModel.postValue(currentQuote) // postValue se usa para cambiar el valor desde un hilo no principal
    }

}