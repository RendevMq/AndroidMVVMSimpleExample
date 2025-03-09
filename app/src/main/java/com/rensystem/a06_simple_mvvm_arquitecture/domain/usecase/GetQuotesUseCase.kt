package com.rensystem.a06_simple_mvvm_arquitecture.domain.usecase

import com.rensystem.a06_simple_mvvm_arquitecture.data.QuoteRepository
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel

/*
 * Actualmente, los casos de uso están directamente acoplados a la implementación concreta del repositorio (`QuoteRepository`).
 * Este enfoque puede funcionar a corto plazo, pero rompe el principio de separación de responsabilidades e independencia entre las capas de la arquitectura.
 *
 * Los casos de uso deben centrarse solo en la lógica de negocio, sin conocer los detalles de implementación de la capa de datos
 * (como la interacción con `ApiService` o la implementación concreta del repositorio).
 *
 * Actualmente, los casos de uso están acoplados al repositorio (`QuoteRepository`), lo que significa que al hacer `Ctrl + Click`
 * en el repositorio, el caso de uso revela detalles internos de la capa **Data**, como la forma en que se obtiene la información
 * de la red. Este tipo de acoplamiento no es deseable, ya que va en contra del principio de separación de responsabilidades,
 * haciendo que **Domain** dependa de **Data**, lo cual reduce la flexibilidad y dificulta el mantenimiento.
 *
 * Si la capa **Domain** interactúa directamente con estos detalles, se pierde la flexibilidad para cambiar las implementaciones de la capa **Data** sin afectar a **Domain**.

*
 * **Mejora propuesta**:
 * - **Definir una interfaz en Domain** (`QuoteRepository`), que declare los métodos necesarios sin implementar detalles específicos.
 * - **Implementar esa interfaz en la capa Data** a través de `QuoteRepositoryImpl`, que interactúa con el `ApiService` o cualquier otra fuente de datos concreta.
 *
 * De esta manera, **Domain solo interactúa con abstracciones** y no con implementaciones concretas, lo que mejora la mantenibilidad y escalabilidad del proyecto.
 * La inyección de dependencias también ayudará a desacoplar las clases y a facilitar las pruebas unitarias y la implementación de futuros cambios sin romper la arquitectura existente.
 */

class GetQuotesUseCase {
    private val repository = QuoteRepository()

    suspend operator  fun invoke():List<QuoteModel>?{
        return repository.getAllQuotes()
    }
}

