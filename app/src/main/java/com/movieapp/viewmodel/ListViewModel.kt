package com.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.countries.model.MoviesServices
import com.movieapp.di.DaggerApiComponent
import com.movieapp.model.Movies
import com.movieapp.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel: ViewModel() {

    @Inject
    lateinit var moviesServices: MoviesServices

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    val movies = MutableLiveData<List<Results>>()
    val movieLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        disposable.add(
            moviesServices.getMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Movies>>() {
                    override fun onSuccess(value: List<Movies>?) {
                        movies.value = value[1]
                        movieLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        movieLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}

