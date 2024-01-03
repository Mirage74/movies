package com.example.movies


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movies.adapters.MoviesAdapter
import com.example.movies.databinding.ActivityFavouriteMoviesBinding
import com.example.movies.models.favourite_view.FavouriteViewModel
import com.example.movies.models.favourite_view.ModelFavouriteFactory
import com.example.movies.pojo.movies.Movie

class FavouriteMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteMoviesBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var moviesAdapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite_movies)
        viewModel = ViewModelProvider(this, ModelFavouriteFactory(application))[FavouriteViewModel::class.java]
        setupRecyclerView()

        binding.recyclerViewFavourite.layoutManager = GridLayoutManager(this, 2)


        viewModel.FavMoviesList.observe(this) {
            moviesAdapter.submitList(it)
        }



        moviesAdapter.setOnReachEndListener(object : MoviesAdapter.OnReachEndListener {
            override fun onReachEnd() {

            }
        })
    }

    fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        binding.recyclerViewFavourite.adapter = moviesAdapter
        setupClickListener()
    }

    private fun setupClickListener() {
        moviesAdapter.onCardClickListener = {
            val intent = MovieDetailActivity.newIntent(this@FavouriteMoviesActivity, it)
            startActivity(intent)
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FavouriteMoviesActivity::class.java)
        }
    }

}

