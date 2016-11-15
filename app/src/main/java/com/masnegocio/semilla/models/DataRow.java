package com.masnegocio.semilla.models;

import java.io.Serializable;

/**
 * Created by Tadeo-developer on 01/11/16.
 */

public class DataRow implements Serializable {

    private ObjectGeneral header;
    private Object data;


    public ObjectGeneral getHeader() {
        return header;
    }

    public void setHeader(ObjectGeneral header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj.toString().equalsIgnoreCase(data.toString()))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
