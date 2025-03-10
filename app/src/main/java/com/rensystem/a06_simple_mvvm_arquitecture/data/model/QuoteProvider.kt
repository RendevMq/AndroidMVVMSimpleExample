package com.rensystem.a06_simple_mvvm_arquitecture.data.model

import javax.inject.Inject

/*
 * Clase que actúa como un **cache local** en memoria para almacenar las citas obtenidas.
 * La primera vez que se hace una solicitud, las citas se obtienen y almacenan en `QuoteProvider`.
 * En futuras solicitudes, las citas se recuperan directamente desde este cache, evitando llamadas redundantes a la red y mejorando el rendimiento.
 */
class QuoteProvider @Inject constructor() {
    //Ya no es necesio dentro de un companion object porque ahora cada que se necesite "QuoteProvider" por la inyeccion me devuelve el objeto en si, y solo tenriamos que acceder al atrivuto
    var quotes: List<QuoteModel> = emptyList()
}

//class QuoteProvider {
//    //companion object para acceder a "quotes" desde la misma clase
//    companion object {
//        var quotes:List<QuoteModel> = emptyList()
//    }
//}
