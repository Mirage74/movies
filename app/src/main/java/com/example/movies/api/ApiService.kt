package com.example.movies.api

import com.example.movies.pojo.MovieResponse
import com.example.movies.pojo.trailers.TrailerResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //@GET("movie?token=6S4AVWD-MQ8M2RM-J4PDSQ5-YH8G3KW&id=799-804")
    //@GET("movie?token=6S4AVWD-MQ8M2RM-J4PDSQ5-YH8G3KW&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=5")
    //@GET("movie?token=6S4AVWD-MQ8M2RM-J4PDSQ5-YH8G3KW&rating.kp=3-7&sortField=votes.kp&sortType=-1&limit=15")
    //@GET("movie?token=QCFGZ8S-TR8MAK7-HHWZDE0-7ST7TRW&rating.kp=1-10&sortField=id&sortType=-1&limit=30")
    //@GET("movie?token=6S4AVWD-MQ8M2RM-J4PDSQ5-YH8G3KW&sortField=votes.kp&sortType=-1&limit=15")
    //@GET("movie?page=1&limit=20&selectFields=id&selectFields=name&selectFields=alternativeName&selectFields=enName&selectFields=type&selectFields=year&selectFields=description&selectFields=shortDescription&selectFields=movieLength&selectFields=isSeries&selectFields=ticketsOnSale&selectFields=totalSeriesLength&selectFields=seriesLength&selectFields=ratingMpaa&selectFields=ageRating&selectFields=top10&selectFields=top250&selectFields=typeNumber&selectFields=status&selectFields=names&selectFields=logo&selectFields=poster&selectFields=backdrop&selectFields=rating&selectFields=votes&selectFields=genres&selectFields=countries&selectFields=releaseYears&id=326&id=327&id=328&id=400&id=424&id=437&id=533&id=8743&id=337&id=1143242&id=877&id=282&id=828&token=QCFGZ8S-TR8MAK7-HHWZDE0-7ST7TRW")
    @GET("movie?rating.kp=1-10&sortField=id&sortType=-1&limit=15&id=300-400")
    fun loadMovies(
        @Query(QUERY_PARAM_TOKEN) token: String,
        @Query(QUERY_PARAM_PAGE) page: String,
        @Query(QUERY_PARAM_NOT_NULL_FIELD) notNullField: String
    ): Single<MovieResponse>


    @GET("movie/{id}?")
    //@GET("movie?token=6S4AVWD-MQ8M2RM-J4PDSQ5-YH8G3KW&sortField=votes.kp&sortType=-1&limit=15")
    fun loadTrailers(
        @Path("id") id: String,
        @Query(QUERY_PARAM_TOKEN) token: String
    ): Single<TrailerResponse>


    companion object {
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_TOKEN = "token"
        private const val QUERY_PARAM_NOT_NULL_FIELD = "notNullFields"

    }

}