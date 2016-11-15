package com.masnegocio.semilla.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.masnegocio.semilla.R;
import com.masnegocio.semilla.adapters.CustomAdapter;
import com.masnegocio.semilla.adapters.holders.CustomViewHolder;
import com.masnegocio.semilla.decorations.DividerItemDecoration;
import com.masnegocio.semilla.dialogs.FormDialog;
import com.masnegocio.semilla.dialogs.GeneralDialog;
import com.masnegocio.semilla.dialogs.SingleChoiceDialog;
import com.masnegocio.semilla.models.Action;
import com.masnegocio.semilla.models.Catalog;
import com.masnegocio.semilla.models.DataRow;
import com.masnegocio.semilla.models.Event;
import com.masnegocio.semilla.models.Form;
import com.masnegocio.semilla.models.Row;
import com.masnegocio.semilla.tasks.Tarea;
import com.masnegocio.semilla.tasks.TareaCatalog;
import com.masnegocio.semilla.tasks.TareaForm;
import com.masnegocio.semilla.utils.Compare;
import com.masnegocio.semilla.utils.Singleton;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Tadeo-developer on 26/10/16.
 */

public class CatalogFragment extends Fragment implements CustomViewHolder.OnClickItemIconListener{
    private RecyclerView recyclerView;
    private ArrayList<Row> data;
    private ArrayList<Row> dataAux;
    private CustomAdapter<Row> adapter;
    private FloatingActionButton add;
    private Event event;
    private Catalog catalog;
    private String title;



