package com.example.pedidos;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    private ListView list;
Adaptador adaptador;
public static ArrayList<Users>users=new ArrayList<>();

String url="https://pedidoshade.000webhostapp.com/CRUD/mostrar.php";
Users pedidos;
TextView usuario;

String tabla;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list = findViewById(R.id.Listview);
        adaptador = new Adaptador(this, users);
        list.setAdapter(adaptador);
        usuario= findViewById(R.id.provisorio);

        SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        String provisorio=preferences.getString("strUser","strUser");
        usuario.setText(provisorio);

       // Bundle recibeUsuario = getIntent().getExtras();
       // tabla = recibeUsuario.getString("keyUser");

        tabla =provisorio;

        if(savedInstanceState!=null){
            tabla=savedInstanceState.getString("tabla");
            usuario.setText(tabla);
        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog=new ProgressDialog(view.getContext());
                CharSequence[]dialogoItem={"Ver datos","Editar datos", "Eliminar datos"};
                builder.setTitle(users.get(position).getCliente());
                builder.setItems(dialogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                            startActivity( new Intent(getApplicationContext(), Detalles.class).putExtra("position",position));
                                break;
                            case 1:
                                Intent editar = new Intent(Home.this, Editar.class);
                                editar.putExtra("position",position);
                                editar.putExtra("tabla",tabla);
                                startActivity(editar);
                                break;
                            case 2:
                                EliminarDatos(users.get(position).getId());
                                break;
                        }

                    }
                });
                builder.create().show();


            }
        });
        mostrardatos();


    }




    private void EliminarDatos(final String id) {
        StringRequest request = new StringRequest(Request.Method.POST,"https://pedidoshade.000webhostapp.com/CRUD/eliminar.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("datos eliminados")) {
                    Toast.makeText(Home.this, "eliminando", Toast.LENGTH_SHORT).show();
                    Intent elimina = new Intent(getApplicationContext(), Home.class);
                    startActivity(elimina);
                    finish();

                } else {
                    Toast.makeText(Home.this, "no se pudo eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Home.this,"error",Toast.LENGTH_SHORT).show();

                }

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("id",id);
                params.put("tabla",tabla);
                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public  void  agregar (View view){
        Intent insert = new Intent(Home.this, insertar.class);
        insert.putExtra("tabla",tabla);
        startActivity(insert);
    }
    public void mostrardatos(){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                users.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray(tabla);
                    if (success.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);

                            String id=object.getString("id");
                            String fecha=object.getString("fecha");
                            String pedido=object.getString("pedido");
                            String cliente=object.getString("cliente");
                            String plataforma=object.getString("plataforma");
                            String total=object.getString("total");
                            String seña=object.getString("seña");
                            pedidos=new Users(id,fecha,pedido,cliente,plataforma,total,seña);
                            users.add(pedidos);
                            adaptador.notifyDataSetChanged();
                        }
                    }


                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros=new HashMap<String,String>();
                parametros.put("tabla",tabla);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue( this);
        requestQueue.add(request);
    }
    public void btnAgg (View view){
        startActivity(new Intent(getApplicationContext(), insertar.class));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("tabla", tabla);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tabla=savedInstanceState.getString("tabla");
    }


}