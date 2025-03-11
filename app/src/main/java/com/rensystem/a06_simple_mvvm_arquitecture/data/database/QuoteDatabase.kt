package com.rensystem.a06_simple_mvvm_arquitecture.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.rensystem.a06_simple_mvvm_arquitecture.data.database.dao.QuoteDao
import com.rensystem.a06_simple_mvvm_arquitecture.data.database.entities.QuoteEntity

@Database(entities = [QuoteEntity::class] , version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    //Por cada dao vamos a tener que craer una funcion abstracta
    abstract fun getQuoteDao():QuoteDao
}