package com.masnegocio.semilla.models;

/**
 * Created by Tadeo-developer on 26/10/16.
 */

public class Action extends ObjectGeneral{

    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
