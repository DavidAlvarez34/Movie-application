package com.example.appmovie.repository

import com.example.appmovie.data.model.MovieList
import com.example.appmovie.data.remote.MovieDataSource

class MovieRepositoryImplements(private val dataSource: MovieDataSource) : MovieRepository{
    override suspend fun getUpcomingMovies(): MovieList = dataSource.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()
}