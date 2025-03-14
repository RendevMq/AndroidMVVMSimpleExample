package com.rensystem.a06_simple_mvvm_arquitecture.ui.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote
import com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase.GetQuotesUseCase
import com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase.GetRandomQuoteUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

// La anotación @ExperimentalCoroutinesApi permite el uso de APIs experimentales de coroutines para pruebas.
// En este caso, la usamos porque estamos trabajando con `runTest`, que es una API experimental de testing de coroutines.

// La regla InstantTaskExecutorRule es una regla proporcionada por Android para garantizar que LiveData y StateFlow
// se actualicen correctamente durante las pruebas. Esencialmente, asegura que las tareas pendientes en LiveData
// se ejecuten de forma instantánea, facilitando las pruebas sin tener que esperar a que las tareas de ejecución en
// segundo plano se completen (como las coroutines).

// `Dispatchers.setMain()` cambia el dispatcher predeterminado a uno que podemos controlar durante las pruebas.
// Esto permite que nuestras pruebas de coroutines se ejecuten de manera controlada, lo que facilita la prueba
// del ciclo de vida del `ViewModel`, que usa `viewModelScope.launch` para ejecutar las coroutines en el hilo principal.
// Usamos `Dispatchers.setMain(Dispatchers.Unconfined)` para evitar un bloqueo en el hilo principal durante las pruebas.

@ExperimentalCoroutinesApi
class QuoteViewModelTest {

    // Regla para permitir que LiveData y StateFlow se actualicen correctamente en pruebas
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: QuoteViewModel
    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase
    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        // Creamos los mocks
        MockKAnnotations.init(this)

        // Usamos el dispatcher de prueba para controlar la ejecución de las coroutines
        Dispatchers.setMain(Dispatchers.Unconfined)

        // Inicializamos el ViewModel con los mocks
        viewModel = QuoteViewModel(getQuotesUseCase, getRandomQuoteUseCase)
    }

    // Limpiamos el dispatcher después de cada test
    // Esto restablece el dispatcher principal a su estado original después de cada prueba,
    // garantizando que cada prueba corra en un entorno limpio y controlado.
    @After
    fun onAfter() {
        Dispatchers.resetMain()  // Restablecemos el dispatcher principal
    }

    @Test
    fun `should update quoteModel with the first quote from API`() = runTest {
        // Given: Simulamos que GetQuotesUseCase devuelve una lista de citas
        val quotes = listOf(Quote("Life is beautiful", "Author A"))
        coEvery { getQuotesUseCase() } returns quotes

        // When: Llamamos a la función onCreateMe del ViewModel
        viewModel.onCreateMe()

        // Then: Verificamos que _quoteModel se haya actualizado con la primera cita de la lista
        advanceUntilIdle()  // Avanzamos hasta que las coroutines en el viewModel hayan terminado

        // Verificamos que el valor de quoteModel sea el esperado
        assertEquals(Quote("Life is beautiful", "Author A"), viewModel.quoteModel.value)
    }

    @Test
    fun `should handle empty API response and fetch mock quotes`() = runTest {
        // Given: Simulamos que GetQuotesUseCase devuelve una lista vacía, y GetRandomQuoteUseCase devuelve una cita mock
        val emptyQuotes = emptyList<Quote>()
        val mockQuote = Quote("Mock quote", "Author C")
        coEvery { getQuotesUseCase() } returns emptyQuotes
        coEvery { getRandomQuoteUseCase() } returns mockQuote

        // When: Llamamos a la función onCreateMe y luego a randomQuote
        viewModel.onCreateMe()  // Esto debería llamar al API
        viewModel.randomQuote()  // Esto debería llamar a la función randomQuote

        // Then: Verificamos que _quoteModel se haya actualizado con la cita mock
        advanceUntilIdle()  // Avanzamos hasta que las coroutines en el viewModel hayan terminado

        // Verificamos que el valor de quoteModel sea la cita mock
        assertEquals(mockQuote, viewModel.quoteModel.value)
    }

    @Test
    fun `should set first quote when viewModel is created for the first time`() = runTest {
        // Given: Simulamos que GetQuotesUseCase devuelve una lista de citas
        val quotes = listOf(Quote("Life is beautiful", "Author A"))
        coEvery { getQuotesUseCase() } returns quotes

        // Cuando: Llamamos a onCreateMe
        viewModel.onCreateMe()
        advanceUntilIdle()  // Avanzamos hasta que las coroutines hayan terminado

        // Then: Verificamos que el primer valor de quoteModel sea la primera cita de la lista
        assertEquals(Quote("Life is beautiful", "Author A"), viewModel.quoteModel.value)
    }

    @Test
    fun `should keep the last value when randomQuoteUseCase returns null`() = runTest {
        // Given: Simulamos que GetQuotesUseCase devuelve una lista de citas más extensa
        val quotes = listOf(
            Quote("Life is beautiful", "Author A"),
            Quote("Knowledge is power", "Author B"),
            Quote("The only way to do great work is to love what you do", "Author C")
        )

        // Simulamos que GetQuotesUseCase devuelve las citas
        coEvery { getQuotesUseCase() } returns quotes

        // When: Inicializamos el ViewModel y llamamos a onCreateMe para obtener la primera cita
        viewModel.onCreateMe()

        // Then: Verificamos que _quoteModel se actualice con la primera cita
        advanceUntilIdle()  // Avanzamos hasta que todas las coroutines se hayan completado
        assertEquals(Quote("Life is beautiful", "Author A"), viewModel.quoteModel.value)

        // --- Cambio de contexto ---

        // Given: Simulamos que randomQuoteUseCase retorna null
        coEvery { getRandomQuoteUseCase() } returns null

        // When: Llamamos a randomQuote cuando randomQuoteUseCase retorna null
        viewModel.randomQuote()

        // Then: Verificamos que _quoteModel no haya cambiado y mantiene la última cita
        advanceUntilIdle()
        assertEquals(Quote("Life is beautiful", "Author A"), viewModel.quoteModel.value)

        // --- Repetición de la misma acción ---

        // When: Llamamos nuevamente a randomQuote para asegurarnos de que el valor no cambie
        viewModel.randomQuote()
        advanceUntilIdle()

        // Then: Verificamos nuevamente que el valor sigue siendo el mismo
        assertEquals(Quote("Life is beautiful", "Author A"), viewModel.quoteModel.value)
    }

}


