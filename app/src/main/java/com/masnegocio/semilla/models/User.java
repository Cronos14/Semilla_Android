package com.masnegocio.semilla.models;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 07/11/16.
 */

public class User {

    private String name;
    private ArrayList<String> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public void addRol(String rol){
        if(roles==null)
            roles = new ArrayList<>();

        roles.add(rol);
    }

    @Override
    public String toString() {
        return name;
    }
}
