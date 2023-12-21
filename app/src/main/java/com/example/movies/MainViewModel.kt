package com.example.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movies.api.ApiFactory
import com.example.movies.pojo.Movie
import com.example.movies.pojo.MovieResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch




private const val TAG = "My Exception Handler"
private const val NOT_NULL_FIELD = "videos.trailers.url"

//const val TOKEN = "6S4AVWD-MQ8M2RM-J4PDSQ5-YH8G3KW"

const val TOKEN = "QCFGZ8S-TR8MAK7-HHWZDE0-7ST7TRW"


class MainViewModel(application: Application, page: Int) : AndroidViewModel(application) {
//    constructor(application: Application, page: Int) : this(application) {
//        Log.d("page", "constructor, page: ${page}")
//        this.page = page
//    }
    var movies: MutableLiveData<List<Movie>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private var page = 1

    private val compositeDisposable = CompositeDisposable()

    init {
        loadMovies()
        this.page = page
        Log.d("page", "init block")
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun loadMovies() {
        val loading = isLoading.value
        if ((loading != null) && loading) {
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("page", "compositeDisposable.add page: ${page}")
            //Log.d("page", "this hash: ${this.hashCode()}")
            compositeDisposable.add(ApiFactory.apiService.loadMovies(TOKEN, page.toString(), NOT_NULL_FIELD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    GlobalScope.launch {
                        isLoading.postValue(true)
                    }
                }
                .doAfterTerminate { isLoading.value = false }
                .subscribe({
                    isLoading.value = true
                    if (movies.value != null) {
                        val loadedMovies: MutableList<Movie> = movies.value as MutableList<Movie>
                        loadedMovies.addAll(it.movies)
                        movies.value = loadedMovies
                        //Log.d("page", "getQquery page: ${page}")
                        //Log.d("page", "getQquery 0 id: ${it.movies[0].id}")

                    } else {
                        movies.value = it.movies
                        //refreshList()

                    }
                    //Log.d("page", "page+++++++++++++: ${page}")
                    page++

                }) {
                    Log.d(
                        TAG,
                        "fun loadMovies() .subscribe exeption: + ${it.toString()}"
                    )
                })
        }
    }

//    override fun hashCode(): Int {
//        return this.movies.hashCode()
//    }

    override fun onCleared() {
        super.onCleared()
        Log.d("page", "onCleared")
        compositeDisposable.dispose()
    }
}