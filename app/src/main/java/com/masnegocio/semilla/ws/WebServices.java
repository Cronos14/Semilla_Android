package com.masnegocio.semilla.ws;

import android.util.Log;

import com.masnegocio.semilla.utils.Singleton;
import com.masnegocio.semilla.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Tadeo-developer on 10/09/16.
 */
public class WebServices {

    public static final String URL_SERVIDOR = "http://www.luctadeveloper.com/uidinamico/";
    public static final String URL_SERVIDOR_MN = "http://qa.e-xpenses-cloud.masnegocio.com/mn-radt-engine/builder/";
    public static final String UI = "prueba.php";
    public static final String MENU = "menu/build";
    public static final String CATALOG = "catalog/build";
    public static final String FORM = "form/build";
    public static final String JSON = "json_app.php";

    private static JSONObject jObj 	= null;
    private static String json 		= "";

    public static JSONObject serviceUI(){


        return request(URL_SERVIDOR+JSON,"",true,"POST");
    }

    public static JSONObject serviceMenu(String id, String rol){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("id",id);
            JSONArray jsonArray = new JSONArray();

            for(String rolAux : Singleton.getInstance().getUser().getRoles()){
                jsonArray.put(rolAux);
            }


            jsonObject.accumulate("roles",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return request(URL_SERVIDOR_MN+MENU,jsonObject.toString(),true,"POST");
    }

    public static JSONObject serviceCatalog(HashMap<String,Object> parameters){



        JSONObject jsonObject = new JSONObject();

        for (String key : parameters.keySet()) {
            try {
                jsonObject.accumulate(key,parameters.get(key));
                JSONArray jsonArray = new JSONArray();
                for(String rolAux : Singleton.getInstance().getUser().getRoles()){
                    jsonArray.put(rolAux);
                }
                jsonObject.accumulate("roles",jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*try {
            jsonObject.accumulate("id",parameters.getId());
            jsonObject.accumulate("mode",parameters.getMode());
            jsonObject.accumulate("i_clave",parameters.getClave());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        return request(URL_SERVIDOR_MN+CATALOG,jsonObject.toString(),true,"POST");
    }

    public static JSONObject serviceForm(HashMap<String,Object> parameters){

        if(parameters!=null) {
            JSONObject jsonObject = new JSONObject();
            try {

                for (String key : parameters.keySet()) {
                    jsonObject.accumulate(key, parameters.get(key));
                }

                jsonObject.accumulate("roles", Utils.getRoles());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return request(URL_SERVIDOR_MN+FORM,jsonObject.toString(),true,"POST");
        }else{
            return request(URL_SERVIDOR_MN+FORM,"",true,"POST");
        }


    }


    private static JSONObject request(String requestURL, String params, boolean contentTypeJson, HashMap<String,String> headers, String method){


        URL url;
        String response = "";
        try {
            Log.i("WebServices", "URL: "+requestURL);
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(method);

            //conn.setDoInput(true);
            //conn.setDoOutput(true);


            if(headers!=null){
                String txtHeaders = "[";
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    txtHeaders+=key+":"+value+",";
                    conn.setRequestProperty(key,value);

                }
                txtHeaders+="]";

                Log.i("WebServices", "headers: "+txtHeaders);
            }

            if(contentTypeJson)conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            Log.i("WebServices", "request: " + params.toString());

            if(method.equals("POST")){
                conn.setDoOutput(true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                bw.write(params);

                bw.flush();
                bw.close();
            }



            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_UNAUTHORIZED ||
                    responseCode == HttpsURLConnection.HTTP_CONFLICT) {

                BufferedReader br;
                if(responseCode == HttpsURLConnection.HTTP_OK){

                    br=new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                }else{
                    br=new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                }
                String line;
                while ((line=br.readLine()) != null) {
                    response+=line;
                }

            }else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            json = response;
            Log.i("WebServices ", "response txt: "+json);
            jObj = new JSONObject(json);
            Log.i("WebServices ", "response: " + jObj.toString());

        } catch (JSONException e) {

            try{
                Log.e("WebServices ","http json object exception: " + e);
                Log.e("WebServices ","convert to JsonArray: "+json);
                JSONArray jsonArray =  new JSONArray(json);
                JSONObject jsonObjectAux = new JSONObject();
                jsonObjectAux.accumulate("values",jsonArray);
                jObj = jsonObjectAux;
            }catch (JSONException e1){
                Log.e("WebServices ","http json object exception to Array: " + e);
                jObj = null;
            }


        }
        return jObj;
    }

    private static JSONObject request(String requestURL,
                                      String params, boolean contentTypeJson, String method) {

        return request(requestURL,params,contentTypeJson,null,method);


    }

}
