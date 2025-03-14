package com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase

import com.rensystem.a06_simple_mvvm_arquitecture.domain.QuoteRepository
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetRandomQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: QuoteRepository
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    //Se ejecuta antes de cada prueba
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(repository)
    }

    @Test
    fun `should return null when database is empty`() = runBlocking {
        // Given: Dado que la base de datos está vacía
        val quotesFromDatabase = emptyList<Quote>()
        // Mock: Simulamos que repository.getAllQuotesFromDatabase() devuelve una lista vacía
        coEvery { repository.getAllQuotesFromDatabase() } returns quotesFromDatabase

        // When: Llamamos al caso de uso
        val result = getRandomQuoteUseCase()

        // Then: Verificamos que el resultado sea null
        assertNull(result)  // Verificamos que el resultado sea null porque la base de datos está vacía
    }

    @Test
    fun `should return a random quote when database is not empty`() = runBlocking {

        //Given
        val quotesFromDatabase = listOf(
            Quote("Life is beautiful", "Author A"),
            Quote("Knowledge is power", "Author B"),
            Quote("Success is the sum of small efforts", "Author C")
        )
        // Mock: Simulamos que repository.getAllQuotesFromDatabase() devuelve las citas de la base de datos
        coEvery { repository.getAllQuotesFromDatabase() } returns quotesFromDatabase

        //When: Llamamos al caso de uso
        val result = getRandomQuoteUseCase()

        //Then
        // Then: Verificamos que el resultado sea una de las citas aleatorias
        assert(result in quotesFromDatabase)  // Verificamos que el resultado sea una cita de la lista
    }


}