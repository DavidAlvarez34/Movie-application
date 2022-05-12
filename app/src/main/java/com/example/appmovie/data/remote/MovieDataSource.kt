package com.example.appmovie.data.remote

import com.example.appmovie.data.model.MovieList
import com.example.appmovie.repository.WebService
import com.example.appmovie.urlappconstants.AppConstantsValue

class MovieDataSource(private val webService:WebService) {
    suspend fun  getUpcomingMovies():MovieList = webService.getUpcomingMovies(AppConstantsValue.API_KEY)
    suspend fun  getTopRatedMovies():MovieList = webService.getTopRatedMovies(AppConstantsValue.API_KEY)
    suspend fun  getPopularMovies():MovieList = webService.getPopularMovies(AppConstantsValue.API_KEY)

}