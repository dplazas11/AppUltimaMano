package com.example.ultimamano.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;
import com.example.ultimamano.models.usuario;
import com.example.ultimamano.network.RetrofitClient;
import com.example.ultimamano.network.SupabaseApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;

    Button btnGoRegister;

    SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        api = RetrofitClient.getClient().create(SupabaseApi.class);

        btnLogin.setOnClickListener(v -> login());

        btnGoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login() {

        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        api.loginUser(email).enqueue(new Callback<List<usuario>>() {

            @Override
            public void onResponse(Call<List<usuario>> call, Response<List<usuario>> response) {

                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {

                    usuario u = response.body().get(0);

                    if (u.getContrasena().equals(pass)) {

                        Toast.makeText(LoginActivity.this,
                                "Login exitoso",
                                Toast.LENGTH_SHORT).show();

                        // REDIRECCIÓN POR ROL
                        if (u.getId_rol() == 1) {

                            startActivity(new Intent(LoginActivity.this, PlayerHomeActivity.class));

                        } else if (u.getId_rol() == 3) {

                            startActivity(new Intent(LoginActivity.this, CasinoHomeActivity.class));
                        }

                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Contraseña incorrecta",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Usuario no encontrado",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<usuario>> call, Throwable t) {
                Log.e("LOGIN_ERROR", t.getMessage());

                Toast.makeText(LoginActivity.this,
                        "Error de conexión",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}