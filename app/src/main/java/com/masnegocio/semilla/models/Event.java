package com.masnegocio.semilla.models;

import java.io.Serializable;

/**
 * Created by Tadeo-developer on 20/10/16.
 */

public class Event extends ObjectGeneral implements Serializable {

    private ObjectGeneral headers;
    private ObjectGeneral parameters;

    public ObjectGeneral getHeaders() {
        return headers;
    }

    public void setHeaders(ObjectGeneral headers) {
        this.headers = headers;
    }

    public ObjectGeneral getParameters() {
        return parameters;
    }

    public void setParameters(ObjectGeneral parameters) {
        this.parameters = parameters;
    }
}
