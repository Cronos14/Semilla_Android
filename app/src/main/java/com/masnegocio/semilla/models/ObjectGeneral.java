package com.masnegocio.semilla.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Tadeo-developer on 01/11/16.
 */

public class ObjectGeneral implements Serializable {
    private HashMap<String,Object> attributes;

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String key, Object value){
        if(attributes==null){
            attributes = new HashMap<>();
        }

        attributes.put(key,value);
    }

    public void addAttributesOfJson(JSONObject jsonObject){

        JSONArray names = jsonObject.names();

        for(int i = 0;i<names.length();i++){

            String name = names.optString(i);

            Object element = jsonObject.opt(name);

            if(element!=null && (!(element instanceof JSONObject) && !(element instanceof JSONArray))){
                addAttribute(name,jsonObject.opt(name));
            }

        }

    }
}
