package com.example.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.api.ApiFactory
import com.example.movies.pojo.trailers.Trailer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MovieDetailViewModel"

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var trailers: MutableLiveData<List<Trailer>> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getTrailers(): LiveData<List<Trailer>> {
        return trailers
    }

    fun loadTrailers(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            compositeDisposable.add(
                ApiFactory.apiService.loadTrailers(id.toString(), TOKEN)
                    //compositeDisposable.add(ApiFactory.apiService.loadTrailers("626", TOKEN)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map {it.trailersList.trailers }
                    .subscribe({
                        trailers.value = it

                    }) {
                        Log.d(TAG, "fun loadTrailers .subscribe exeption: + ${it.toString()}")
                    })
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("page", "onCleared")
        compositeDisposable.dispose()
    }
}