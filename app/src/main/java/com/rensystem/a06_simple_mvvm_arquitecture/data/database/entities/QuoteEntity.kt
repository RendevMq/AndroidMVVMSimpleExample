package com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rensystem.a06_simple_mvvm_arquitecture.domain.model.Quote


/*
 * La clase `QuoteEntity` representa una entidad que será almacenada en la base de datos mediante Room.
 * Cada instancia de `QuoteEntity` corresponde a una fila en la tabla `quote_table`.
 *
 * La clase contiene campos que representan las columnas de la tabla y está anotada con las anotaciones de Room:
 * - `@Entity` marca la clase como entidad de base de datos.
 * - `@PrimaryKey` define la clave primaria de la tabla.
 * - `@ColumnInfo` personaliza los nombres de las columnas.
 *
 * `QuoteEntity` mapea los datos para que puedan ser persistidos y recuperados de la base de datos.
 *
 * En este caso, la entidad contiene información sobre la cita y su autor.
 */

@Entity(tableName = "quote_table")
data class QuoteEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int=0,

    @ColumnInfo(name = "quote")
    val quote:String,

    @ColumnInfo(name = "author")
    val author:String
)

// Mapper para convertir de `Quote` (dominio) a `QuoteEntity` (base de datos)
fun Quote.toDatabase() = QuoteEntity(quote = quote,author=author)
