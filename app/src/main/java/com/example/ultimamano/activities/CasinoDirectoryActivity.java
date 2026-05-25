package com.example.ultimamano.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;
import com.example.ultimamano.models.casino;
import com.example.ultimamano.network.RetrofitClient;
import com.example.ultimamano.network.SupabaseApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CasinoDirectoryActivity extends AppCompatActivity {

    ListView listViewCasinos;

    SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_casino_directory);

        // UI
        listViewCasinos =
                findViewById(R.id.listViewCasinos);

        // API
        api = RetrofitClient
                .getClient()
                .create(SupabaseApi.class);

        // CARGAR DIRECTORIO
        cargarCasinos();
    }

    private void cargarCasinos() {

        api.getDirectorioCasinos()
                .enqueue(new Callback<List<casino>>() {

                    @Override
                    public void onResponse(
                            Call<List<casino>> call,
                            Response<List<casino>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            List<casino> casinos =
                                    response.body();

                            ArrayList<String> lista =
                                    new ArrayList<>();

                            // RECORRER CASINOS
                            for (casino c : casinos) {

                                String nombreCasino =
                                        c.getUsuario().getNombre();

                                String ciudad =
                                        c.getCiudad().getNombre();

                                String tipoCasino =
                                        c.getTipo_casino().getNombre();

                                String item =
                                        "🎰 Casino: " + nombreCasino
                                                + "\n📍 Dirección: " + c.getDireccion()
                                                + "\n🏙 Ciudad: " + ciudad
                                                + "\n🃏 Tipo: " + tipoCasino;

                                lista.add(item);
                            }

                            // ADAPTER
                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<>(
                                            CasinoDirectoryActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            lista
                                    );

                            listViewCasinos.setAdapter(adapter);

                        } else {

                            Toast.makeText(
                                    CasinoDirectoryActivity.this,
                                    "Error cargando casinos",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<casino>> call,
                            Throwable t) {

                        Toast.makeText(
                                CasinoDirectoryActivity.this,
                                "Error de conexión",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}