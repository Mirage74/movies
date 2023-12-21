package com.example.movies

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.pojo.Movie


//private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val moviesAdapter = MoviesAdapter()
    //private var page = 1


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //viewModel = ViewModelProvider(this)[MainViewModel(application)::class.java]
        viewModel = ViewModelProvider(this, ModelFactory(application, 1))[MainViewModel::class.java]
        binding.recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewMovies.adapter = moviesAdapter


        viewModel.movies.observe(this) {
            //Log.d(TAG,"viewModel.getMovies()  ${it.size}")
            //Log.d(TAG,"viewModel. first id:   ${it[0].id}")
            moviesAdapter.submitList(it)
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

