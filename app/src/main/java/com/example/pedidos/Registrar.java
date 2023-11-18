package com.example.pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class Registrar extends AppCompatActivity {
    EditText txtUsuario,txtMail,txtContraseña;
    Button regBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        txtUsuario = findViewById(R.id.usuario);
        txtMail = findViewById(R.id.mail);
        txtContraseña = findViewById(R.id.contraseña);

        regBtn = findViewById(R.id.btnRegistrar);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();


            }
        });
    }

    private void registrar() {
        String usuario=txtUsuario.getText().toString();
        String mail=txtMail.getText().toString();
        String contraseña=txtContraseña.getText().toString();


        ProgressDialog progressDialog=new ProgressDialog(this);
        if(usuario.isEmpty()){
            txtUsuario.setError("Complete este campo");
        }
        else if(mail.isEmpty()){
            txtMail.setError("Complete este campo");
        }
        else if(contraseña.isEmpty()){
            txtContraseña.setError("Complete este campo");
        }else{
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, "https://pedidoshade.000webhostapp.com/CRUD/registrar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Registrar.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                    progressDialog.dismiss();
                    }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Registrar.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String ,String>params=new HashMap<String,String>();
                    params.put("usuario",usuario);
                    params.put("mail",mail);
                    params.put("contraseña",contraseña);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(Registrar.this);
            requestQueue.add(request);
        }

    }
}