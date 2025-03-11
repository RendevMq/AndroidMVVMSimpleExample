package com.rensystem.a06_simple_mvvm_arquitecture.domain.model

import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.QuoteEntity
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel

data class Quote(val quote:String, val author:String)

//MAPPER
fun QuoteModel.toDomain() = Quote(quote,author)
fun QuoteEntity.toDomain() = Quote(quote,author)

