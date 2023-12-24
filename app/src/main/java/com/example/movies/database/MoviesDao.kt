package com.example.movies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.pojo.movies.Movie
import io.reactivex.rxjava3.core.Completable

@Dao
interface MoviesDao {
    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId")
    fun getFavoriteMovie(movieId: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieToFavoriteDatabase(movie: Movie): Completable

    @Query("DELETE FROM favorite_movies WHERE id = :movieId")
    fun removeMovieFromFavoriteDatabase(movieId: Int): Completable

    @Query("DELETE FROM favorite_movies")
    fun removeAllDataFromFavorites()
}