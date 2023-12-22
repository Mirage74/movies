package com.example.movies.models.detail_view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ModelDetailFactory (val application: Application):
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(application) as T
    }
}