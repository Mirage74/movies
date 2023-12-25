package com.example.movies.models.favourite_view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.movies.database.MovieDatabase
import com.example.movies.pojo.movies.Movie

private const val TAG = "FavouriteViewModel"
private const val DB_NAME = "movie.db"

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private val moviesDatabase = Room.databaseBuilder(
        application,
        MovieDatabase::class.java,
        DB_NAME
    ).build()

    private val dbDAO = moviesDatabase.moviesDao()

    fun getAllFavoriteMovies(): LiveData<List<Movie>> {
        Log.d(TAG, "getFavoriteMovie")
        return dbDAO.getAllFavoriteMovies()
    }


}