package com.devtides.countries.model

import com.movieapp.di.DaggerApiComponent
import com.movieapp.model.Movies
import com.movieapp.model.MoviesApi
import io.reactivex.Single
import javax.inject.Inject

class MoviesServices {

    @Inject
    lateinit var api: MoviesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getMovies(): Single<List<Movies>> {
        return api.getMovies()
    }
}