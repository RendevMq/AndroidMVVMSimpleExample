package com.rensystem.a06_simple_mvvm_arquitecture.data.model

/*
 * Clase que actúa como un **cache local** en memoria para almacenar las citas obtenidas.
 * La primera vez que se hace una solicitud, las citas se obtienen y almacenan en `QuoteProvider`.
 * En futuras solicitudes, las citas se recuperan directamente desde este cache, evitando llamadas redundantes a la red y mejorando el rendimiento.
 */
class QuoteProvider {
    companion object {
        var quotes:List<QuoteModel> = emptyList()
    }
}
