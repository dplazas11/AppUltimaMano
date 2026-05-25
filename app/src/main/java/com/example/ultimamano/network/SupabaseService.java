package com.example.ultimamano.network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class SupabaseService {

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(SupabaseConfig.SUPABASE_URL + "/rest/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
