package com.example.pedidos;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Recuperar extends AppCompatActivity {
Button button;
EditText email;

String mail;

TextView mailRecup;


String URL="https://pedidoshade.000webhostapp.com/CRUD/forgotpassword.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        button= findViewById(R.id.button);
        email = findViewById(R.id.editTextTextEmailAddress);
        mailRecup=findViewById(R.id.mailRecupTxt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperar();


            }
        });

    }

    private void recuperar() {
        mail=email.getText().toString().trim();
        StringRequest request=new StringRequest(Request.Method.POST, "https://pedidoshade.000webhostapp.com/CRUD/forgotpassword.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Recuperar.this, response, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Recuperar.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mail", mail);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Recuperar.this);
        requestQueue.add(request);
    }


}