package com.example.pedidos;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class insertar extends AppCompatActivity {

    EditText txtDate,txtPedido,txtCliente,txtPlataforma,txtTotal,txtSeña;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDate = findViewById(R.id.txtDate);
        txtPedido = findViewById(R.id.txtPedido);
        txtCliente = findViewById(R.id.txtCliente);
        txtPlataforma = findViewById(R.id.txtPlataforma);
        txtTotal = findViewById(R.id.txtTotal);
        txtSeña = findViewById(R.id.txtSeña);

        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();


            }
        });
    }

    private void insertar() {
        String fecha=txtDate.getText().toString().trim();
        String pedido=txtPedido.getText().toString().trim();
        String cliente=txtCliente.getText().toString().trim();
        String plataforma=txtPlataforma.getText().toString().trim();
        String total=txtTotal.getText().toString().trim();
        String seña=txtSeña.getText().toString().trim();

        ProgressDialog progressDialog=new ProgressDialog(this);
        if(fecha.isEmpty()){
            txtDate.setError("Complete este campo");
        }
        else if(pedido.isEmpty()){
            txtPedido.setError("Complete este campo");
        }
        else if(cliente.isEmpty()){
            txtCliente.setError("Complete este campo");
        }
        else if(plataforma.isEmpty()){
            txtPlataforma.setError("Complete este campo");
        }
        else if(total.isEmpty()){
            txtTotal.setError("Complete este campo");
        }else{
            progressDialog.show();
            StringRequest request=new StringRequest(Request.Method.POST, "https://pedidoshade.000webhostapp.com/CRUD/insertar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("datos insertados")) {
                        Toast.makeText(insertar.this, "datos ingresados", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(insertar.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(insertar.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String ,String>params=new HashMap<String,String>();
                    params.put("fecha",fecha);
                    params.put("pedido",pedido);
                    params.put("cliente",cliente);
                    params.put("plataforma",plataforma);
                    params.put("total",total);
                    params.put("seña",seña);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(insertar.this);
            requestQueue.add(request);
        }

    }


}