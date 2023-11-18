package com.example.pedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adaptador extends ArrayAdapter<Users> {

    Context context;
            List<Users>arrayalistaUsers;
    public Adaptador(@NonNull Context context, List<Users>arrayalistaUsers) {
        super(context, R.layout.my_list_item,arrayalistaUsers);
        this.context=context;
        this.arrayalistaUsers=arrayalistaUsers;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item,null,true);

        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvCliente = view.findViewById(R.id.tvCliente);

    tvId.setText(arrayalistaUsers.get(position).getId());
    tvCliente.setText(arrayalistaUsers.get(position).getCliente());
    return view;

    }
}

