package com.example.programacionmoviles006tmdb.movies_api;

import com.example.programacionmoviles006tmdb.json_mapper.Movie;
import com.example.programacionmoviles006tmdb.json_mapper.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPI {
    @GET("movie/popular?api_key=e82192506167b3cc71d307ad568b73c6")  // Segunda mitad del enlace para obtener todas las películas
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("search/movie?api_key=e82192506167b3cc71d307ad568b73c6")   // Segunda mitad del enlace para mostrar las peliculas con cierta palabra clave en el nombre
    Call<MovieResponse> searchMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("movie/{movie_id}?api_key=e82192506167b3cc71d307ad568b73c6")   // Segunda mitad del enlace para mostrar la pelicula con un ID en especifico
    Call<Movie> getMovieDetails(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
