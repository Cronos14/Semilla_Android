package com.masnegocio.semilla.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 25/10/16.
 */

public class Form extends ObjectGeneral implements Serializable {

    private MenuGeneral toolbar;
    private ArrayList<Section> sections;

    public MenuGeneral getToolbar() {
        return toolbar;
    }

    public void setToolbar(MenuGeneral toolbar) {
        this.toolbar = toolbar;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }
}
