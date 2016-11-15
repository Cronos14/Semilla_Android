package com.masnegocio.semilla.models;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 24/10/16.
 */

public class ViewCustom extends ViewGeneral {
    private ArrayList<ObjectGeneral> values;

    public ArrayList<ObjectGeneral> getValues() {
        return values;
    }

    public void setValues(ArrayList<ObjectGeneral> values) {
        this.values = values;
    }
}

