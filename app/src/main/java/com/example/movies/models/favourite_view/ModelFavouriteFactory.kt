package com.example.movies.models.favourite_view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.models.detail_view.MovieDetailViewModel

class ModelFavouriteFactory (val application: Application):
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouriteViewModel(application) as T
    }
}

