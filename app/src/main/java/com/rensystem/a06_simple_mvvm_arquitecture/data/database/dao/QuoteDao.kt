package com.rensystem.a06_simple_mvvm_arquitecture.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.QuoteEntity

/*
 * El Data Access Object (DAO) proporciona una abstracción sobre la base de datos para interactuar con la tabla de citas.
 * Los DAOs definen las operaciones que se pueden realizar en las entidades de la base de datos.
 * En este caso, `QuoteDao` gestiona las operaciones CRUD relacionadas con las citas, tales como obtener, insertar y eliminar citas.
 *
 * Room es la librería que se utiliza para la persistencia local de datos. Esta interfaz expone métodos como:
 * - Obtener todas las citas de la base de datos.
 * - Insertar nuevas citas o actualizar las existentes.
 * - Eliminar todas las citas.
 *
 * El DAO es utilizado por el repositorio para obtener datos persistidos o actualizar la base de datos.
 */

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote_table ORDER BY author DESC")
    suspend fun getAllQuotes():List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes:List<QuoteEntity>)

    @Query("DELETE FROM quote_table")
    suspend fun deleteAllQuotes()
}