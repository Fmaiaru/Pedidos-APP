package com.example.pedidos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
EditText edtUser,edtPassword;
Button btnLogin;

Button recuperala;
Button btnRegistrar;
String strUser,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser=findViewById(R.id.mailLogin);
        edtPassword=findViewById(R.id.contraseñaLogin);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegistrar=findViewById(R.id.aRegistrarBtn);
        recuperala=findViewById(R.id.forgotBtn);
        recuperarPreferencias();



        recuperala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(getApplicationContext(), Recuperar.class));
            }
        });
btnRegistrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity( new Intent(getApplicationContext(), Registrar.class));
    }
});

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                strUser=edtUser.getText().toString();
                password=edtPassword.getText().toString();
                if(!strUser.isEmpty()&&!password.isEmpty()) {
                    validarUsuario("https://pedidoshade.000webhostapp.com/CRUD/login.php");
                }else {
                    Toast.makeText(Login.this, "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void  validarUsuario(String URL){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    guardarPreferencias();
                    Bundle enviaUsuario= new Bundle();
                    enviaUsuario.putString("keyUser", edtUser.getText().toString() );
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.putExtras(enviaUsuario);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Login.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros=new HashMap<String,String>();
                parametros.put("strUser",strUser);
                parametros.put("password",password);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void guardarPreferencias(){
        SharedPreferences  preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("strUser",strUser);
        editor.putString("password",password);
        editor.putBoolean("sesion",true);
        editor.commit();

    }
    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        edtUser.setText(preferences.getString("strUser","micorreo@gmail.com"));
        edtPassword.setText(preferences.getString("password", "123456"));

    }
}