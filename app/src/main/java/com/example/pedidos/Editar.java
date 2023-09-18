package com.example.pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class Editar extends AppCompatActivity {
    EditText txtDate,txtPedido,txtCliente,txtPlataforma,txtTotal,txtSeña;
    TextView txtId;
    Button btnSend;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        txtId=findViewById(R.id.txtId);
        txtDate = findViewById(R.id.txtDate);
        txtPedido = findViewById(R.id.txtPedido);
        txtCliente = findViewById(R.id.txtCliente);
        txtPlataforma = findViewById(R.id.txtPlataforma);
        txtTotal = findViewById(R.id.txtTotal);
        txtSeña = findViewById(R.id.txtSeña);

        btnSend=findViewById(R.id.btnSend);
        Intent intent = getIntent();
        position=intent.getExtras().getInt("position");

        txtId.setText(Home.users.get(position).getId());
        txtDate.setText(Home.users.get(position).getFecha());
        txtPedido.setText(Home.users.get(position).getPedido());
        txtCliente.setText(Home.users.get(position).getCliente());
        txtPlataforma.setText(Home.users.get(position).getPlataforma());
        txtTotal.setText(Home.users.get(position).getTotal());
        txtSeña.setText(Home.users.get(position).getSeña());
    }
    public void actualizar(View view) {
        final String id=txtId.getText().toString();
        final String fecha=txtDate.getText().toString();
        final String pedido=txtPedido.getText().toString();
        final String cliente=txtCliente.getText().toString();
        final String plataforma=txtPlataforma.getText().toString();
        final String total=txtTotal.getText().toString();
        final String seña=txtSeña.getText().toString();







        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://pedidoshade.000webhostapp.com/CRUD/editar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Editar.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Editar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("id",id);
                params.put("fecha",fecha);
                params.put("pedido",pedido);
                params.put("cliente",cliente);
                params.put("plataforma",plataforma);
                params.put("total",total);
                params.put("seña",seña);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
        requestQueue.add(request);





    }
}