package com.rensystem.a06_simple_mvvm_arquitecture.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.rensystem.a06_simple_mvvm_arquitecture.data.database.dao.QuoteDao
import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.QuoteEntity

/*
 * `QuoteDatabase` es la clase que configura la base de datos utilizando Room.
 * En esta clase se definen las entidades que forman la base de datos (en este caso, `QuoteEntity`).
 * Además, Room maneja la creación, acceso y las migraciones de la base de datos.
 *
 * Room automáticamente genera la implementación de la base de datos y proporciona acceso a los DAOs.
 *
 * Este archivo contiene la configuración y acceso a las operaciones definidas en el DAO.
 *
 * La base de datos se gestiona de forma centralizada en esta clase para facilitar la persistencia y las consultas.
 */

@Database(entities = [QuoteEntity::class] , version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    //Por cada dao vamos a tener que craer una funcion abstracta
    abstract fun getQuoteDao():QuoteDao
}