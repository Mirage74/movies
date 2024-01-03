package com.example.movies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapters.MoviesAdapter
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.models.main_view.MainViewModel
import com.example.movies.models.main_view.ModelFactory
import com.example.movies.pojo.movies.Movie


//private const val TAG = "MainActivity"

//class MainActivity : ComponentActivity() {
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    //private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val intent = FavouriteMoviesActivity.newIntent(this)
        startActivity(intent)
        viewModel = ViewModelProvider(this, ModelFactory(application, 1))[MainViewModel::class.java]

        setupRecyclerView()

        binding.recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)




        viewModel.moviesList.observe(this) {
            moviesAdapter.submitList(it)
        }

        moviesAdapter.setOnReachEndListener(object : MoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        })

    }

    fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.recyclerViewMovies.adapter = moviesAdapter
        setupClickListener()
    }

    private fun setupClickListener() {
        moviesAdapter.onCardClickListener = {
            val intent = MovieDetailActivity.newIntent(this@MainActivity, it)
            startActivity(intent)
        }
    }

}

