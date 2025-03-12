package com.rensystem.a06_simple_mvvm_arquitecture.data

import com.rensystem.a06_simple_mvvm_arquitecture.data.database.dao.QuoteDao
import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.QuoteEntity
import com.rensystem.a06_simple_mvvm_arquitecture.data.mock.MockQuoteService
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.data.network.QuoteService
import com.rensystem.a06_simple_mvvm_arquitecture.domain.QuoteRepository
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.toDomain
import javax.inject.Inject
import javax.inject.Singleton

/*
 * El repositorio se encarga de abstraer el acceso a las fuentes de datos relacionadas con las citas.
 * Proporciona métodos para obtener citas desde diferentes orígenes, como una API remota, datos simulados (mock) o la base de datos local.
 * La responsabilidad del repositorio es gestionar el acceso a los datos, sin involucrarse en la lógica de persistencia o manipulación avanzada de datos.
 *
 * En el futuro, si se requieren nuevas fuentes de datos o cambios en las existentes, el repositorio puede integrarlas de manera transparente,
 * sin afectar el resto de la aplicación.
 *
 * Las funciones de persistencia y manipulación de datos en la base de datos son gestionadas fuera del repositorio, típicamente en la capa de dominio.
 */


@Singleton
class QuoteRepositoryImpl @Inject constructor(
    private val api: QuoteService,
    private val mock:MockQuoteService,
    private val quoteDao: QuoteDao
) : QuoteRepository {
    // Este repositorio solo obtiene datos de internet, no maneja almacenamiento.
    // El dominio se encarga de la persistencia y la manipulación de datos.
    override suspend fun getAllQuotesFromApi(): List<Quote> {
        return try {
            val response: List<QuoteModel> = api.getQuotes()
            response.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()// Si ocurre un error en la API, retornamos una lista vacía
        }
    }

    override suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response: List<QuoteEntity> = quoteDao.getAllQuotes()
        return response.map { it.toDomain() }
    }

    override suspend fun getAllQuotesFromMock():List<Quote>{
        val response: List<QuoteModel> = mock.getMockQuotes()
        return response.map { it.toDomain() }
    }

    override suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        if (quotes.isNotEmpty()) {
            quoteDao.insertAll(quotes)
        }
    }

    override suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
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