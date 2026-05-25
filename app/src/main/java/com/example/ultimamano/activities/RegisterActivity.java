package com.example.ultimamano.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;
import com.example.ultimamano.models.usuario;
import com.example.ultimamano.network.RetrofitClient;
import com.example.ultimamano.network.SupabaseApi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etNombre, etEmail, etPassword;
    RadioGroup radioRol;
    Button btnRegister;

    Button btnBackLogin;
    SupabaseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // UI
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        radioRol = findViewById(R.id.radioRol);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackLogin = findViewById(R.id.btnBackLogin);

        // API
        api = RetrofitClient.getClient().create(SupabaseApi.class);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        String nombre = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        // VALIDACIÓN BÁSICA
        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔥 DEFINIR ROL
        int rol = 1; // jugador por defecto

        int selectedId = radioRol.getCheckedRadioButtonId();

        if (selectedId == R.id.rbCasino) {
            rol = 3;
        }



        // CREAR OBJETO USUARIO
        usuario u = new usuario();
        u.setNombre(nombre);
        u.setEmail(email);
        u.setContrasena(pass);
        u.setIdRol(rol);


        // PETICIÓN A SUPABASE
        api.registerUser(u).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(RegisterActivity.this,
                            "Usuario registrado correctamente",
                            Toast.LENGTH_SHORT).show();

                    Log.d("REGISTER", "OK");

                    btnBackLogin.setVisibility(Button.VISIBLE);

                    btnBackLogin.setOnClickListener(v -> {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });

                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.e("REGISTER_ERROR", error);
                    } catch (Exception e) {
                        Log.e("REGISTER_ERROR", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(RegisterActivity.this,
                        "Error de conexión",
                        Toast.LENGTH_SHORT).show();

                Log.e("REGISTER", t.getMessage());
            }
        });
    }
}