package com.masnegocio.semilla.models;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 10/10/16.
 */

public class MenuGeneral extends ViewGeneral {

    private Event event;

    public void addMenuGeneral(MenuGeneral menuGeneral){
        if(getViewGenerals()==null){
            setViewGenerals(new ArrayList<ViewGeneral>());
            addMenuGeneral(menuGeneral);
        }else{
            getViewGenerals().add(menuGeneral);
        }

    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
