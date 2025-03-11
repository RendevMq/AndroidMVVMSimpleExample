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