package com.masnegocio.semilla.models;

/**
 * Created by Tadeo-developer on 25/10/16.
 */

public class Value {

    private Object value;
    private String label;
    private boolean selected;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
