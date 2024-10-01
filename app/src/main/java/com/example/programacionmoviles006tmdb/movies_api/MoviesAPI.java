package com.example.programacionmoviles006tmdb.movies_api;

import com.example.programacionmoviles006tmdb.json_mapper.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesAPI {
    @GET("movie/popular?api_key=e82192506167b3cc71d307ad568b73c6")
    Call<MovieResponse> getPopularMovies();
}
