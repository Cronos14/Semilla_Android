package com.masnegocio.semilla.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

/**
 * Created by Tadeo-developer on 10/09/16.
 */
public abstract class Tarea extends AsyncTask<String,Integer,JSONObject> {


    protected AppCompatActivity appCompatActivity;
    protected ProgressDialog progressDialog;
    protected String title;

    public interface OnPostExecuteListener{
        void onPostExecute(Object object);
    }

    private OnPostExecuteListener onPostExecuteListener;

    protected Tarea(AppCompatActivity appCompatActivity, String title){
        this.appCompatActivity = appCompatActivity;
        this.title = title;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(appCompatActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(title);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }



    public void setOnPostExecuteListener(OnPostExecuteListener onPostExecuteListener){
        this.onPostExecuteListener = onPostExecuteListener;
    }

    public OnPostExecuteListener getOnPostExcecuteListener(){
        return this.onPostExecuteListener;
    }

}
