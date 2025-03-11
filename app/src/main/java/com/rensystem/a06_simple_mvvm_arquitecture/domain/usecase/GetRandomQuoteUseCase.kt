package com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase
import com.rensystem.a06_simple_mvvm_arquitecture.domain.QuoteRepository
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(
    private val repository : QuoteRepository
) {
    suspend operator fun invoke():Quote?{
        val quotes = repository.getAllQuotesFromDatabase()
        if(quotes.isNotEmpty()){
            val randomNumber = (0..quotes.size-1).random()
            return quotes[randomNumber]
        }
        return null
    }
}