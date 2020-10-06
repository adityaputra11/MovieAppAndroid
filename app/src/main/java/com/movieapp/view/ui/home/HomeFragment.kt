package com.movieapp.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.movieapp.R
import com.movieapp.util.getProgressDrawable
import com.movieapp.util.loadImage
import com.movieapp.viewmodel.ListViewModel
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var viewModel: ListViewModel
    private val moviesListAdapter = MoviesListAdapter(arrayListOf())

    var sampleImages = intArrayOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_background,
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val carousel: CarouselView = root.findViewById(R.id.carouselView)
        val textView: TextView = root.findViewById(R.id.text_home)
        val movList: RecyclerView = root.findViewById(R.id.movieList)
        val refreshList: SwipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(movList)

        movList.apply {
            layoutManager =    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = moviesListAdapter
        }

        refreshList.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel(carousel)
        return root
    }

    fun observeViewModel(carousel: CarouselView) {
        viewModel.movies.observe(viewLifecycleOwner, Observer {countries ->
            countries?.let {
                movieList.visibility = View.VISIBLE
                moviesListAdapter.updateMovies(it)
                carousel.setImageListener { position, imageView ->
                    imageView.loadImage(
                        it[position],
                        getProgressDrawable(requireActivity())
                    )
                }
                carousel.pageCount = it.size

            }
        })

        viewModel.movieLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let { list_error.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    list_error.visibility = View.GONE
                    movieList.visibility = View.GONE
                }
            }
        })
    }

}