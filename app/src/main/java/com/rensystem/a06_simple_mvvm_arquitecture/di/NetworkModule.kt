package com.rensystem.a06_simple_mvvm_arquitecture.di

import com.rensystem.a06_simple_mvvm_arquitecture.data.QuoteRepositoryImpl
import com.rensystem.a06_simple_mvvm_arquitecture.data.mock.MockQuoteService
import com.rensystem.a06_simple_mvvm_arquitecture.data.model.QuoteProvider
import com.rensystem.a06_simple_mvvm_arquitecture.data.network.QuoteApiClient
import com.rensystem.a06_simple_mvvm_arquitecture.data.network.QuoteService
import com.rensystem.a06_simple_mvvm_arquitecture.domain.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
*  * Este módulo se encarga de proveer las dependencias relacionadas con la red, como Retrofit y sus interfaces.
 * Es útil cuando las dependencias son complejas de proporcionar directamente, como aquellas que involucran
 * librerías externas o clases que contienen interfaces.
 *
 * Además, al definir un módulo, podemos especificar el **alcance** de las dependencias. Esto controla el ciclo de vida
 * de las instancias creadas:*/
@Module
@InstallIn(SingletonComponent::class) //se puede definir el alcance
object NetworkModule {

    //proveo Retrofit
    @Singleton //mantenga una unica instancia
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cleanarch-retrofit-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //proveemos la interface QUOTEAPICLIENT
    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): QuoteApiClient {
        return retrofit.create(QuoteApiClient::class.java)
    }

    //==(↓)(↓)(↓) Nuevos providers, luego se separar el QuoteRepository en -> QuoteRepository y QuoteRepositoryImpl (↓)(↓)(↓)===//

    @Singleton
    @Provides
    fun provideRepository(
        api: QuoteService,
        mockApi: MockQuoteService,
        quoteProvider: QuoteProvider

    ): QuoteRepository {
        return QuoteRepositoryImpl(api,mockApi,quoteProvider)
    }

}