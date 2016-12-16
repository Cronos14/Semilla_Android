package com.masnegocio.semilla.tasks;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.masnegocio.semilla.models.Form;
import com.masnegocio.semilla.models.MenuGeneral;
import com.masnegocio.semilla.models.ObjectGeneral;
import com.masnegocio.semilla.models.Section;
import com.masnegocio.semilla.models.ViewCustom;
import com.masnegocio.semilla.models.ViewGeneral;
import com.masnegocio.semilla.ws.WebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 25/10/16.
 */

public class TareaForm extends TareaGeneral {


    public TareaForm(AppCompatActivity appCompatActivity, String title) {
        super(appCompatActivity, title);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return WebServices.serviceForm(parameters);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        if (jsonObject != null) {


            Form form = instanceForm(jsonObject.optJSONObject("form"));

            if (getOnPostExcecuteListener() != null) {
                getOnPostExcecuteListener().onPostExecute(form);
            }

        } else {
            Log.e("TareaUI", "jsonObject es null");
        }
    }

    private Form instanceForm(JSONObject jsonForm){

        Form form = new Form();

        form.addAttributesOfJson(jsonForm);

        ArrayList<Section> sections = new ArrayList<>();

        JSONArray sectionsJsonArray = jsonForm.optJSONArray("sections");

        for(int i = 0;i<sectionsJsonArray.length();i++){
            JSONObject jsonSection = sectionsJsonArray.optJSONObject(i);

            Section section = new Section();
            section.addAttributesOfJson(jsonSection);
            section.setViewGenerals(getViewCustoms(jsonSection.optJSONArray("elements")));
            sections.add(section);
        }

        form.setSections(sections);

        form.setToolbar(getToolBarGeneral(jsonForm.optJSONArray("toolbar")));

        return form;
    }

    private ArrayList<ViewGeneral> getViewCustoms(JSONArray elements){

        ArrayList<ViewGeneral> viewGenerals = new ArrayList<>();
        for(int i = 0;i<elements.length();i++){
            JSONObject jsonElement = elements.optJSONObject(i);

            ViewCustom viewCustom = new ViewCustom();
            viewCustom.addAttributesOfJson(jsonElement);

            viewCustom.setValues(getValues(jsonElement.optJSONArray("values")));
            viewGenerals.add(viewCustom);
        }

        return  viewGenerals;
    }

    private ArrayList<ObjectGeneral> getValues(JSONArray jsonValues){

        ArrayList<ObjectGeneral> values = new ArrayList<>();
        if(jsonValues!=null) {
            for (int i = 0; i < jsonValues.length(); i++) {
                JSONObject jsonValue = jsonValues.optJSONObject(i);

                ObjectGeneral objectGeneral = new ObjectGeneral();
                objectGeneral.addAttributesOfJson(jsonValue,true);
                values.add(objectGeneral);
            }
        }

        return values;
    }

    private MenuGeneral getToolBarGeneral(JSONArray jsonArrayToolbar){


        MenuGeneral toolbarGeneral = new MenuGeneral();
        for(int i = 0;i<jsonArrayToolbar.length();i++){

            JSONObject jsonToolbar = jsonArrayToolbar.optJSONObject(i);
            MenuGeneral menuGeneral = new MenuGeneral();
            menuGeneral.addAttributesOfJson(jsonToolbar);
            menuGeneral.setEvent(getEvent(jsonToolbar.optJSONObject("event")));
            toolbarGeneral.addMenuGeneral(menuGeneral);


        }

        return toolbarGeneral;

    }
}
