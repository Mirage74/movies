package com.example.movies

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapters.MoviesAdapter
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.models.main_view.MainViewModel
import com.example.movies.models.main_view.ModelFactory
import com.example.movies.pojo.movies.Movie


//private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val moviesAdapter = MoviesAdapter()
    //private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val intent = FavouriteMoviesActivity.newIntent(this)
        startActivity(intent)
        //viewModel = ViewModelProvider(this)[MainViewModel(application, 1)::class.java]
        viewModel = ViewModelProvider(this, ModelFactory(application, 1))[MainViewModel::class.java]
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewMovies.adapter = moviesAdapter


        viewModel.getMovies().observe(this) {
            moviesAdapter.setMovies(it)
        }

        moviesAdapter.setOnCardClickListener(object : MoviesAdapter.OnCardClickListener {
            override fun onCardClick(movie: Movie) {
                val intent = MovieDetailActivity.newIntent(this@MainActivity, movie)
                startActivity(intent)

            }
        })

        moviesAdapter.setOnReachEndListener(object : MoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        })

    }


}

