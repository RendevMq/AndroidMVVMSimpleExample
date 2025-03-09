package com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase
import com.rensystem.a06_simple_mvvm_arquitecture.data.QuoteRepository
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel

class GetRandomQuoteUseCase {

    private val repository = QuoteRepository()

    suspend operator fun invoke():QuoteModel?{
        val quotes = repository.getAllQuotes()
        if(quotes.isNotEmpty()){
            val randomNumber = (0..quotes.size-1).random()
            return quotes[randomNumber]
        }
        return null
    }

}