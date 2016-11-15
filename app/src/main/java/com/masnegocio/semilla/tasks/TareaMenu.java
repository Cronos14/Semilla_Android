package com.masnegocio.semilla.tasks;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.masnegocio.semilla.models.MenuGeneral;
import com.masnegocio.semilla.ws.WebServices;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Tadeo-developer on 21/10/16.
 */

public class TareaMenu extends TareaGeneral {

    public TareaMenu(AppCompatActivity appCompatActivity, String title) {
        super(appCompatActivity, title);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return WebServices.serviceMenu(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);


        if (jsonObject != null) {


            MenuGeneral menu = instanceMenu(jsonObject.optJSONArray("menu"));

            if (getOnPostExcecuteListener() != null) {
                getOnPostExcecuteListener().onPostExecute(menu);
            }

        } else {
            Log.e("TareaUI", "jsonObject es null");
        }
    }

    private MenuGeneral instanceMenu(JSONArray menu) {

        MenuGeneral menuGeneral = new MenuGeneral();
        for (int i = 0; i < menu.length(); i++) {
            menuGeneral.addMenuGeneral(instanceItemMenu(menu.optJSONObject(i)));
        }

        return menuGeneral;
    }

    private MenuGeneral instanceItemMenu(JSONObject menuJson) {


        MenuGeneral menuGeneral = new MenuGeneral();
        menuGeneral.addAttributesOfJson(menuJson);
        menuGeneral.setEvent(getEvent(menuJson.optJSONObject("event")));

        JSONArray menuOptions = menuJson.optJSONArray("options");
        if (menuOptions != null) {
            for (int i = 0; i < menuOptions.length(); i++) {
                menuGeneral.addMenuGeneral(instanceItemMenu(menuOptions.optJSONObject(i)));
            }
        }

        return menuGeneral;
    }



}
