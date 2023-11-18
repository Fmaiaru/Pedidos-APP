package com.example.pedidos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class insertar extends AppCompatActivity {

    EditText txtDate,txtPedido,txtCliente,txtPlataforma,txtTotal,txtSeña;
    Button btnSend;
    Button dateButton;

    DatePickerDialog datePickerDialog;
    String tabla;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtPedido = findViewById(R.id.txtPedido);
        txtCliente = findViewById(R.id.txtCliente);
        txtPlataforma = findViewById(R.id.txtPlataforma);
        txtTotal = findViewById(R.id.txtTotal);
        txtSeña = findViewById(R.id.txtSeña);

        dateButton=findViewById(R.id.spinner);
        dateButton.setText(getTodaysDate());
        initDatePicker();

        Intent insert=getIntent();
        tabla=insert.getStringExtra("tabla");

        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();


            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month= month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                month=month+1;
                String date = makeDateString(year,month,day);
                dateButton.setText(date);


            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style= AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year) {
        return day+"/"+month+"/"+year;
    }

    public void openDatePicker(View view){
        datePickerDialog.show();

    }

    private void insertar() {
        String fecha=dateButton.getText().toString().trim();
        String pedido=txtPedido.getText().toString().trim();
        String cliente=txtCliente.getText().toString().trim();
        String plataforma=txtPlataforma.getText().toString().trim();
        String total=txtTotal.getText().toString().trim();
        String seña=txtSeña.getText().toString().trim();


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

            StringRequest request=new StringRequest(Request.Method.POST, "https://pedidoshade.000webhostapp.com/CRUD/insertar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("datos insertados")) {
                        Toast.makeText(insertar.this, "datos ingresados", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(), Cargando.class));
                        finish();
                    } else {
                        Toast.makeText(insertar.this, response, Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(insertar.this,error.getMessage(),Toast.LENGTH_SHORT).show();

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
                    params.put("tabla",tabla);
                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(insertar.this);
            requestQueue.add(request);
        }

    }


}