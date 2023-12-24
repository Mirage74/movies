package com.example.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.pojo.movies.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

}