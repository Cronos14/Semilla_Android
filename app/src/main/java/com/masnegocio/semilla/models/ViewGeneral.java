package com.masnegocio.semilla.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 24/09/16.
 */

public class ViewGeneral extends ObjectGeneral implements Serializable {

    private ArrayList<ViewGeneral> viewGenerals;

    public ArrayList<ViewGeneral> getViewGenerals() {
        return viewGenerals;
    }

    public void setViewGenerals(ArrayList<ViewGeneral> viewGenerals) {
        this.viewGenerals = viewGenerals;
    }
}
