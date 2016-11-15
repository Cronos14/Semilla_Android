package com.masnegocio.semilla.tasks;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.masnegocio.semilla.models.MenuGeneral;
import com.masnegocio.semilla.models.Navigation;
import com.masnegocio.semilla.models.Tema;
import com.masnegocio.semilla.models.ViewGeneral;
import com.masnegocio.semilla.ws.WebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 10/09/16.
 */
public class TareaUI extends Tarea {

    public TareaUI(AppCompatActivity appCompatActivity,String title) {
        super(appCompatActivity,title);
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
        return WebServices.serviceUI();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);



        if (jsonObject != null) {

            //int code = jsonObject.optInt("code");

            //if(code==1){


            final JSONObject temaJson = jsonObject.optJSONObject("tema");
            final JSONObject toolbarJson = temaJson.optJSONObject("toolbar");
            final JSONArray pantallasJson = temaJson.optJSONArray("pantallas");

            /*final Tema navigation = instanceTema(temaJson);
            navigation.setToolbar(instanceToolbar(toolbarJson));
            //navigation.setPantallas(instanceScreens(pantallasJson));

            if (getOnPostExcecuteListener() != null) {
                getOnPostExcecuteListener().onPostExecute(navigation);
            }

            //Form

            TareaForm tareaForm = new TareaForm(appCompatActivity,"Generando Form...");
            tareaForm.setOnPostExecuteListener(new OnPostExecuteListener() {
                @Override
                public void onPostExecute(Object object) {
                    navigation.addForm((Form)object);
                    if (getOnPostExcecuteListener() != null) {
                        getOnPostExcecuteListener().onPostExecute(navigation);
                    }
                }
            });
            tareaForm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

            //Menu MN

            TareaMenu tareaMenu = new TareaMenu(appCompatActivity,"Generando Menu...");
            tareaMenu.setOnPostExecuteListener(new OnPostExecuteListener() {
                @Override
                public void onPostExecute(Object object) {

                    final Navigation navigation = new Navigation();

                    navigation.setMenu((MenuGeneral) object);
                    ViewGeneral toolbar = new ViewGeneral();
                    toolbar.addAttribute("control","toolbar");
                    toolbar.addAttribute("label","E-xpenses");
                    navigation.setToolbar(toolbar);
                    //navigation.setPantallas(instanceScreens(pantallasJson));


                    if (getOnPostExcecuteListener() != null) {
                        getOnPostExcecuteListener().onPostExecute(navigation);
                    }

                    //Form

                    /*TareaForm tareaForm = new TareaForm(appCompatActivity,"Generando Form...");
                    tareaForm.setOnPostExecuteListener(new OnPostExecuteListener() {
                        @Override
                        public void onPostExecute(Object object) {
                            navigation.addForm((Form)object);
                            if (getOnPostExcecuteListener() != null) {
                                getOnPostExcecuteListener().onPostExecute(navigation);
                            }
                        }
                    });
                    tareaForm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/



                }
            });

            tareaMenu.execute("mnuExpensesSemillaV10","ADMINISTRADOR");




        } else {
            Log.e("TareaUI", "jsonObject es null");
        }
    }

    private Tema instanceTema(JSONObject temaJson) {

        Tema tema = null;
        JSONObject tipo = temaJson.optJSONObject("tipo");
        String control = tipo.optString("control");

        if (control.equalsIgnoreCase("navigation")) {
            tema = new Navigation();
            JSONArray opciones = tipo.optJSONArray("opciones");

            ArrayList<ViewGeneral> viewCustomsOpciones = new ArrayList<>();

            for (int i = 0; i < opciones.length(); i++) {
                JSONObject opcion = opciones.optJSONObject(i);
                MenuGeneral viewCustom = new MenuGeneral();
                viewCustom.addAttributesOfJson(opcion);
                viewCustomsOpciones.add(viewCustom);
            }

            ViewGeneral viewGeneral = new ViewGeneral();
            viewGeneral.addAttribute("control","navigation");
            viewGeneral.setViewGenerals(viewCustomsOpciones);
            ((Navigation) tema).setMenu(viewGeneral);
        }

        return tema;
    }


    private ViewGeneral instanceToolbar(JSONObject toolbarJson) {


        ViewGeneral toolbar = new ViewGeneral();
        toolbar.addAttribute("control","toolbar");
        toolbar.addAttribute("label","E-xpenses");
        String control = toolbarJson.optString("control");

        if (control.equalsIgnoreCase("toolbar")) {

            JSONArray opciones = toolbarJson.optJSONArray("opciones");

            ArrayList<ViewGeneral> viewCustomsOpciones = new ArrayList<>();

            for (int i = 0; i < opciones.length(); i++) {
                JSONObject opcion = opciones.optJSONObject(i);
                ViewGeneral viewGeneral = new ViewGeneral();
                viewGeneral.addAttributesOfJson(opcion);

                viewCustomsOpciones.add(viewGeneral);
            }

            toolbar.setViewGenerals(viewCustomsOpciones);
            return toolbar;
        }

        return null;


    }


}
