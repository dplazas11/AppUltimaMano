package com.example.ultimamano.network;

import java.util.List;
import com.example.ultimamano.models.usuario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SupabaseApi {

    @Headers({
            "apikey: sb_publishable_MAG00PmJHKo6Zmf3Cr89-Q_A84xF3Ut",
            "Authorization: Bearer sb_publishable_MAG00PmJHKo6Zmf3Cr89-Q_A84xF3Ut",
            "Content-Type: application/json"

    })

    @GET("usuario")
    Call<List<usuario>> loginUser(
            @Query("email") String email
    );

    @POST("usuario")
    Call<ResponseBody> registerUser(@Body usuario user);

}
