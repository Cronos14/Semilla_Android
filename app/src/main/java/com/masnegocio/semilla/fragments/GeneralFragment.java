package com.masnegocio.semilla.fragments;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.masnegocio.semilla.R;
import com.masnegocio.semilla.models.Event;
import com.masnegocio.semilla.models.Form;
import com.masnegocio.semilla.models.Pantalla;
import com.masnegocio.semilla.models.ViewGeneral;
import com.masnegocio.semilla.tasks.Tarea;
import com.masnegocio.semilla.tasks.TareaForm;
import com.masnegocio.semilla.utils.Singleton;
import com.masnegocio.semilla.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralFragment extends Fragment {


    private Pantalla pantalla;
    private Form form;
    private Event event;

    public GeneralFragment() {
        // Required empty public constructor
    }

    public static GeneralFragment newInstance(Pantalla pantalla) {

        Bundle args = new Bundle();

        args.putSerializable("form", pantalla);
        GeneralFragment fragment = new GeneralFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static GeneralFragment newInstance(Form form) {

        Bundle args = new Bundle();

        args.putSerializable("form", form);
        GeneralFragment fragment = new GeneralFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static GeneralFragment newInstance(Event event) {

        Bundle args = new Bundle();

        args.putSerializable("form", event);
        GeneralFragment fragment = new GeneralFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        setHasOptionsMenu(true);
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
        params.setMargins(margin,margin,margin,margin);
        linearLayout.setLayoutParams(params);

        if(getArguments()!=null){
            if(getArguments().getSerializable("form") instanceof Pantalla){
                pantalla = (Pantalla) getArguments().getSerializable("form");
            }else if(getArguments().getSerializable("form") instanceof Form){
                form = (Form) getArguments().getSerializable("form");
            }else if(getArguments().getSerializable("form") instanceof Event){
                TareaForm tareaForm = new TareaForm((AppCompatActivity)getActivity(),"Generando Form...");
                tareaForm.setOnPostExecuteListener(new Tarea.OnPostExecuteListener() {
                    @Override
                    public void onPostExecute(Object object) {
                        form = (Form)object;
                        linearLayout.addView(Utils.generateForm(getActivity(), form));
                    }
                });
                tareaForm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

        }


        if(pantalla!=null){
            linearLayout.addView(Utils.generateForm(getActivity(), pantalla));
        }else if(form != null){
            linearLayout.addView(Utils.generateForm(getActivity(), form));
        }


        return linearLayout;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        if (Singleton.getInstance().getTema().getToolbar().getViewGenerals() != null) {
            menu.add(Menu.NONE, 1, Menu.NONE, "Refrescar");
            for (ViewGeneral viewGeneral : Singleton.getInstance().getTema().getToolbar().getViewGenerals()) {

                String control = viewGeneral.getAttributes().get("control").toString();
                String label = viewGeneral.getAttributes().get("label").toString();

                if (control.equalsIgnoreCase("menu")) {
                    menu.add(Menu.NONE, 1, Menu.NONE, label);
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("CatalogFragment","diste click al item");
        if (item.getTitle().equals("Tramites")) {


        }

        return super.onOptionsItemSelected(item);
    }

}
