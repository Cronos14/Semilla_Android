package com.masnegocio.semilla.models;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 07/10/16.
 */

public class Tema {
    private ViewGeneral toolbar;
    private ArrayList<Pantalla> pantallas;
    private ArrayList<Form> forms;
    private ArrayList<Catalog> catalogs;

    public ViewGeneral getToolbar() {
        return toolbar;
    }

    public void setToolbar(ViewGeneral toolbar) {
        this.toolbar = toolbar;
    }

    public ArrayList<Pantalla> getPantallas() {
        return pantallas;
    }

    public void setPantallas(ArrayList<Pantalla> pantallas) {
        this.pantallas = pantallas;
    }

    public ArrayList<Form> getForms() {
        return forms;
    }

    public void addForm(Form form){
        if(forms==null) {
            forms = new ArrayList<>();
        }
        forms.add(form);

    }

    public void addCatalog(Catalog catalog){
        if(catalogs==null)
            catalogs = new ArrayList<>();

        catalogs.add(catalog);
    }

    public ArrayList<Catalog> getCatalogs() {
        return catalogs;
    }
}
