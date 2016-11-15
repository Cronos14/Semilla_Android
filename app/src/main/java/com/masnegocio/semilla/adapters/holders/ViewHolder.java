package com.masnegocio.semilla.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by Tadeo-developer on 18/01/16.
 */
public abstract class ViewHolder<T> extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
        super(itemView);
    }


    public abstract void bind(T obj);
}
