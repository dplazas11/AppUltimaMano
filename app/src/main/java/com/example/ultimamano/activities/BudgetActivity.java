package com.example.ultimamano.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;
import com.example.ultimamano.models.apuesta;
import com.example.ultimamano.network.RetrofitClient;
import com.example.ultimamano.network.SupabaseApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetActivity extends AppCompatActivity {

    EditText etPresupuesto;

    Button btnCalcular;

    TextView tvTotalApostado;
    TextView tvTotalResultado;
    TextView tvRestante;

    SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        etPresupuesto = findViewById(R.id.etPresupuesto);

        btnCalcular = findViewById(R.id.btnCalcular);

        tvTotalApostado = findViewById(R.id.tvTotalApostado);
        tvTotalResultado = findViewById(R.id.tvTotalResultado);
        tvRestante = findViewById(R.id.tvRestante);

        api = RetrofitClient
                .getClient()
                .create(SupabaseApi.class);

        btnCalcular.setOnClickListener(v -> calcularPresupuesto());
    }

    private void calcularPresupuesto() {

        String presupuestoTexto =
                etPresupuesto.getText().toString();

        if (presupuestoTexto.isEmpty()) {

            Toast.makeText(
                    this,
                    "Ingrese presupuesto",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double presupuesto =
                Double.parseDouble(presupuestoTexto);

        api.getApuestas().enqueue(new Callback<List<apuesta>>() {

            @Override
            public void onResponse(
                    Call<List<apuesta>> call,
                    Response<List<apuesta>> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    double totalApostado = 0;
                    double totalResultado = 0;

                    for (apuesta a : response.body()) {

                        totalApostado += a.getMontoApostado();

                        totalResultado += a.getMontoResultado();
                    }

                    double restante =
                            presupuesto
                                    - totalApostado
                                    + totalResultado;

                    tvTotalApostado.setText(
                            "Total Apostado: $" + totalApostado
                    );

                    tvTotalResultado.setText(
                            "Total Resultado: $" + totalResultado
                    );

                    tvRestante.setText(
                            "Dinero Restante: $" + restante
                    );

                } else {

                    Toast.makeText(
                            BudgetActivity.this,
                            "Error obteniendo apuestas",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<apuesta>> call,
                    Throwable t) {

                Toast.makeText(
                        BudgetActivity.this,
                        "Error conexión",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}