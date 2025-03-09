package com.rensystem.a06_simple_mvvm_arquitecture.data.mock

import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import javax.inject.Inject

//Servicio encargado de obtener citas simuladas(mock)
class MockQuoteService @Inject constructor() {

    //Funcion para obtener citas simuladas(mock)
    suspend fun getMockQuotes() : List<QuoteModel>{
        //Devolvemos una lista de citas mockeadas
        return listOf(
            QuoteModel("Mock Quote 1", "Mock Author 1"),
            QuoteModel("Mock Quote 2", "Mock Author 2"),
            QuoteModel("Mock Quote 3", "Mock Author 3")
        )
    }
}