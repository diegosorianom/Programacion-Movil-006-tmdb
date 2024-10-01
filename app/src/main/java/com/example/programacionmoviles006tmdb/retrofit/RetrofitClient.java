package com.example.programacionmoviles006tmdb.retrofit;

import com.example.programacionmoviles006tmdb.movies_api.MoviesAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que se encarga de crear una instancia de la interfaz MoviesAPI, que se utiliza para hacer peticiones a la API de The Movie Database.
 * La instancia se crea utilizando Retrofit, que se encarga de convertir las peticiones en llamadas a la API.
 * La instancia se crea de forma lazy, es decir, solo se crea cuando se llama al método getInstance() por primera vez.
 * Luego de eso, se reutiliza la misma instancia para todas las peticiones.
 */
public class RetrofitClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/"; // Primera mitad del enlace, que se combinará con la segunda mitad ubicada en MoviesAPI
    private static MoviesAPI instance;

    // Singleton
    public static MoviesAPI getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(MoviesAPI.class);
        }
        return instance;
    }
}