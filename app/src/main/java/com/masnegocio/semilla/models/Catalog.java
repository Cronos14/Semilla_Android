package com.masnegocio.semilla.models;

import java.io.Serializable;

/**
 * Created by Tadeo-developer on 26/10/16.
 */

public class Catalog extends ObjectGeneral implements Serializable {

    private String title;
    private MenuGeneral toolbar;
    private Table table;
    private Event rootEvent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MenuGeneral getToolbar() {
        return toolbar;
    }

    public void setToolbar(MenuGeneral toolbar) {
        this.toolbar = toolbar;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Event getRootEvent() {
        return rootEvent;
    }

    public void setRootEvent(Event rootEvent) {
        this.rootEvent = rootEvent;
    }
}
