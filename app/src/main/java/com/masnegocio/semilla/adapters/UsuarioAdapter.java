package com.masnegocio.semilla.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.masnegocio.semilla.adapters.holders.UsuarioViewHolder;
import com.masnegocio.semilla.adapters.holders.ViewHolder;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 10/06/16.
 */
public class UsuarioAdapter<T> extends AdapterGeneral {
    public UsuarioAdapter(ArrayList<T> objects, int layout){
        super(objects,layout);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = getViewInflater(parent);

        ViewHolder anticiposViewHolder = new UsuarioViewHolder(itemView);
        itemView.setOnClickListener(this);
        return anticiposViewHolder;
    }

}
