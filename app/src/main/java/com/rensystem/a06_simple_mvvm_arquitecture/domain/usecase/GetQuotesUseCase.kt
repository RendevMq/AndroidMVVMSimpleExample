package com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase

import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.domain.QuoteRepository
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(
    private val repository : QuoteRepository
) {
    suspend operator  fun invoke():List<QuoteModel>?{
        return repository.getAllQuotes()
    }
}

