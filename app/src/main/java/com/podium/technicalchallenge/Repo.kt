package com.podium.technicalchallenge

import com.google.gson.Gson
import com.podium.technicalchallenge.entity.Genres
import com.podium.technicalchallenge.entity.GenresResponse
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.entity.MovieResponse
import com.podium.technicalchallenge.network.ApiClient
import com.podium.technicalchallenge.network.queries.Queries
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import org.json.JSONObject

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class Repo {

    suspend fun searchMovies(query: String, querySort: Queries.QuerySort): Result<List<MovieEntity>?> {
        val paramObject = JSONObject()
        paramObject.put(
            "query", Queries.searchMovies(query, querySort)
        )

        val response = ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java).postGetMovies(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, MovieResponse::class.java)
        return if (data != null) {
            Result.Success(data.data.movies)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getTop5PopularMovies(): Result<List<MovieEntity>?> {
        val paramObject = JSONObject()
        paramObject.put(
            "query", Queries.getTop5PopularMoviesQuery()
        )
        val response = ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java).postGetMovies(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, MovieResponse::class.java)
        return if (data != null) {
            Result.Success(data.data.movies)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getMovies(): Result<List<MovieEntity>?> {
        val paramObject = JSONObject()
        paramObject.put(
            "query", Queries.getMoviesQuery()
        )

        val response = ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java).postGetMovies(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, MovieResponse::class.java)
        return if (data != null) {
            Result.Success(data.data.movies)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    suspend fun getGenres(): Result<List<String>?> {
        val paramObject = JSONObject()
        paramObject.put(
            "query", Queries.getGenresQuery()
        )

        val response = ApiClient.getInstance().provideRetrofitClient().create(GraphQLService::class.java).postGetMovies(paramObject.toString())
        val jsonBody = response.body()
        val data = Gson().fromJson(jsonBody, GenresResponse::class.java)
        return if (data != null) {
            Result.Success(data.data.genres)
        } else {
            Result.Error(java.lang.Exception())
        }
    }

    companion object {
        private var INSTANCE: Repo? = null
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}