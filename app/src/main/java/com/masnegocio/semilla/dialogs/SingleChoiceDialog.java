package com.masnegocio.semilla.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.masnegocio.semilla.R;
import com.masnegocio.semilla.models.ObjectGeneral;

import java.util.ArrayList;

/**
 * Created by raul.gonzalez on 28/12/2015.
 */
public class SingleChoiceDialog extends DialogFragment {

    public interface OnClickListener{
        void onClick(Object object);
    }

    private OnClickListener onClickListener;
    private String titulo;
    private int numeroBotones;
    private int position;
    private ArrayList<ObjectGeneral> headers;


    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }




    public static SingleChoiceDialog newInstance(String titulo, int numeroBotones, ArrayList<ObjectGeneral> headers) {

        Bundle args = new Bundle();

        args.putString("titulo",titulo);
        args.putInt("numeroBotones",numeroBotones);
        args.putSerializable("headers", headers);
        SingleChoiceDialog fragment = new SingleChoiceDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());

        if(getArguments()!=null){
            titulo = getArguments().getString("titulo");
            numeroBotones = getArguments().getInt("numeroBotones");
            headers = (ArrayList<ObjectGeneral>) getArguments().getSerializable("headers");

        }

        final LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams paramsLinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(paramsLinear);

        //LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,Utils.getPixelValue(getContext(), (int) getResources().getDimension(R.dimen.dialog_titulo)));
        LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) getResources().getDimension(R.dimen.dialog_titulo));
        //paramsTv.gravity = Gravity.CENTER;
        TextView tvTitulo = new TextView(getContext());
        tvTitulo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tvTitulo.setText(titulo);
        tvTitulo.setTextColor(getResources().getColor(android.R.color.white));
        tvTitulo.setGravity(Gravity.CENTER);
        tvTitulo.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.dialog_titulo_size));
        tvTitulo.setLayoutParams(paramsTv);

        linearLayout.addView(tvTitulo);



        final String[] headersArray = new String[headers.size()];

        for(int i = 0;i<headers.size();i++){
            ObjectGeneral objectGeneral = headers.get(i);
            String head = (String)objectGeneral.getAttributes().get("label");
            headersArray[i] = head;

        }

        alerta.setTitle(titulo);
        //alerta.setView(linearLayout);

        alerta.setSingleChoiceItems(headersArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("SingleChoiceDialog","pos: "+which);
                position = which;
            }
        });




        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(onClickListener!=null)
                    onClickListener.onClick(headers.get(position).getAttributes().get("field"));

                dismiss();
            }
        });

        if(numeroBotones>=2) {
            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(onClickListener!=null)
                        onClickListener.onClick(headers.get(0).getAttributes().get("field"));
                    dismiss();
                }
            });
        }

        return alerta.create();
    }
}
