package com.example.ultimamano.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;

public class PlayerHomeActivity extends AppCompatActivity {

    TextView tvBienvenida;

    Button btnCasinos;
    Button btnApuestas;
    Button btnPresupuesto;
    Button btnHistorial;
    Button btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_home);

        tvBienvenida = findViewById(R.id.tvBienvenida);

        btnCasinos = findViewById(R.id.btnCasinos);
        btnApuestas = findViewById(R.id.btnApuestas);
        btnPresupuesto = findViewById(R.id.btnPresupuesto);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // RECIBIR NOMBRE
        String nombre = getIntent().getStringExtra("nombre");

        tvBienvenida.setText("Bienvenido ");

        // BOTONES

        btnCasinos.setOnClickListener(v -> {
            startActivity(new Intent(this, CasinoDirectoryActivity.class));
        });

        btnApuestas.setOnClickListener(v -> {
            startActivity(new Intent(this, BetActivity.class));
        });

        btnPresupuesto.setOnClickListener(v -> {
            startActivity(new Intent(this, BudgetActivity.class));
        });

        btnHistorial.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });

        btnCerrarSesion.setOnClickListener(v -> {

            Intent intent = new Intent(this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

        });
    }
}