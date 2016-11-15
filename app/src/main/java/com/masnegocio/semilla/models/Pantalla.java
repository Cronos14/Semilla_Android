package com.masnegocio.semilla.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 24/09/16.
 */

public class Pantalla implements Serializable {

    private int id;
    private String nombre;

    private ArrayList<ViewGeneral> viewGenerals;

    public Pantalla(){
        viewGenerals = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addViewCustom(ViewGeneral viewGeneral){
        viewGenerals.add(viewGeneral);
    }

    public ArrayList<ViewGeneral> getViewGenerals() {
        return viewGenerals;
    }

    public void setViewGenerals(ArrayList<ViewGeneral> viewGenerals) {
        this.viewGenerals = viewGenerals;
    }

    @Override
    public boolean equals(Object obj) {


        if(((Pantalla) obj).getId()==getId())
            return true;
        else
            return false;

    }
}
