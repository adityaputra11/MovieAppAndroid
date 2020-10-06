package com.movieapp.di

import com.devtides.countries.model.MoviesServices
import com.movieapp.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: MoviesServices)

    fun inject(viewModel: ListViewModel)
}