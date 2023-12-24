package com.example.movies

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movies.adapters.ReviewsAdapter
import com.example.movies.adapters.TrailersAdapter
import com.example.movies.databinding.ActivityMovieDetailBinding
import com.example.movies.models.detail_view.ModelDetailFactory
import com.example.movies.models.detail_view.MovieDetailViewModel
import com.example.movies.pojo.movies.Movie
import com.example.movies.pojo.trailers.Trailer
import io.reactivex.rxjava3.disposables.CompositeDisposable

private const val EXTRA_MOVIE = "movie"

//private const val TAG = "MovieDetailActivity"
private val compositeDisposable = CompositeDisposable()

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel
    private val trailersAdapter = TrailersAdapter()
    private val reviewsAdapter = ReviewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        viewModel = ViewModelProvider(
            this,
            ModelDetailFactory(application)
        )[MovieDetailViewModel::class.java]
        binding.recyclerViewTrailers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTrailers.adapter = trailersAdapter
        binding.recyclerViewReviews.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewReviews.adapter = reviewsAdapter

        val movie = intent.getSerializableExtra(EXTRA_MOVIE, Movie::class.java)

        val drawableOn = ContextCompat.getDrawable(this, android.R.drawable.star_big_on)
        val drawableOff = ContextCompat.getDrawable(this, android.R.drawable.star_big_off)

        with(binding) {
            if (movie != null) {
                Glide.with(this@MovieDetailActivity)
                    .load(movie.poster.url)
                    .into(imageViewPoster)
                textViewTitle.text = movie.name
                textViewYear.text = movie.year.toString()
                textViewDescription.text = movie.description

                viewModel.loadTrailers(movie.id)
                viewModel.getTrailers().observe(this@MovieDetailActivity) {
                    trailersAdapter.setTrailers(it)
                    //Log.d(TAG, "trailers: + ${it.toString()}")
                }

                viewModel.loadReviews(movie.id)
                viewModel.getReviews().observe(this@MovieDetailActivity) {
                    reviewsAdapter.setReviews(it)
                }
                viewModel.getFavoriteMovie(movie.id).observe(this@MovieDetailActivity) {
                    if (it == null) {
                        imageViewStar.setImageDrawable(drawableOff)
                        imageViewStar.setOnClickListener {
                        Log.d("MovieDetailViewModel", "viewModel it == null")
                        viewModel.insertMovieToFav(movie)

                        }
                    } else {
                        imageViewStar.setImageDrawable(drawableOn)
                        imageViewStar.setOnClickListener {
                        Log.d("MovieDetailViewModel", "viewModel it !- null")
                        viewModel.removeMovieFromFav(movie.id)

                        }
                    }
                }

                trailersAdapter.setOnCardClickListener(object :
                    TrailersAdapter.OnCardClickListener {
                    override fun onCardClick(trailer: Trailer) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(trailer.url)
                        startActivity(intent)
                        //Log.d(TAG, "trailer's show was clicked")

                    }
                })
            }
        }
    }

    companion object {
        fun newIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}