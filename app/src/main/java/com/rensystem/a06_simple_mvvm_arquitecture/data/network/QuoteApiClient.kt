package com.rensystem.a06_simple_mvvm_arquitecture.data.network

import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET("/quotes.json")
    suspend fun getAllQuotes():Response<List<QuoteModel>>
}