package com.rensystem.a06_simple_mvvm_arquitecture.data

import com.rensystem.a06_simple_mvvm_arquitecture.data.mock.MockQuoteService
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteProvider
import com.rensystem.a06_simple_mvvm_arquitecture.data.network.QuoteService

/*
 * El repositorio abstrae las fuentes de datos, por lo que la aplicación no necesita conocer de dónde provienen las citas.
 * En el futuro, si es necesario agregar nuevas fuentes de datos (como una base de datos local), el repositorio solo tendría que integrarlas de forma transparente.

 * Repositorio encargado de gestionar el acceso a las citas desde diferentes fuentes de datos:
 * 1. Red (API): Intenta obtener las citas a través de una solicitud de red.
 * 2. Datos Mock: Si ocurre un error de red o no se puede acceder a la API, obtiene citas simuladas (mock).
 * 3. Cache Local (`QuoteProvider`): Si las citas ya están almacenadas en `QuoteProvider`, las utiliza sin hacer ninguna solicitud externa.
 *
 */
class QuoteRepository {

    private val api = QuoteService()
    private val mockApi = MockQuoteService()


    suspend fun getAllQuotes(): List<QuoteModel> {
        // Verificamos si ya existen citas en QuoteProvider
        if (QuoteProvider.quotes.isNotEmpty()) {
            // Si hay citas en QuoteProvider, las devolvemos directamente sin hacer más peticiones
            return QuoteProvider.quotes
        }

        return try {
            // Intentamos obtener las citas desde la red
            val response = api.getQuotes()

            // Guardamos las citas obtenidas en el QuoteProvider
            QuoteProvider.quotes = response

            // Retornamos las citas obtenidas
            response
        } catch (e: Exception) {
            // Si ocurre un error en la red, podemos retornar datos mockeados
            val mockQuotes = mockApi.getMockQuotes()

            // Guardamos los datos mockeados en el QuoteProvider
            QuoteProvider.quotes = mockQuotes

            // Retornamos las citas obtenidas (mock)
            mockQuotes
        }
    }
}

//class QuoteRepository {
//    private val api = QuoteService()
//    suspend fun getAllQuotes():List<QuoteModel>{
//        val response = api.getQuotes()
//        QuoteProvider.quotes = response
//        return response
//    }
//}