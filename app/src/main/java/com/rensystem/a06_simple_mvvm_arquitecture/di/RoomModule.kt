package com.rensystem.a06_simple_mvvm_arquitecture.di

import android.content.Context
import androidx.room.Room
import com.rensystem.a06_simple_mvvm_arquitecture.data.database.QuoteDatabase
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Este objeto `RoomModule` es un módulo de Dagger/Hilt que configura e inyecta las dependencias relacionadas con Room.
 * Se encarga de proporcionar las instancias de la base de datos (`QuoteDatabase`) y el DAO (`QuoteDao`).
 *
 * Utilizando Hilt para la inyección de dependencias, se asegura que la base de datos y el DAO se creen de manera eficiente y compartan la misma instancia en toda la aplicación.
 *
 * - `@Singleton` asegura que solo se cree una instancia de la base de datos en toda la aplicación.
 * - `@Provides` indica cómo se deben crear las dependencias de Room.
 * - `@InstallIn(SingletonComponent::class)` define el alcance de la inyección para la aplicación completa.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "quote_database"

    //PARA PODER INYECTAR LA BASE DE DATOS
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            QuoteDatabase::class.java,
            QUOTE_DATABASE_NAME
        )
            .build()

    //PARA PODER INYECTAR EL DAO
    @Singleton
    @Provides
    fun provideQuoteDao(db:QuoteDatabase) = db.getQuoteDao()
}