    public static CatalogFragment newInstance(String title, Event event) {

        Bundle args = new Bundle();
        args.putSerializable("event", event);
        args.putString("title",title);
        CatalogFragment fragment = new CatalogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CatalogFragment newInstance(String title, Catalog catalog) {

        Bundle args = new Bundle();
        args.putSerializable("catalog", catalog);
        args.putString("title",title);
        CatalogFragment fragment = new CatalogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.content_list,container,false);

        if(getArguments()!=null){
            event = (Event) getArguments().getSerializable("event");
            title = getArguments().getString("title");
            catalog = (Catalog) getArguments().getSerializable("catalog");
        }

        Log.e("CatalogFragment","onCreateView: "+title);


        AppCompatActivity parentActivity = (AppCompatActivity)getActivity();
        if(parentActivity.getSupportActionBar()!=null) {
            parentActivity.getSupportActionBar().setTitle(title);
        }

        add = (FloatingActionButton)view.findViewById(R.id.fab);

        recyclerView = (RecyclerView)view.findViewById(R.id.general_recycler);
        recyclerView.setHasFixedSize(true);

        data = new ArrayList<>();
        dataAux = new ArrayList<>();

        adapter = new CustomAdapter<>(data);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Row dataSelected = data.get(recyclerView.getChildAdapterPosition(view));

                for(Action action : dataSelected.getActions()){
                    Log.e("CatalogFragment","actions: "+action.getAttributes().get("tooltip"));
                }

            }
        });



        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(catalog.getToolbar().getEvent()!=null && catalog.getToolbar().getEvent().getParameters()!=null) {
                    TareaForm tareaForm = new TareaForm((AppCompatActivity) getActivity(), "Generando form...");
                    tareaForm.setParameters(catalog.getToolbar().getEvent().getParameters().getAttributes());
                    tareaForm.setOnPostExecuteListener(new Tarea.OnPostExecuteListener() {
                        @Override
                        public void onPostExecute(Object object) {
                            Form form = (Form) object;
                            FormDialog formDialog = FormDialog.newInstance(form, "Crear", 2);
                            formDialog.setOnClickListener(new FormDialog.OnClickListener() {
                                @Override
                                public void onClick(Object object) {

                                }
                            });
                            formDialog.show(getFragmentManager(), "dialog");
                        }
                    });
                    tareaForm.execute();
                }



            }
        });

        setRetainInstance(true);
        setHasOptionsMenu(true);

        CustomViewHolder.onClickItemIconListener = this;


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(catalog!=null){

            data.addAll(catalog.getTable().getRows());
            dataAux.addAll(catalog.getTable().getRows());
            adapter.notifyDataSetChanged();

        }else {


            TareaCatalog tareaCatalog = new TareaCatalog((AppCompatActivity) getActivity(), "Generando Catalogo...");
            tareaCatalog.setParameters(event.getParameters().getAttributes());
            tareaCatalog.setOnPostExecuteListener(new Tarea.OnPostExecuteListener() {
                @Override
                public void onPostExecute(Object object) {
                    catalog = (Catalog) object;
                    catalog.setTitle(title);
                    Singleton.getInstance().getTema().addCatalog(catalog);
                    data.addAll(catalog.getTable().getRows());
                    dataAux.addAll(catalog.getTable().getRows());
                    adapter.notifyDataSetChanged();

                }
            });

            tareaCatalog.execute();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        Log.e("CatalogFragment","onCreateOptionsMenu: "+title);
        menu.clear();

        //opciones generales
        menu.add(Menu.NONE, 1, Menu.NONE, "Ordenar");

        //opcion buscar
        SearchView searchView = new SearchView(getContext());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("CatalogFragment","newText: "+newText);

                searchRows(newText);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("CatalogFragment","query: "+query);
                searchRows(query);
                return true;
            }


        });
        menu.add(Menu.NONE, 2, Menu.NONE, "Buscar").setActionView(searchView).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Log.e("CatalogFragment","onOptionsItemSelected: "+title);
        if (item.getTitle().equals("Ordenar")) {

            Log.e("CatalogFragment","Click Ordenar");


            SingleChoiceDialog singleChoiceDialog = SingleChoiceDialog.newInstance("Ordenar por...",1,catalog.getTable().getHeaders());
            singleChoiceDialog.setOnClickListener(new SingleChoiceDialog.OnClickListener() {
                @Override
                public void onClick(Object object) {

                    Collections.sort(data, new Compare((String)object));

                    adapter.notifyDataSetChanged();

                }
            });

            singleChoiceDialog.show(getFragmentManager(),"choice");

        }

        return super.onOptionsItemSelected(item);
    }

    private void searchRows(String query){
        if(query.isEmpty()){
            data.clear();
            data.addAll(dataAux);

            adapter.notifyDataSetChanged();

        }else{

            ArrayList<Row> rowsFilter = new ArrayList<>();
            for(Row row : dataAux){
                for(DataRow dataRow : row.getFields()){
                    String dataLower = dataRow.getData().toString().toLowerCase();
                    String newTextLower = query.toLowerCase();
                    if(dataLower.contains(newTextLower)){
                        rowsFilter.add(row);
                    }
                }
            }

            data.clear();
            data.addAll(rowsFilter);

            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClickItemIcon(Row row, Action action) {

        String idAction = (String)action.getAttributes().get("id");
        if(idAction.equalsIgnoreCase(CustomViewHolder.ID_BTN_EDIT)){

            TareaForm tareaForm = new TareaForm((AppCompatActivity) getActivity(), "Generando form...");
            tareaForm.setParameters(action.getEvent().getParameters().getAttributes());
            tareaForm.setOnPostExecuteListener(new Tarea.OnPostExecuteListener() {
                @Override
                public void onPostExecute(Object object) {
                    Form form = (Form) object;
                    FormDialog formDialog = FormDialog.newInstance(form, "Editar", 2);
                    formDialog.setOnClickListener(new FormDialog.OnClickListener() {
                        @Override
                        public void onClick(Object object) {

                        }
                    });
                    formDialog.show(getFragmentManager(), "dialog");
                }
            });
            tareaForm.execute();


        }else if(idAction.equalsIgnoreCase(CustomViewHolder.ID_BTN_DELETE)){
            GeneralDialog generalDialog = GeneralDialog.newInstance("Borrar",2,"Seguro que desea borrar este registro");
            generalDialog.setOnClickListener(new GeneralDialog.OnClickListener() {
                @Override
                public void onClick(Object object) {

                }
            });
            generalDialog.show(getFragmentManager(),"");
        }else{

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("CatalogFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("CatalogFragment","onStop");

        //if(searchView!=null)
        //    searchView.setOnQueryTextListener(null);
        CustomViewHolder.onClickItemIconListener = null;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("CatalogFragment","onDestroy");




    }
}
