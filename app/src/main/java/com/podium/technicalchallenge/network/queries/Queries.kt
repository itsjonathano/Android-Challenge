package com.podium.technicalchallenge.network.queries

import android.util.Log

object Queries {
    fun getMoviesQuery() =
        """
    query GetMoviesQuery {
  movies {
    title
    genres
    popularity
    releaseDate
    posterPath
    overview
    cast { name, profilePath }
    director { name }
  }
}
"""

    // Get All Genres available in the API
    fun getGenresQuery() = """
      query GetMoviesQuery {
      genres
    }
    """

    enum class QuerySort {
        Alphabetical,
        ReverseAlphabetical,
        MostPopular,
        LeastPopular,
        LatestRelease,
        OldestRelease

    }

    fun searchMovies(search: String, querySort: QuerySort): String {

        // Sort based on constant from radio buttons
        var sort = when (querySort) {
            QuerySort.Alphabetical ->  "orderBy:\"title\" sort:ASC"
            QuerySort.ReverseAlphabetical -> "orderBy:\"title\" sort:DESC"
            QuerySort.MostPopular -> "orderBy:\"popularity\" sort:DESC"
            QuerySort.LeastPopular -> "orderBy:\"popularity\" sort:ASC"
            QuerySort.LatestRelease -> "orderBy:\"releaseDate\" sort:DESC"
            QuerySort.OldestRelease -> "orderBy:\"releaseDate\" sort:ASC"
        }

        var query = """
            query GetMoviesQuery {
              movies(search:"${search}" ${sort}){
                title
                genres
                popularity
                releaseDate
                posterPath
                overview
                cast { name, profilePath }
                director { name }
              }
            }
        """
        return query
    }

    fun getMoviesByGenre(genre: String) = {
        """
        query GetMoviesQuery {
          movies (limit:5, orderBy:"popularity", sort: DESC) {
            title
            genres
            popularity
            releaseDate
            posterPath
            overview
            cast { name, profilePath }
            director { name }
          }
        }
    """
    }

    fun getTop5PopularMoviesQuery()  =
        """
     query GetMoviesQuery {
      movies (limit:5, orderBy:"popularity", sort: DESC) {
        title
        genres
        popularity
        releaseDate
        posterPath
        overview
        cast { name, profilePath }
        director { name }
      }
    }  
"""

}