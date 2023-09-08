package com.example.pedidos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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

public class Home extends AppCompatActivity {
    private ListView list;
Adaptador adaptador;
public static ArrayList<Users>users=new ArrayList<>();
String url="https://pedidoshade.000webhostapp.com/CRUD/mostrar.php";
Users usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        list=findViewById(R.id.Listview);
        adaptador=new Adaptador(this,users);
        list.setAdapter(adaptador);
        mostrardatos();
    }
    public void mostrardatos(){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                users.clear();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("datos");
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
                            usuarios=new Users(id,fecha,pedido,cliente,plataforma,total,seña);
                            users.add(usuarios);
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
        });
        RequestQueue requestQueue= Volley.newRequestQueue( this);
        requestQueue.add(request);
    }
    public void btnAgg (View view){
        startActivity(new Intent(getApplicationContext(), insertar.class));

    }
}