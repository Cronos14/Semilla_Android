package com.masnegocio.semilla.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.masnegocio.semilla.R;
import com.masnegocio.semilla.adapters.holders.CustomViewHolder;
import com.masnegocio.semilla.adapters.holders.ViewHolder;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 10/06/16.
 */
public class CustomAdapter<T> extends AdapterGeneral {
    public CustomAdapter(ArrayList<T> objects){
        super(objects,0);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = getViewInflater(parent);

        ViewHolder anticiposViewHolder = new CustomViewHolder(itemView);
        itemView.setOnClickListener(this);
        return anticiposViewHolder;
    }

    @Override
    public View getViewInflater(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params);
        int margin = (int) parent.getResources().getDimension(R.dimen.activity_vertical_margin);
        params.setMargins(margin,margin,margin,margin);
        return linearLayout;
    }
}
