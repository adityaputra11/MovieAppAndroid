package com.movieapp.model

import io.reactivex.Single
import retrofit2.http.GET

interface MoviesApi {

    @GET("3/trending/all/day?api_key=ba6c83a57eaaae900b700fad3a945735")
    fun getMovies(): Single<List<Movies>>
}