package com.movieapp.di

import com.devtides.countries.model.MoviesServices
import com.movieapp.model.MoviesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://api.themoviedb.org"

    @Provides
    fun provideCountriesApi(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    fun provideCountriesService(): MoviesServices {
        return MoviesServices()
    }
}