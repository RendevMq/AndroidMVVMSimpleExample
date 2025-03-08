package com.rensystem.a06_simple_mvvm_arquitecture.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rensystem.a06_simple_mvvm_arquitecture.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.model.QuoteProvider

class QuoteViewModel : ViewModel() {

    val quoteModel = MutableLiveData<QuoteModel>()

    fun randomQuote(){
        val currentQuote = QuoteProvider.random()
        quoteModel.postValue(currentQuote)
    }


}