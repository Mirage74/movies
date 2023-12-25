package com.example.movies.models.detail_view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.movies.api.ApiFactory
import com.example.movies.database.MovieDatabase
import com.example.movies.models.main_view.TOKEN
import com.example.movies.pojo.movies.Movie
import com.example.movies.pojo.reviews.Review
import com.example.movies.pojo.trailers.Trailer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"
private const val DB_NAME = "movie.db"

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var trailers: MutableLiveData<List<Trailer>> = MutableLiveData()
    private var reviews: MutableLiveData<List<Review>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    private val moviesDatabase = Room.databaseBuilder(
        application,
        MovieDatabase::class.java,
        DB_NAME
    ).build()

    private val dbDAO = moviesDatabase.moviesDao()



    fun getTrailers(): LiveData<List<Trailer>> {
        //Log.d(TAG, "getTrailers")
        return trailers
    }

    fun getReviews(): LiveData<List<Review>> {
        return reviews
    }

    fun getFavoriteMovie(movieID: Int): LiveData<Movie> {
        Log.d(TAG, "getFavoriteMovie")
        return dbDAO.getFavoriteMovie(movieID)
    }

    fun insertMovieToFav(movie: Movie) {
        compositeDisposable.add(
            dbDAO.insertMovieToFavoriteDatabase(movie)
                .subscribeOn(Schedulers.io())
                .subscribe({

                    Log.d(TAG, "insertMovieToFav: + ${movie.name}")
                }) {
                    Log.d(TAG, "fun insertMovieToFav .subscribe exception: + $it")
                })

    }

    fun removeMovieFromFav(movieId: Int) {
        compositeDisposable.add(
            dbDAO.removeMovieFromFavoriteDatabase(movieId)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "removeMovieFromFav: + $movieId")
                }) {
                    Log.d(TAG, "fun removeMovieFromFav .subscribe exception: + $it")
                })
    }


    fun loadTrailers(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            compositeDisposable.add(
                ApiFactory.apiService.loadTrailers(id.toString(), TOKEN)
                    //compositeDisposable.add(ApiFactory.apiService.loadTrailers("626", TOKEN)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.trailersList.trailers }
                    .subscribe({
                        trailers.value = it

                    }) {
                        Log.d(TAG, "fun loadTrailers .subscribe exception: + $it")
                    })
        }
    }

    fun loadReviews(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            compositeDisposable.add(
                ApiFactory.apiService.loadReviws(TOKEN, "10", "1", id.toString() )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        reviews.value = it.reviewList

                    }) {
                        Log.d(TAG, "fun loadReviews .subscribe exeption: + $it")
                    })
        }
    }

    override fun onCleared() {
        super.onCleared()
        //Log.d(TAG, "onCleared")
        compositeDisposable.dispose()
    }
}