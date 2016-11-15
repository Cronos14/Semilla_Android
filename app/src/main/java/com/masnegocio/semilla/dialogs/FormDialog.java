package com.masnegocio.semilla.dialogs;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.masnegocio.semilla.R;
import com.masnegocio.semilla.models.Event;
import com.masnegocio.semilla.models.Form;
import com.masnegocio.semilla.models.MenuGeneral;
import com.masnegocio.semilla.models.Pantalla;
import com.masnegocio.semilla.models.ViewGeneral;
import com.masnegocio.semilla.utils.Utils;


/**
 * Created by raul.gonzalez on 28/12/2015.
 */
public class FormDialog extends DialogFragment {

    public interface OnClickListener{
        void onClick(Object object);
    }

    private Pantalla pantalla;
    private Form form;
    private OnClickListener onClickListener;
    private String titulo;
    private int numeroBotones;
    private Event event;


    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }




    public static FormDialog newInstance(Pantalla pantalla, String titulo, int numeroBotones) {

        Bundle args = new Bundle();
        args.putSerializable("form", pantalla);
        args.putString("titulo",titulo);
        args.putInt("numeroBotones",numeroBotones);
        FormDialog fragment = new FormDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static FormDialog newInstance(Form form, String titulo, int numeroBotones) {

        Bundle args = new Bundle();
        args.putSerializable("form", form);
        args.putString("titulo",titulo);
        args.putInt("numeroBotones",numeroBotones);
        FormDialog fragment = new FormDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());

        if(getArguments()!=null){
            titulo = getArguments().getString("titulo");
            numeroBotones = getArguments().getInt("numeroBotones");

            if(getArguments().getSerializable("form") instanceof Pantalla){
                pantalla = (Pantalla) getArguments().getSerializable("form");
            }else if(getArguments().getSerializable("form") instanceof Form){
                form = (Form) getArguments().getSerializable("form");
            }



        }

        View view = null;
        if(pantalla!=null){
            view = Utils.generateForm(getActivity(), pantalla);
        }else if(form != null){
            view = Utils.generateForm(getActivity(), form);
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
        linearLayout.addView(view);


        alerta.setView(linearLayout);

        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MenuGeneral toolbar = form.getToolbar();
                for(ViewGeneral viewGeneral : toolbar.getViewGenerals()){
                    MenuGeneral menuGeneral = (MenuGeneral)viewGeneral;

                    if(menuGeneral.getAttributes().get("style").toString().equalsIgnoreCase("btnGuardar")){
                        Event event = menuGeneral.getEvent();
                    }
                }

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
