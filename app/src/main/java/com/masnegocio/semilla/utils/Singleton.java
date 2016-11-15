package com.masnegocio.semilla.utils;


import com.masnegocio.semilla.models.Tema;
import com.masnegocio.semilla.models.User;

/**
 * Created by Tadeo-developer on 11/10/16.
 */

public class Singleton {

    private static Singleton singleton;

    private String id;
    private User user;
    private Tema tema;

    public static Singleton getInstance(){
        if(singleton==null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    private Singleton(){

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static Singleton getSingleton() {
        return singleton;
    }

    public static void setSingleton(Singleton singleton) {
        Singleton.singleton = singleton;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
