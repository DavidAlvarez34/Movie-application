package com.example.appmovie.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.appmovie.R
import com.example.appmovie.core.Resource
import com.example.appmovie.data.model.Movie
import com.example.appmovie.data.remote.MovieDataSource
import com.example.appmovie.databinding.FragmentMovieBinding
import com.example.appmovie.presentation.MovieViewModel
import com.example.appmovie.presentation.MovieViewModelFactory
import com.example.appmovie.repository.MovieRepositoryImplements
import com.example.appmovie.repository.RetrofitClient
import com.example.appmovie.ui.movies.adapters.MovieAdapter
import com.example.appmovie.ui.movies.adapters.concat.PopularConcatAdapter
import com.example.appmovie.ui.movies.adapters.concat.TopRatedConcatAdapter
import com.example.appmovie.ui.movies.adapters.concat.UpcomingConcatAdapter


class MovieFragment : Fragment(R.layout.fragment_movie),MovieAdapter.OnMovieClickListener {
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel>{
        MovieViewModelFactory(MovieRepositoryImplements(
            MovieDataSource(RetrofitClient.webService)
        ))
    }
    private lateinit var concatAdapter: ConcatAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        concatAdapter = ConcatAdapter()
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result->
            when (result){
                is  Resource.Loading -> {
                    //Log.d( "LiveData","Loading....")
                    binding.progressBar.visibility = View.VISIBLE
                }
                is  Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0,UpcomingConcatAdapter(MovieAdapter(result.data.first.results,this@MovieFragment)))
                        addAdapter(1,TopRatedConcatAdapter(MovieAdapter(result.data.second.results,this@MovieFragment)))
                        addAdapter(2,PopularConcatAdapter(MovieAdapter(result.data.third.results,this@MovieFragment)))
                    }
                    binding.rvMovies.adapter = concatAdapter
                    // Log.d( "LiveData","Upcoming ${result.data.first} ")
                   // Log.d("LiveData","Popular ${result.data.second}")
                   // Log.d("LiveData","Top ${result.data.third}")
                }
                is  Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    //Log.d( "Error","${result.exception}")
                }
            }
        })


    }

    override fun onMovieClick(movie: Movie) {
       //Log.d("Error","onMovieClick: ${movie}")
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movie.poster_path,movie.backdrop_path,
            movie.vote_average.toFloat(), movie.vote_count,movie.overview,movie.title,movie.original_language, movie.release_date)
        findNavController().navigate(action)
    }
}

