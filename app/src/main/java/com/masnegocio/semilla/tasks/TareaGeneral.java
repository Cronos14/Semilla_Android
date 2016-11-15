package com.masnegocio.semilla.tasks;

import android.support.v7.app.AppCompatActivity;

import com.masnegocio.semilla.models.Event;
import com.masnegocio.semilla.models.ObjectGeneral;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Tadeo-developer on 27/10/16.
 */

public abstract class TareaGeneral extends Tarea {

    protected HashMap<String,Object> parameters;
    protected TareaGeneral(AppCompatActivity appCompatActivity, String title) {
        super(appCompatActivity, title);
    }

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }

    protected Event getEvent(JSONObject eventJson){
        if (eventJson != null) {
            Event event = new Event();

            event.addAttributesOfJson(eventJson);

            JSONObject headersJson = eventJson.optJSONObject("headers");
            if(headersJson!=null){
                ObjectGeneral headers = new ObjectGeneral();
                headers.addAttributesOfJson(headersJson);
                event.setHeaders(headers);
            }

            JSONObject parametersJson = eventJson.optJSONObject("parameters");
            if(parametersJson!=null){
                ObjectGeneral parameters = new ObjectGeneral();
                parameters.addAttributesOfJson(parametersJson);
                event.setParameters(parameters);
            }

            return event;
        }

        return null;
    }
}
