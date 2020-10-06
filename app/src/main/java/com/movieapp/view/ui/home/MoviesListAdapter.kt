package com.movieapp.view.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieapp.R
import com.movieapp.model.Movies
import com.movieapp.util.getProgressDrawable
import com.movieapp.util.loadImage
import kotlinx.android.synthetic.main.item_movies.view.*

class MoviesListAdapter(var movies: ArrayList<Movies>): RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    fun updateMovies(newMovies: List<Movies>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MoviesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
    )

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    class MoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageView = view.imageView
        private val countryName = view.name
        private val countryCapital = view.capital
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(movies: Movies) {
            countryName.text = movies.countryName
            countryCapital.text = movies.capital
            imageView.loadImage(movies.flag, progressDrawable)
        }
    }
}