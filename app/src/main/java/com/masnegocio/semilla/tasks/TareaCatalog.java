package com.masnegocio.semilla.tasks;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.masnegocio.semilla.models.Action;
import com.masnegocio.semilla.models.Catalog;
import com.masnegocio.semilla.models.DataRow;
import com.masnegocio.semilla.models.MenuGeneral;
import com.masnegocio.semilla.models.ObjectGeneral;
import com.masnegocio.semilla.models.Row;
import com.masnegocio.semilla.models.Table;
import com.masnegocio.semilla.ws.WebServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tadeo-developer on 25/10/16.
 */

public class TareaCatalog extends TareaGeneral {

    public TareaCatalog(AppCompatActivity appCompatActivity, String title) {
        super(appCompatActivity, title);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return WebServices.serviceCatalog(parameters);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        if (jsonObject != null) {


            Catalog catalog = instanceCatalog(jsonObject.optJSONObject("catalog"));

            if (getOnPostExcecuteListener() != null) {
                getOnPostExcecuteListener().onPostExecute(catalog);
            }

        } else {
            Log.e("TareaUI", "jsonObject es null");
        }
    }

    private Catalog instanceCatalog(JSONObject jsonCatalog){

        Catalog catalog = new Catalog();

        catalog.addAttributesOfJson(jsonCatalog);

        catalog.setToolbar(getToolBarGeneral(jsonCatalog.optJSONArray("toolbar")));
        catalog.setTable(getTable(jsonCatalog.optJSONObject("table")));
        catalog.setRootEvent(getEvent(jsonCatalog.optJSONObject("root_event")));

        return catalog;
    }

    private MenuGeneral getToolBarGeneral(JSONArray jsonArrayToolbar){


        for(int i = 0;i<jsonArrayToolbar.length();i++){

            JSONObject jsonToolbar = jsonArrayToolbar.optJSONObject(i);
            MenuGeneral toolbarGeneral = new MenuGeneral();
            toolbarGeneral.addAttributesOfJson(jsonToolbar);
            toolbarGeneral.setEvent(getEvent(jsonToolbar.optJSONObject("event")));


            return toolbarGeneral;
        }

        return null;

    }

    private Table getTable(JSONObject jsonTable){

        Table table = new Table();

        table.addAttributesOfJson(jsonTable);

        JSONArray jsonArray = jsonTable.optJSONArray("headers");

        ArrayList<ObjectGeneral> headers = new ArrayList<>();

        for(int i = 0;i<jsonArray.length();i++){
            JSONObject jsonHeader = jsonArray.optJSONObject(i);

            ObjectGeneral objectGeneral = new ObjectGeneral();
            objectGeneral.addAttributesOfJson(jsonHeader);

            //posicion del row hardcodeado descomentar relative layout en CustomViewHolder

            /*if(i==1){
                objectGeneral.addAttribute("toRightOf","i_cc_id");
            }else if(i==2){
                objectGeneral.addAttribute("toBelowOf","i_cc_id");
            }else if(i==3){
                objectGeneral.addAttribute("toRightOf","v_cc_centro_costos");
                objectGeneral.addAttribute("toBelowOf","v_cc_responsable");
            }else if(i==4){
                objectGeneral.addAttribute("toBelowOf","v_cc_responsable");
            }*/




            headers.add(objectGeneral);
        }

        table.setHeaders(headers);

        JSONArray jsonArrayData = jsonTable.optJSONArray("data");

        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0;i<jsonArrayData.length();i++){
            JSONObject jsonData = jsonArrayData.optJSONObject(i);


            Row row = new Row();

            //agrega cada campo con su respectivo header
            ArrayList<DataRow> fields = new ArrayList<>();
            for(int j = 0;j<table.getHeaders().size();j++){
                ObjectGeneral header = table.getHeaders().get(j);
                Object data = jsonData.opt(header.getAttributes().get("field").toString());
                DataRow dataRow = new DataRow();
                dataRow.setHeader(header);
                dataRow.setData(data);
                fields.add(dataRow);
            }

            row.setFields(fields);


            JSONArray jsonArrayActions = jsonData.optJSONArray("actions");
            if(jsonArrayActions!=null) {
                ArrayList<Action> actions = new ArrayList<>();
                for (int j = 0; j < jsonArrayActions.length(); j++) {
                    JSONObject jsonAction = jsonArrayActions.optJSONObject(j);

                    if(jsonAction!=null) {
                        Action action = new Action();
                        action.addAttributesOfJson(jsonAction);
                        action.setEvent(getEvent(jsonAction.optJSONObject("event")));
                        actions.add(action);
                    }
                }
                row.setActions(actions);
            }

            rows.add(row);


            /*
            LinkedHashMap<String,Object> fields = new LinkedHashMap<>();
            for(Header header : table.getHeaders()){
                String key = header.getLabel();
                Object value = jsonData.opt(header.getField());
                fields.put(key,value);
            }*/

        }

        table.setRows(rows);

        return table;
    }


}
