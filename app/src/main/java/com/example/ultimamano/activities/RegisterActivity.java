package com.example.ultimamano.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimamano.R;
import com.example.ultimamano.models.casino;
import com.example.ultimamano.models.usuario;
import com.example.ultimamano.network.RetrofitClient;
import com.example.ultimamano.network.SupabaseApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etNombre;
    EditText etEmail;
    EditText etPassword;

    EditText etDireccion;
    EditText etCiudad;
    EditText etTipoCasino;

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

        etDireccion = findViewById(R.id.etDireccion);
        etCiudad = findViewById(R.id.etCiudad);
        etTipoCasino = findViewById(R.id.etTipoCasino);

        radioRol = findViewById(R.id.radioRol);

        btnRegister = findViewById(R.id.btnRegister);
        btnBackLogin = findViewById(R.id.btnBackLogin);

        // API
        api = RetrofitClient
                .getClient()
                .create(SupabaseApi.class);

        // OCULTAR CAMPOS CASINO AL INICIO
        ocultarCamposCasino();

        // CAMBIO DE ROL
        radioRol.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.rbCasino) {

                mostrarCamposCasino();

            } else {

                ocultarCamposCasino();
            }
        });

        // REGISTRAR
        btnRegister.setOnClickListener(v -> registerUser());

        // VOLVER LOGIN
        btnBackLogin.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);

            finish();
        });
    }

    private void registerUser() {

        String nombre =
                etNombre.getText().toString().trim();

        String email =
                etEmail.getText().toString().trim();

        String pass =
                etPassword.getText().toString().trim();

        // VALIDACIÓN
        if (nombre.isEmpty()
                || email.isEmpty()
                || pass.isEmpty()) {

            Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // DEFINIR ROL
        int rol = 1;

        int selectedId =
                radioRol.getCheckedRadioButtonId();

        if (selectedId == R.id.rbCasino) {

            rol = 3;
        }

        // VALIDAR CAMPOS CASINO
        if (rol == 3) {

            if (etDireccion.getText().toString().trim().isEmpty()
                    || etCiudad.getText().toString().trim().isEmpty()
                    || etTipoCasino.getText().toString().trim().isEmpty()) {

                Toast.makeText(
                        this,
                        "Completa los datos del casino",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }
        }

        // CREAR USUARIO
        usuario u = new usuario();

        u.setNombre(nombre);
        u.setEmail(email);
        u.setContrasena(pass);
        u.setIdRol(rol);

        // INSERT USUARIO
        int finalRol = rol;
        api.registerUser(u)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(
                            Call<ResponseBody> call,
                            Response<ResponseBody> response) {

                        if (response.isSuccessful()) {

                            Log.d("REGISTER", "Usuario registrado");

                            // SI ES CASINO
                            if (finalRol == 3) {

                                registrarCasino(email);

                            } else {

                                Toast.makeText(
                                        RegisterActivity.this,
                                        "Jugador registrado correctamente",
                                        Toast.LENGTH_SHORT
                                ).show();

                                btnBackLogin.setVisibility(View.VISIBLE);
                            }

                        } else {

                            try {

                                String error =
                                        response.errorBody().string();

                                Log.e(
                                        "REGISTER_ERROR",
                                        error
                                );

                            } catch (Exception e) {

                                Log.e(
                                        "REGISTER_ERROR",
                                        e.getMessage()
                                );
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<ResponseBody> call,
                            Throwable t) {

                        Toast.makeText(
                                RegisterActivity.this,
                                "Error de conexión",
                                Toast.LENGTH_SHORT
                        ).show();

                        Log.e(
                                "REGISTER",
                                t.getMessage()
                        );
                    }
                });
    }

    private void registrarCasino(String email) {

        api.getUsuarioByEmail("eq." + email)
                .enqueue(new Callback<List<usuario>>() {

                    @Override
                    public void onResponse(
                            Call<List<usuario>> call,
                            Response<List<usuario>> response) {

                        Log.d("CASINO_DEBUG",
                                response.body().toString());
                        if (response.isSuccessful()
                                && response.body() != null
                                && !response.body().isEmpty()) {

                            usuario usuarioCreado =
                                    response.body().get(0);

                            casino c = new casino();

                            c.setIdUsuario(
                                    usuarioCreado.getIdUsuario()
                            );

                            c.setDireccion(
                                    etDireccion
                                            .getText()
                                            .toString()
                                            .trim()
                            );

                            c.setIdCiudad(
                                    Integer.parseInt(
                                            etCiudad
                                                    .getText()
                                                    .toString()
                                                    .trim()
                                    )
                            );

                            c.setIdTipoCasino(
                                    Integer.parseInt(
                                            etTipoCasino
                                                    .getText()
                                                    .toString()
                                                    .trim()
                                    )
                            );

                            api.registrarCasino(c)
                                    .enqueue(new Callback<ResponseBody>() {

                                        @Override
                                        public void onResponse(
                                                Call<ResponseBody> call,
                                                Response<ResponseBody> response) {

                                            if (response.isSuccessful()) {

                                                Toast.makeText(
                                                        RegisterActivity.this,
                                                        "Casino registrado correctamente",
                                                        Toast.LENGTH_SHORT
                                                ).show();

                                                btnBackLogin.setVisibility(View.VISIBLE);

                                            } else {

                                                try {

                                                    Log.e(
                                                            "CASINO_ERROR",
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
                                                    RegisterActivity.this,
                                                    "Error registrando casino",
                                                    Toast.LENGTH_SHORT
                                            ).show();

                                            Log.e(
                                                    "CASINO_ERROR",
                                                    t.getMessage()
                                            );
                                        }
                                    });

                        } else {

                            Toast.makeText(
                                    RegisterActivity.this,
                                    "No se encontró usuario",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<usuario>> call,
                            Throwable t) {

                        Toast.makeText(
                                RegisterActivity.this,
                                "Error obteniendo usuario",
                                Toast.LENGTH_SHORT
                        ).show();

                        Log.e(
                                "REGISTER",
                                t.getMessage()
                        );
                    }
                });
    }

    private void ocultarCamposCasino() {

        etDireccion.setVisibility(View.GONE);

        etCiudad.setVisibility(View.GONE);

        etTipoCasino.setVisibility(View.GONE);
    }

    private void mostrarCamposCasino() {

        etDireccion.setVisibility(View.VISIBLE);

        etCiudad.setVisibility(View.VISIBLE);

        etTipoCasino.setVisibility(View.VISIBLE);
    }
}