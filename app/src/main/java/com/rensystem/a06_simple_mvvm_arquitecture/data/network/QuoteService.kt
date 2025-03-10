package com.rensystem.a06_simple_mvvm_arquitecture.data.network

import android.util.Log
import com.rensystem.a06_simple_mvvm_arquitecture.core.RetrofitHelper
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteModel
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * Clase encargada de gestionar la obtención de las citas desde una fuente remota (API).
 *
 * Esta clase centraliza toda la lógica de acceso a las citas a través de la red, utilizando Retrofit u otros servicios de red en el futuro.
 * El repositorio y otras capas del proyecto no necesitan conocer los detalles de implementación de la red, ya que se abstrae completamente en esta clase (no necesitan saber cómo se obtienen las citas).
 *
 * En caso de que se necesite cambiar la implementación de Retrofit o el cliente de red, esta clase es el único lugar donde se realizarían los cambios,
 * evitando modificar el resto del proyecto, lo que mejora la mantenibilidad y flexibilidad del proyecto.
 */
class QuoteService @Inject constructor(
    private val quoteApiClient:QuoteApiClient
) {

    suspend fun getQuotes(): List<QuoteModel> {

        return withContext(Dispatchers.IO) {
            try {
                //Realizamos la llamada a la API
                val response = quoteApiClient.getAllQuotes()
                //Verificamos si la resuesta fue exitosa
                if(response.isSuccessful && response.body()!=null){
                    Log.i("Renato" , "Success quotes: ${response.code()}")
                    response.body() ?: emptyList()
                } else{
                    Log.i("Renato" , "Error fetching quotes: ${response.code()} && Response body: ${response.body()}")
                    throw Exception("Error fetching quotes: ${response.code()}")
                }
            } catch (e:Exception){
                Log.i("Renato" , "Network error: ${e.message}")
                throw Exception("Network error: ${e.message}")
            }

        }
    }
}

//class QuoteService {
//    private val retrofit = RetrofitHelper.getRetrofit()
//    suspend fun getQuotes(): List<QuoteModel> {
//        return withContext(Dispatchers.IO) {
//            val response = retrofit.create(QuoteApiClient::class.java).getAllQuotes()
//            response.body() ?: emptyList()
//        }
//    }
//}