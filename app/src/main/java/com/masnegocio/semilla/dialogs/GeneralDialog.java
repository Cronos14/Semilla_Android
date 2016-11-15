package com.masnegocio.semilla.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.masnegocio.semilla.R;

/**
 * Created by raul.gonzalez on 28/12/2015.
 */
public class GeneralDialog extends DialogFragment {

    public interface OnClickListener{
        void onClick(Object object);
    }

    private OnClickListener onClickListener;
    private String titulo;
    private int numeroBotones;
    private String descripcion;


    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }




    public static GeneralDialog newInstance(String titulo, int numeroBotones, String descripcion) {

        Bundle args = new Bundle();

        args.putString("titulo",titulo);
        args.putInt("numeroBotones",numeroBotones);
        args.putString("descripcion", descripcion);
        GeneralDialog fragment = new GeneralDialog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());

        if(getArguments()!=null){
            titulo = getArguments().getString("titulo");
            numeroBotones = getArguments().getInt("numeroBotones");
            descripcion = getArguments().getString("descripcion");

        }

        LinearLayout.LayoutParams paramsIcon = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginIcon = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
        paramsIcon.setMargins(marginIcon,marginIcon,marginIcon,marginIcon);
        TextView textView = new TextView(getContext());
        textView.setText(descripcion);
        textView.setLayoutParams(paramsIcon);




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
        linearLayout.addView(textView);


        alerta.setView(linearLayout);

        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(onClickListener!=null)
                    onClickListener.onClick(true);

                dismiss();
            }
        });
        if(numeroBotones>=2) {
            alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(onClickListener!=null)
                        onClickListener.onClick(false);
                    dismiss();
                }
            });
        }






        return alerta.create();
    }
}
