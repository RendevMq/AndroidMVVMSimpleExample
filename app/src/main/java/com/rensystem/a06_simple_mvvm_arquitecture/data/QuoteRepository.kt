package com.rensystem.a06_simple_mvvm_arquitecture.data

import com.rensystem.a06_simple_mvvm_arquitecture.data.mock.MockQuoteService
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteProvider
import com.rensystem.a06_simple_mvvm_arquitecture.data.network.QuoteService
import javax.inject.Inject
import javax.inject.Singleton

/*
 * El repositorio abstrae las fuentes de datos, por lo que la aplicación no necesita conocer de dónde provienen las citas.
 * En el futuro, si es necesario agregar nuevas fuentes de datos (como una base de datos local), el repositorio solo tendría que integrarlas de forma transparente.

 * Repositorio encargado de gestionar el acceso a las citas desde diferentes fuentes de datos:
 * 1. Red (API): Intenta obtener las citas a través de una solicitud de red.
 * 2. Datos Mock: Si ocurre un error de red o no se puede acceder a la API, obtiene citas simuladas (mock).
 * 3. Cache Local (`QuoteProvider`): Si las citas ya están almacenadas en `QuoteProvider`, las utiliza sin hacer ninguna solicitud externa.
 *
 */

@Singleton
class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val mockApi:MockQuoteService,
    private  val quoteProvider: QuoteProvider
){

    suspend fun getAllQuotes(): List<QuoteModel> {
        // Verificamos si ya existen citas en QuoteProvider
        if (quoteProvider.quotes.isNotEmpty()) {
            // Si hay citas en QuoteProvider, las devolvemos directamente sin hacer más peticiones
            return quoteProvider.quotes
        }

        return try {
            // Intentamos obtener las citas desde la red
            val response = api.getQuotes()

            // Guardamos las citas obtenidas en el QuoteProvider
            quoteProvider.quotes = response

            // Retornamos las citas obtenidas
            response
        } catch (e: Exception) {
            // Si ocurre un error en la red, podemos retornar datos mockeados
            val mockQuotes = mockApi.getMockQuotes()

            // Guardamos los datos mockeados en el QuoteProvider
            quoteProvider.quotes = mockQuotes

            // Retornamos las citas obtenidas (mock)
            mockQuotes
        }
    }
}

/*
 * Se utiliza @Singleton para asegurar que haya **una única instancia** del repositorio a lo largo de toda la aplicación.
 *
 * Si no se aplica @Singleton, cada caso de uso obtendría una nueva instancia del repositorio, lo que haría que cada uno
 * tenga su propio `quoteProvider`. Esto provoca que, aunque el primer repositorio haya llenado su `quoteProvider` con
 * datos, el segundo repositorio tendrá uno vacío y seguirá haciendo llamadas innecesarias a la API.
 *
 * Al usar @Singleton en el repositorio, garantizamos que **todos los casos de uso compartan la misma instancia del repositorio** y, por ende, el mismo `quoteProvider`, evitando consultas redundantes y asegurando la correcta gestión de los datos.
 *
 * Claro que también se podría haber puesto el @Singleton en el `QuoteProvider` para asegurar que solo haya una instancia de él en toda la aplicación, pero ponerlo en el repositorio garantiza que tanto el repositorio como el `quoteProvider` sean gestionados de forma coherente y como una unidad.
 */

//class QuoteRepository {
//    private val api = QuoteService()
//    suspend fun getAllQuotes():List<QuoteModel>{
//        val response = api.getQuotes()
//        QuoteProvider.quotes = response
//        return response
//    }
//}