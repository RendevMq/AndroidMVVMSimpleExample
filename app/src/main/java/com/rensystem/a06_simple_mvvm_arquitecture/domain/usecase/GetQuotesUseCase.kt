package com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase

import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.toDatabase
import com.rensystem.a06_simple_mvvm_arquitecture.domain.QuoteRepository
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote
import javax.inject.Inject

/*
*  * Repositorio encargado de gestionar el acceso a las citas desde diferentes fuentes de datos:
 * 1. Red (API): Intenta obtener las citas a través de una solicitud de red.
 * 2. Datos Mock: Si ocurre un error de red o no se puede acceder a la API, obtiene citas simuladas (mock).
 * 3. Base de datos local (Room): Si las citas ya están almacenadas en la base de datos de Room, las utiliza sin hacer ninguna solicitud externa.*/

class GetQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {
    suspend operator fun invoke(): List<Quote> {
        // Intentamos obtener las citas desde la API
        val quotesFromApi = repository.getAllQuotesFromApi()

        // Si no obtenemos citas de la API, obtenemos las citas mock
        val quotes =
            if (quotesFromApi.isNotEmpty()) quotesFromApi else repository.getAllQuotesFromMock()

        // Las citas (de la API o mock), las guardamos en la base de datos
        repository.clearQuotes()  // Limpiar las citas existentes
        repository.insertQuotes(quotes.map { it.toDatabase() })  // Insertar las nuevas citas
        return quotes
    }
}

