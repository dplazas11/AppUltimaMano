package com.example.ultimamano.network;

import java.util.List;

import com.example.ultimamano.models.apuesta;
import com.example.ultimamano.models.casino;
import com.example.ultimamano.models.usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SupabaseApi {


    @GET("usuario")
    Call<List<usuario>> loginUser(
            @Query("select") String select,
            @Query("email") String emailEq
    );

    @POST("usuario")
    Call<ResponseBody> registerUser(@Body usuario user);

    @POST("apuesta")
    Call<ResponseBody> registrarApuesta(
            @Body apuesta apuesta

    );

    @GET("apuesta?select=*")
    Call<List<apuesta>> getApuestas();

    @POST("casino")
    Call<ResponseBody> registrarCasino(
            @Body casino casino
    );

    @GET("usuario")
    Call<List<usuario>> getUsuarioByEmail(
            @Query("email") String email
    );

    @GET("casino?select=direccion,usuario(nombre),ciudad(nombre),tipo_casino(nombre)")
    Call<List<casino>> getDirectorioCasinos();

}
