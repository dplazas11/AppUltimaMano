package com.example.ultimamano.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {

                        Request request = chain.request().newBuilder()
                                .addHeader("apikey", SupabaseConfig.SUPABASE_API_KEY)
                                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_API_KEY)
                                .addHeader("Content-Type", "application/json")
                                .build();

                        return chain.proceed(request);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(SupabaseConfig.SUPABASE_URL + "/rest/v1/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
