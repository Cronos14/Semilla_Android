package com.masnegocio.semilla;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.masnegocio.semilla.dialogs.FormDialog;
import com.masnegocio.semilla.fragments.CatalogFragment;
import com.masnegocio.semilla.fragments.GeneralFragment;
import com.masnegocio.semilla.models.Event;
import com.masnegocio.semilla.models.Form;
import com.masnegocio.semilla.models.MenuGeneral;
import com.masnegocio.semilla.models.Navigation;
import com.masnegocio.semilla.models.Pantalla;
import com.masnegocio.semilla.models.Tema;
import com.masnegocio.semilla.models.ViewGeneral;
import com.masnegocio.semilla.tasks.Tarea;
import com.masnegocio.semilla.tasks.TareaUI;
import com.masnegocio.semilla.utils.Singleton;
import com.masnegocio.semilla.utils.Utils;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Tema tema;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


    }

    private void generarTema() {

        if (tema instanceof Navigation) {
            toolbar = new Toolbar(this);
            String toolbarLabel = tema.getToolbar().getAttributes().get("label").toString();
            Log.e("MainAcitivty", "toolbar" + toolbarLabel);
            toolbar.setTitle(toolbarLabel);
            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));


            setSupportActionBar(toolbar);

            if (toolbar != null) {
                drawer = new DrawerLayout(this);

                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.open, R.string.close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = new NavigationView(this);
                DrawerLayout.LayoutParams paramsNav = new DrawerLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.MATCH_PARENT);
                paramsNav.gravity = Gravity.START;
                navigationView.setLayoutParams(paramsNav);
                navigationView.setNavigationItemSelectedListener(this);

                LinearLayout linearHeader = new LinearLayout(this);
                linearHeader.setOrientation(LinearLayout.VERTICAL);
                linearHeader.setGravity(Gravity.BOTTOM);
                if (Build.VERSION.SDK_INT < 16) {
                    linearHeader.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_primary));
                }else{
                    linearHeader.setBackground(getResources().getDrawable(R.drawable.background_primary));
                }

                LinearLayout.LayoutParams paramsHeader = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.nav_header_height));
                linearHeader.setLayoutParams(paramsHeader);

                ImageView imageView = new ImageView(this);
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
                LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(paramsImg);
                TextView textHeader = new TextView(this);
                textHeader.setText("E-xpenses Cloud");
                textHeader.setTextColor(getResources().getColor(android.R.color.white));
                //textHeader.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.ic_dialog_email));
                linearHeader.addView(imageView);
                linearHeader.addView(textHeader);
                LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int margin = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                paramsTv.setMargins(margin, margin, margin, margin);
                textHeader.setLayoutParams(paramsTv);

                navigationView.addHeaderView(linearHeader);


                for (ViewGeneral viewGeneral1 : ((Navigation) tema).getMenu().getViewGenerals()) {
                    //Log.e("MainAcitivty", "navigation menu: " + viewGeneral1.getLabel());
                    Menu menu = navigationView.getMenu();

                    //menu.add(Menu.NONE, Menu.FIRST, Menu.NONE,viewGeneral1.getLabel());
                    String label = viewGeneral1.getAttributes().get("label").toString();
                    SubMenu subMenu = menu.addSubMenu(label);
                    if (viewGeneral1.getViewGenerals() != null) {
                        addSubMenus(menu,subMenu, viewGeneral1);
                    }else{
                        menu.add(label);
                    }


                }

                //contenido de la vista principal
                frameLayout = new FrameLayout(this);
                FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                frameLayout.setLayoutParams(frameParams);
                frameLayout.setId(Utils.generateViewId());

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 10);
                linearLayout.setLayoutParams(params);

                linearLayout.addView(toolbar, 0);
                linearLayout.addView(frameLayout);

                drawer.addView(linearLayout);

                drawer.addView(navigationView);

                setContentView(drawer);


            } else {
                Log.e("MainActivity", "Se necesita toolbar para agregar un navigation");
            }


        }


    }

    private void addSubMenus(Menu menu, SubMenu subMenu, ViewGeneral viewGeneral) {

        for (ViewGeneral subMenuCustom : viewGeneral.getViewGenerals()) {
            if (subMenuCustom.getViewGenerals() != null) {
                String label = subMenuCustom.getAttributes().get("label").toString();
                Log.e("MainActivity","subHijosMenuCustom: "+label);

                SubMenu aux = menu.addSubMenu(label);
                addSubMenus(menu,aux, subMenuCustom);
            } else {
                String label = viewGeneral.getAttributes().get("label").toString();
                String labelSubmenu = subMenuCustom.getAttributes().get("label").toString();
                Log.e("MainActivity","parent: "+ label+" subMenuCustom: "+labelSubmenu);

                subMenu.add(labelSubmenu).setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
            }

        }
    }

    private void intanciarVista(Pantalla pantalla) {

        GeneralFragment fragment = GeneralFragment.newInstance(pantalla);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(frameLayout.getId(), fragment).commit();

    }

    private void intanciarVista(Form form) {

        GeneralFragment fragment = GeneralFragment.newInstance(form);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(frameLayout.getId(), fragment).commit();

    }

    private void replaceView(int id, String tipoVentana) {
        Pantalla aux = new Pantalla();
        aux.setId(id);
        int index = tema.getPantallas().indexOf(aux);
        if (index >= 0) {
            Pantalla pantalla = tema.getPantallas().get(index);

            if (tipoVentana.equalsIgnoreCase("activity")) {

            } else if (tipoVentana.equalsIgnoreCase("dialog")) {

                FormDialog formDialog = FormDialog.newInstance(pantalla, pantalla.getNombre(), 2);
                formDialog.setOnClickListener(new FormDialog.OnClickListener() {
                    @Override
                    public void onClick(Object object) {

                    }
                });
                formDialog.show(getSupportFragmentManager(), "dialog");

            } else {
                GeneralFragment fragment = GeneralFragment.newInstance(pantalla);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(frameLayout.getId(), fragment).commit();
                toolbar.setTitle(pantalla.getNombre());
            }


        }

    }

    private void replaceView(String title, Event event) {

        if(event!=null && event.getAttributes()!=null && event.getAttributes().get("url") != null) {
            String url = (String)event.getAttributes().get("url");
            if (url.contains("form")) {
                GeneralFragment fragment = GeneralFragment.newInstance(event);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(frameLayout.getId(), fragment).commit();
                String titleAux = tema.getForms().get(0).getAttributes().get("title").toString();
                toolbar.setTitle(titleAux);
            } else if (url.contains("catalog")) {

                CatalogFragment fragment = null;
                boolean existe = false;

                //descomentar para dejar en memoria el JSON (problema con el fab button)
                /*if(tema.getCatalogs()!=null && !tema.getCatalogs().isEmpty()) {
                    for (Catalog cat : tema.getCatalogs()) {
                        if (cat.getTitle().equalsIgnoreCase(title)) {
                            existe = true;
                            fragment = CatalogFragment.newInstance(title, cat);
                            break;
                        }
                    }
                }*/

                if(!existe){
                    fragment = CatalogFragment.newInstance(title,event);
                }


                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(frameLayout.getId(), fragment).commit();
                //String titleAux = tema.getForms().get(0).getAttributes().get("title").toString();
                //toolbar.setTitle(titleAux);
            }
        }else{
            Log.e("MainActivity","event null");
        }


    }

    @Override
    protected void onResume() {
        super.onResume();


        if (Singleton.getInstance().getTema() == null) {
            TareaUI tareaUI = new TareaUI(this,"Generando UI");
            tareaUI.setOnPostExecuteListener(new Tarea.OnPostExecuteListener() {

                @Override
                public void onPostExecute(Object object) {

                    if (object instanceof Tema) {
                        Log.e("MainActivty", "generando ui de ws");
                        tema = (Tema) object;
                        Singleton.getInstance().setTema(tema);
                        generarTema();
                        if (tema.getPantallas() != null && !tema.getPantallas().isEmpty()) {
                            intanciarVista(tema.getPantallas().get(0));
                        }else if(tema.getForms() != null && !tema.getForms().isEmpty()){
                            intanciarVista(tema.getForms().get(0));
                        }

                    }

                }
            });
            tareaUI.execute();
        } else {
            Log.e("MainActivty", "generando ui de singleton");
            tema = Singleton.getInstance().getTema();
            generarTema();
            if (tema.getPantallas() != null && !tema.getPantallas().isEmpty()) {
                intanciarVista(tema.getPantallas().get(0));
            }else if(tema.getForms() != null && !tema.getForms().isEmpty()){
                intanciarVista(tema.getForms().get(0));
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (tema.getToolbar().getViewGenerals() != null) {
            menu.add(Menu.NONE, 1, Menu.NONE, "Refrescar");
            for (ViewGeneral viewGeneral : tema.getToolbar().getViewGenerals()) {
                String control = viewGeneral.getAttributes().get("control").toString();
                if (control.equalsIgnoreCase("menu")) {
                    String label = viewGeneral.getAttributes().get("label").toString();
                    menu.add(Menu.NONE, 1, Menu.NONE, label);
                }
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        Log.e("MainActivty", "refrescar");
        if (item.getTitle().equals("Refrescar")) {

           // DialogFragment newFragment = new DatePickerFragment();
           // newFragment.show(getFragmentManager(), "datePicker");

            TareaUI tareaUI = new TareaUI(this,"Generando UI");
            tareaUI.setOnPostExecuteListener(new Tarea.OnPostExecuteListener() {

                @Override
                public void onPostExecute(Object object) {

                    if (object instanceof Tema) {
                        Log.e("MainActivty", "generando ui de ws");
                        tema = (Tema) object;
                        Singleton.getInstance().setTema(tema);
                        generarTema();
                        if (tema.getPantallas() != null && !tema.getPantallas().isEmpty()) {
                            intanciarVista(tema.getPantallas().get(0));
                        }else if(tema.getForms() != null && !tema.getForms().isEmpty()){
                            intanciarVista(tema.getForms().get(0));
                        }

                    }

                }
            });
            tareaUI.execute();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Navigation nav = (Navigation) tema;
        for (ViewGeneral viewGeneral1 : nav.getMenu().getViewGenerals()) {
            if (viewGeneral1 instanceof MenuGeneral) {
                MenuGeneral menuGeneral = (MenuGeneral) viewGeneral1;


                if(menuGeneral.getViewGenerals()!=null) {
                    for (ViewGeneral viewGeneral : menuGeneral.getViewGenerals()) {

                        if (viewGeneral instanceof MenuGeneral) {
                            MenuGeneral subMenu = (MenuGeneral)viewGeneral;
                            String labelViewGeneral = viewGeneral.getAttributes().get("label").toString();
                            if (labelViewGeneral.equalsIgnoreCase(item.getTitle().toString())) {

                                Log.e("MainActivity", "sub menuequals: " + labelViewGeneral+" == "+item.getTitle().toString());
                                //replaceView(menuGeneral.getAccion(), menuGeneral.getTipoVentana());
                                String labelSubMenu = subMenu.getAttributes().get("label").toString();
                                replaceView(labelSubMenu,subMenu.getEvent());

                                drawer.closeDrawer(GravityCompat.START);
                                return true;
                            }
                        }

                    }
                }else{
                    String labelMenuGeneral = menuGeneral.getAttributes().get("label").toString();
                    Log.e("MainActivity", "no tiene submenus: " + labelMenuGeneral);
                }

                String labelViewGeneral1 = viewGeneral1.getAttributes().get("label").toString();
                if (labelViewGeneral1.equalsIgnoreCase(item.getTitle().toString())) {

                    Log.e("MainActivity", "equals: " + labelViewGeneral1+" == "+item.getTitle().toString());
                    String labelMenuGeneral = menuGeneral.getAttributes().get("label").toString();
                    replaceView(labelMenuGeneral,menuGeneral.getEvent());

                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }else{
                    Log.e("MainActivity", "no equals: " + labelViewGeneral1+" != "+item.getTitle().toString());
                }
            }else{
                Log.e("MainActivity", "no instanceof MenuGeneral");
            }


        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
