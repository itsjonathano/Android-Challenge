package com.podium.technicalchallenge

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class DemoViewModel : ViewModel() {
    val TAG = "DemoViewModel"

    val liveSelectedMovie = MutableLiveData<MovieEntity>()

    val liveTop5Movies = MutableLiveData<List<MovieEntity>?>()

    val liveAllMovies = MutableLiveData<List<MovieEntity>?>()

    val liveGenres = MutableLiveData<List<String>?>()

    fun getTop5PopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Repo.getInstance().getTop5PopularMovies()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<MovieEntity>?> -> {
                    Log.d(TAG, "movies= " + result.data)
                    liveTop5Movies.postValue(result.data)
                }
                else -> {
                    Log.e(TAG, "movies= " + result)
                }
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Repo.getInstance().getGenres()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<String>?> -> {
                    Log.d(TAG, "genres= " + result.data)
                    liveGenres.postValue(result.data)
                }
                else -> {
                    Log.e(TAG, "genres= " + result)
                }
            }
        }
    }

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                Repo.getInstance().getMovies()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<MovieEntity>?> -> {
                    Log.d(TAG, "movies= " + result.data)
                    liveAllMovies.postValue(result.data)
                }
                else -> {
                    Log.e(TAG, "movies= " + result)
                }
            }
        }
    }
}
