package com.rensystem.a06_simple_mvvm_arquitecture.domain

import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.QuoteEntity
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote

/*
 * ACTUAL SITUACIÓN Y FUTURA MEJORA:
 * Actualmente, el repositorio y otras clases del proyecto dependen directamente del mismo modelo de datos
 * que proviene de la capa **Data**. Esto crea un acoplamiento entre las capas, lo que puede dificultar
 * el mantenimiento y la flexibilidad en el futuro.
 *
 * Como mejora futura, se recomienda introducir un mapper que permita transformar los modelos entre las capas
 * (Data, Domain, Presentation). Esto permitirá que cada capa gestione su propio modelo de datos de manera independiente,
 * mejorando el desacoplamiento y facilitando la escalabilidad del sistema.
 */

interface QuoteRepository {
    suspend fun getAllQuotesFromApi(): List<Quote>
    suspend fun getAllQuotesFromDatabase(): List<Quote>
    suspend fun getAllQuotesFromMock():List<Quote>
    suspend fun insertQuotes(quotes:List<QuoteEntity>)
    suspend fun clearQuotes()
}