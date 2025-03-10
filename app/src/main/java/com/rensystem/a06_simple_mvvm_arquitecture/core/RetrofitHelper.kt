package com.rensystem.a06_simple_mvvm_arquitecture.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Por que un objeto?
object RetrofitHelper {

    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://cleanarch-retrofit-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}