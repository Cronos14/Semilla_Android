package com.masnegocio.semilla.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 26/10/16.
 */

public class Table extends ObjectGeneral implements Serializable {

    private ArrayList<ObjectGeneral> headers;
    private ArrayList<Row> rows;

    public ArrayList<ObjectGeneral> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<ObjectGeneral> headers) {
        this.headers = headers;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Row> rows) {
        this.rows = rows;
    }
}
