package com.masnegocio.semilla.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        addAttributesOfJson(jsonObject,false);

    }

    public void addAttributesOfJson(JSONObject jsonObject,boolean recursive){

        JSONArray names = jsonObject.names();

        for(int i = 0;i<names.length();i++){

            String name = names.optString(i);

            Object element = jsonObject.opt(name);

            if(element!=null && (!(element instanceof JSONObject) && !(element instanceof JSONArray))){
                addAttribute(name,jsonObject.opt(name));
            }

            if(recursive) {
                //Agrega de forma recursiva JSONObjects y JSONArrays
                if (element != null && element instanceof JSONObject) {
                    ObjectGeneral objectGeneralHijo = new ObjectGeneral();
                    objectGeneralHijo.addAttributesOfJson((JSONObject) element,true);
                    addAttribute(name, objectGeneralHijo);
                } else if (element != null && element instanceof JSONArray) {

                    JSONArray elements = (JSONArray) element;
                    List<ObjectGeneral> objectsArray = new ArrayList<>();
                    for (int j = 0; j < elements.length(); j++) {
                        JSONObject elementHijo = elements.optJSONObject(j);
                        ObjectGeneral objectGeneral = new ObjectGeneral();
                        objectGeneral.addAttributesOfJson(elementHijo,true);
                        objectsArray.add(objectGeneral);
                    }
                    addAttribute(name, objectsArray);

                }
            }

        }

    }

    public void addAttributesOfObjectGeneral(ObjectGeneral objectGeneral){

        addAttributesOfObjectGeneral(objectGeneral,false);

    }

    public void addAttributesOfObjectGeneral(ObjectGeneral objectGeneral,boolean recursive){


        for(String name : objectGeneral.getAttributes().keySet()){

            Object element = objectGeneral.getAttributes().get(name);

            if(element!=null && !(element instanceof ObjectGeneral)){
                addAttribute(name,objectGeneral.getAttributes().get(name));
            }

            if(recursive) {
                //Agrega de forma recursiva JSONObjects y JSONArrays
                if (element != null && element instanceof ObjectGeneral) {
                    ObjectGeneral objectGeneralHijo = new ObjectGeneral();
                    objectGeneralHijo.addAttributesOfObjectGeneral((ObjectGeneral) element,true);
                    addAttribute(name, objectGeneralHijo);
                } else if (element != null && element instanceof List) {

                    List<ObjectGeneral> elements = (List) element;
                    List<ObjectGeneral> objectsArray = new ArrayList<>();
                    for (int j = 0; j < elements.size(); j++) {
                        ObjectGeneral elementHijo = elements.get(j);
                        ObjectGeneral objectGeneralHijo = new ObjectGeneral();
                        objectGeneralHijo.addAttributesOfObjectGeneral(elementHijo,true);
                        objectsArray.add(objectGeneralHijo);
                    }
                    addAttribute(name, objectsArray);

                }
            }

        }

    }
}
