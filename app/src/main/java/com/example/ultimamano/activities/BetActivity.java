package com.example.ultimamano.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;
import com.example.ultimamano.models.apuesta;
import com.example.ultimamano.network.RetrofitClient;
import com.example.ultimamano.network.SupabaseApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BetActivity extends AppCompatActivity {

    EditText etIdJuego;
    EditText etMontoApostado;
    EditText etMontoResultado;
    EditText etFecha;

    Button btnGuardarApuesta;

    SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        etIdJuego = findViewById(R.id.etIdJuego);
        etMontoApostado = findViewById(R.id.etMontoApostado);
        etMontoResultado = findViewById(R.id.etMontoResultado);
        etFecha = findViewById(R.id.etFecha);

        btnGuardarApuesta = findViewById(R.id.btnGuardarApuesta);

        api = RetrofitClient.getClient().create(SupabaseApi.class);

        btnGuardarApuesta.setOnClickListener(v -> guardarApuesta());
    }

    private void guardarApuesta() {

        int idUsuario = 1; // TEMPORAL

        int idJuego = Integer.parseInt(
                etIdJuego.getText().toString()
        );

        double montoApostado = Double.parseDouble(
                etMontoApostado.getText().toString()
        );

        double montoResultado = Double.parseDouble(
                etMontoResultado.getText().toString()
        );

        String fecha = etFecha.getText().toString();

        apuesta a = new apuesta();

        a.setIdUsuario(idUsuario);
        a.setIdJuego(idJuego);
        a.setMontoApostado(montoApostado);
        a.setMontoResultado(montoResultado);
        a.setFecha(fecha);

        api.registrarApuesta(a).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(
                    Call<ResponseBody> call,
                    Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            BetActivity.this,
                            "Apuesta guardada",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    Toast.makeText(
                            BetActivity.this,
                            "Error al guardar",
                            Toast.LENGTH_SHORT
                    ).show();

                    Log.e("APUESTA", "HTTP " + response.code());

                    try {

                        Log.e(
                                "APUESTA_ERROR",
                                response.errorBody().string()
                        );

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(
                    Call<ResponseBody> call,
                    Throwable t) {

                Toast.makeText(
                        BetActivity.this,
                        "Error conexión",
                        Toast.LENGTH_SHORT
                ).show();

                Log.e("APUESTA", t.getMessage());
            }
        });
    }
}