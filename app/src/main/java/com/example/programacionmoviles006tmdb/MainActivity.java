package com.example.programacionmoviles006tmdb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programacionmoviles006tmdb.json_mapper.Movie;
import com.example.programacionmoviles006tmdb.json_mapper.MovieResponse;
import com.example.programacionmoviles006tmdb.movies_api.MoviesAPI;
import com.example.programacionmoviles006tmdb.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String API_KEY = "APIKEY";
    private MoviesAPI api;
    private TextView textViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = RetrofitClient.getInstance();

        textViewResults = findViewById(R.id.textViewResults);

        Button buttonPopularMovies = findViewById(R.id.boton1);
        Button buttonSearchMovies = findViewById(R.id.boton2);
        Button buttonMovieDetails = findViewById(R.id.boton3);

        buttonPopularMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMovies();
            }
        });

        buttonSearchMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovie("Avatar");
            }
        });

        buttonMovieDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMovieDetails(505);
            }
        });
    }

    private void loadMovies() {
        Call<MovieResponse> call = api.getPopularMovies(API_KEY, "es-Es", 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StringBuilder moviesList = new StringBuilder();
                    for (Movie movie : response.body().getResults()) {
                        moviesList.append(movie.getTitle()).append("\n");
                        Log.d(TAG, "Pelicula: " + movie.getTitle());
                    }
                    textViewResults.setText(moviesList.toString());
                    Toast.makeText(MainActivity.this, "Peliculas populares cargadas", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Fallo en la llamada: " + t.getMessage());
            }
        });
    }

    private void searchMovie(String query) {
        Call<MovieResponse> call = api.searchMovie(API_KEY, "es-ES", query, 1);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StringBuilder moviesList = new StringBuilder(); // Para almacenar títulos
                    for (Movie movie : response.body().getResults()) {
                        moviesList.append(movie.getTitle()).append("\n"); // Agregar títulos a la lista
                        Log.d(TAG, "Resultado de búsqueda: " + movie.getTitle());
                    }
                    textViewResults.setText(moviesList.toString()); // Mostrar títulos en el TextView
                    Toast.makeText(MainActivity.this, "Búsqueda completada", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, "Fallo en la llamada: " + t.getMessage());
            }
        });
    }

    private void getMovieDetails(int movieId) {
        Call<Movie> call = api.getMovieDetails(movieId, API_KEY, "es-ES");
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();
                    Log.d(TAG, "Detalles de la película: " + movie.getTitle() + " - " + movie.getOverview());

                    // Mostrar detalles en el TextView
                    String details = "Título: " + movie.getTitle() + "\n" +
                            "Descripción: " + movie.getOverview();
                    textViewResults.setText(details);
                    Toast.makeText(MainActivity.this, "Detalles de película cargados", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "Fallo en la llamada: " + t.getMessage());
            }
        });
    }
}