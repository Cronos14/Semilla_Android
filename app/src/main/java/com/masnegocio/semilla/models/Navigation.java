package com.masnegocio.semilla.models;

/**
 * Created by Tadeo-developer on 07/10/16.
 */

public class Navigation extends Tema {

    private ViewGeneral header;
    private ViewGeneral menu;

    public ViewGeneral getHeader() {
        return header;
    }

    public void setHeader(ViewGeneral header) {
        this.header = header;
    }

    public ViewGeneral getMenu() {
        return menu;
    }

    public void setMenu(ViewGeneral menu) {
        this.menu = menu;
    }
}
