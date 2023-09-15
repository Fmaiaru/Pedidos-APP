package com.example.pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Detalles extends AppCompatActivity {
    TextView txtId,txtDate,txtPedido,txtCliente,txtPlataforma,txtTotal,txtSeña;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        txtId= findViewById(R.id.txtId);
        txtDate = findViewById(R.id.txtDate);
        txtPedido = findViewById(R.id.txtPedido);
        txtCliente = findViewById(R.id.txtCliente);
        txtPlataforma = findViewById(R.id.txtPlataforma);
        txtTotal = findViewById(R.id.txtTotal);
        txtSeña = findViewById(R.id.txtSeña);
        Intent intent = getIntent();

        position=intent.getExtras().getInt("position");

        txtId.setText("ID: " + Home.users.get(position).getId());
        txtDate.setText("Fecha: " + Home.users.get(position).getFecha());
        txtPedido.setText("Pedido: " + Home.users.get(position).getPedido());
        txtCliente.setText("Cliente: " + Home.users.get(position).getCliente());
        txtPlataforma.setText("Plataforma: " + Home.users.get(position).getPlataforma());
        txtTotal.setText("Total: " + Home.users.get(position).getTotal());
        txtSeña.setText("Seña: " + Home.users.get(position).getSeña());
    }
}