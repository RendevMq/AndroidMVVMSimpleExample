package com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase

import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.toDatabase
import com.rensystem.a06_simple_mvvm_arquitecture.domain.QuoteRepository
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach


// @RelaxedMockK: Crea un mock relajado de la clase QuoteRepository.
// Esto significa que no necesitamos definir explícitamente los comportamientos de sus métodos.
// Los métodos no definidos retornarán un valor por defecto (null para tipos de referencia, 0 para números, etc.).
// Usamos esta anotación para simplificar la creación de mocks sin tener que definir manualmente los comportamientos de cada méTtodo.

// @Before: Anotación de JUnit 4 (en JUnit 5 se usa @BeforeEach) que marca el métTodo para ser ejecutado antes de cada prueba.
// En este caso, inicializamos el mock y la instancia del caso de uso antes de que cada prueba se ejecute.

// MockKAnnotations.init(this): Inicializa los mocks creados con las anotaciones @MockK o @RelaxedMockK.
// Es necesario para que los mocks se configuren y se puedan usar correctamente en las pruebas.

// @Test: Esta anotación marca el métTodo como un test que se debe ejecutar.
// Es utilizada para definir el comportamiento que se quiere validar en cada prueba.

class GetQuotesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: QuoteRepository
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    // Se ejecuta antes de cada prueba
    @Before
    fun onBefore() {
        // Inicializamos el mock de repository y el caso de uso
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(repository)
    }

    @Test
    fun `should fetch quotes from API and save them to the database`() = runBlocking {
        // Given: Dado que tenemos citas disponibles desde la API
        val quotesFromApi = listOf(
            Quote("La vida es bella", "Autor A"),
            Quote("El conocimiento es poder", "Autor B")
        )

        // Mock de la función suspendida
        coEvery { repository.getAllQuotesFromApi() } returns quotesFromApi

        // When: Cuando invocamos el caso de uso
        val result = getQuotesUseCase.invoke()

        // Then: Entonces las citas deben ser guardadas en la base de datos y devueltas correctamente
        coVerify { repository.clearQuotes() }  // Verificamos que se hayan limpiado las citas
        coVerify {
            repository.insertQuotes(quotesFromApi.map { it.toDatabase() })
        }  // Verificamos la inserción
        assertEquals(quotesFromApi, result)  // Verificamos que el resultado es el esperado
    }

    @Test
    fun `should fetch mock quotes when API has no quotes`() = runBlocking {
        // Given: Dado que la API no tiene citas (retorna una lista vacía)
        val quotesFromApi = emptyList<Quote>()
        val mockQuotes = listOf(
            Quote("Cita mock 1", "Autor C"),
            Quote("Cita mock 2", "Autor D")
        )

        // Mock de la función suspendida
        coEvery { repository.getAllQuotesFromApi() } returns quotesFromApi
        coEvery { repository.getAllQuotesFromMock() } returns mockQuotes

        // When: Cuando invocamos el caso de uso
        val result = getQuotesUseCase.invoke()

        // Then: Entonces las citas mock deben ser guardadas en la base de datos y devueltas
        coVerify { repository.clearQuotes() }  // Verificamos que se hayan limpiado las citas
        coVerify {
            repository.insertQuotes(mockQuotes.map { it.toDatabase() })
        }  // Verificamos la inserción
        assertEquals(mockQuotes, result)  // Verificamos que el resultado es el esperado
    }
}