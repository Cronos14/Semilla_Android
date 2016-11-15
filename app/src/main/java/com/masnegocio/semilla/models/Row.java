package com.masnegocio.semilla.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 01/11/16.
 */

public class Row implements Serializable {

    private ArrayList<DataRow> fields;
    private ArrayList<Action> actions;

    public ArrayList<DataRow> getFields() {
        return fields;
    }

    public void setFields(ArrayList<DataRow> fields) {
        this.fields = fields;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }
}
