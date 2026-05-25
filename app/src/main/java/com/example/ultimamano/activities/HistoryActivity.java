package com.example.ultimamano.activities;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;
import com.example.ultimamano.models.apuesta;
import com.example.ultimamano.network.RetrofitClient;
import com.example.ultimamano.network.SupabaseApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    ListView listViewHistorial;

    TextView tvTotalApostado;
    TextView tvTotalResultado;

    SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listViewHistorial = findViewById(R.id.listViewHistorial);

        tvTotalApostado = findViewById(R.id.tvTotalApostado);
        tvTotalResultado = findViewById(R.id.tvTotalResultado);

        api = RetrofitClient
                .getClient()
                .create(SupabaseApi.class);

        cargarHistorial();
    }

    private void cargarHistorial() {

        api.getApuestas().enqueue(new Callback<List<apuesta>>() {

            @Override
            public void onResponse(
                    Call<List<apuesta>> call,
                    Response<List<apuesta>> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    List<apuesta> apuestas = response.body();

                    ArrayList<String> lista = new ArrayList<>();

                    double totalApostado = 0;
                    double totalResultado = 0;

                    for (apuesta a : apuestas) {

                        totalApostado += a.getMontoApostado();

                        totalResultado += a.getMontoResultado();

                        String item =
                                "Juego ID: " + a.getIdJuego() +
                                        "\nMonto Apostado: $" + a.getMontoApostado() +
                                        "\nMonto Resultado: $" + a.getMontoResultado() +
                                        "\nFecha: " + a.getFecha();

                        lista.add(item);
                    }

                    tvTotalApostado.setText(
                            "Total Apostado: $" + totalApostado
                    );

                    tvTotalResultado.setText(
                            "Total Resultado: $" + totalResultado
                    );

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(
                                    HistoryActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    lista
                            );

                    listViewHistorial.setAdapter(adapter);

                } else {

                    Toast.makeText(
                            HistoryActivity.this,
                            "Error cargando historial",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<apuesta>> call,
                    Throwable t) {

                Toast.makeText(
                        HistoryActivity.this,
                        "Error conexión",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}