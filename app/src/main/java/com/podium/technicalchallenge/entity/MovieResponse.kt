package com.podium.technicalchallenge.entity

data class MovieResponse(
    val data: Movies
)

data class Movies(
    val movies: List<MovieEntity>
)

data class MovieEntity(
    val title: String,
    val genres: List<String>,
    val popularity: Float,
    val releaseDate: String,
    val posterPath: String,
    val overview: String,
    val cast: List<CastMember>,
    val director: Director
)

data class CastMember(
    val name: String,
    val profilePath: String
)

data class Director(
    val name: String
)

