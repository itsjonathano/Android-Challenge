package com.podium.technicalchallenge.network.queries

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
    fun getGenres() = """
      query GetMoviesQuery {
      genres
    }
        
        
        
    """

    fun getMoviesByAGenre(genre: String) = """
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