package com.example.appmovie.repository

import com.example.appmovie.data.model.MovieList

interface MovieRepository {
    suspend fun  getUpcomingMovies(): MovieList
    suspend fun  getTopRatedMovies(): MovieList
    suspend fun  getPopularMovies(): MovieList
}