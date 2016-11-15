package com.masnegocio.semilla.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.masnegocio.semilla.R;


/**
 * Created by Tadeo-developer on 18/01/16.
 */
public class UsuarioViewHolder extends ViewHolder{

    private TextView usuario;
    private TextView nombreCompleto;
    private TextView correo;
    private TextView departamento;
    private TextView puesto;
    private TextView estatus;


    public UsuarioViewHolder(View itemView) {
        super(itemView);

        usuario = (TextView)itemView.findViewById(R.id.tv_usuario);
        nombreCompleto = (TextView)itemView.findViewById(R.id.tv_nombre_completo);
        //correo = (TextView)itemView.findViewById(R.id.tv_correo);
        departamento = (TextView)itemView.findViewById(R.id.tv_departamento);
        puesto = (TextView)itemView.findViewById(R.id.tv_puesto);
        estatus = (TextView)itemView.findViewById(R.id.tv_estatus);


    }

    @Override
    public void bind(Object obj) {
        /*if(obj instanceof Data){

            data = (Data)obj;

            usuario.setText(data.getUsuario());
            nombreCompleto.setText(data.getNombre()+" "+data.getPrimerApellido()+" "+data.getSegundoApellido());
           // correo.setText(data.getCorreoElectronico());
            departamento.setText(data.getDepartamento());
            puesto.setText(data.getPuesto());
            estatus.setText(data.getEstatus());
        }*/

    }


}