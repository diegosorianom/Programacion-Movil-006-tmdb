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
    // Constante para el log, TAG es el nombre de la clase
    private static final String TAG = "MainActivity";

    // API KEY para la API de TMDB
    private static final String API_KEY = "APIKEY";

    // Referencia a la interfaz MoviesAPI, lo llamaremos API
    private MoviesAPI api;

    // Referencia al cuadro de texto en el que mostraremos los resultados
    private TextView textViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Pantalla en la que mostraremos nuestros resultados

        api = RetrofitClient.getInstance();

        textViewResults = findViewById(R.id.textViewResults);

        Button buttonPopularMovies = findViewById(R.id.boton1);
        Button buttonSearchMovies = findViewById(R.id.boton2);
        Button buttonMovieDetails = findViewById(R.id.boton3);

        // Funcion para cargar las peliculas populares
        buttonPopularMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMovies();
            }
        });

        // Funcion para buscar peliculas
        buttonSearchMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovie("Avatar");
            }
        });

        // Funcion para mostrar los detalles de una pelicula
        buttonMovieDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMovieDetails(505);
            }
        });
    }


    /*
    Funcón para cargar todas las películas
    Envía una llamada HTTP GET con Retrofit para obtener todas las películas populares con su
    respectivo título y su ID. El resultado se almacena en la variable response. Si la respuesta
    es exitosa se muestra un mensaje con la lista de títulos de peliculas y se guardan en un
    StringBuilder para mostrar en el TextView. Si la respuesta no es exitosa se muestra un mensaje
    con el código de error. Si la llamada falla se muestra un mensaje con el error.
     */
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

    /*
    Función para mostrar todas las películas que contienen en su titulo una palabra clave
    Envía una llamada HTTP GET con Retrofit para obtener todas las películas que contienen
    en su título la palabra clave pasada como parámetro. El resultado se almacena en la
    variable response. Si la respuesta es exitosa se muestra un mensaje con la lista de
    títulos de peliculas y se guardan en un StringBuilder para mostrar en el TextView.
    Si la respuesta no es exitosa se muestra un mensaje con el código de error. Si la
    llamada falla se muestra un mensaje con el error.
     */
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

    /*
    Comentario de la función para mostrar los detalles de la pelicula con un id concreto.
    La función hace una llamada HTTP GET con Retrofit para obtener los detalles de una
    película con un id concreto. El resultado se almacena en la variable response.
    Si la respuesta es exitosa se muestra un mensaje con la descripción de la película
    y se guardan en un String para mostrar en el TextView. Si la respuesta no es
    exitosa se muestra un mensaje con el código de error. Si la llamada falla se
    muestra un mensaje con el error.
     */
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