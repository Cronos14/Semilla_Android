package com.masnegocio.semilla.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.masnegocio.semilla.R;
import com.masnegocio.semilla.adapters.CustomAdapter;
import com.masnegocio.semilla.decorations.DividerItemDecoration;
import com.masnegocio.semilla.models.Action;
import com.masnegocio.semilla.models.Boton;
import com.masnegocio.semilla.models.Catalog;
import com.masnegocio.semilla.models.DataRow;
import com.masnegocio.semilla.models.Event;
import com.masnegocio.semilla.models.Form;
import com.masnegocio.semilla.models.MenuGeneral;
import com.masnegocio.semilla.models.ObjectGeneral;
import com.masnegocio.semilla.models.Pantalla;
import com.masnegocio.semilla.models.Row;
import com.masnegocio.semilla.models.Section;
import com.masnegocio.semilla.models.Table;
import com.masnegocio.semilla.models.ViewCustom;
import com.masnegocio.semilla.models.ViewGeneral;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Created by Tadeo-developer on 07/10/16.
 */

public class Utils {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static View generateForm(AppCompatActivity context, Pantalla pantalla) {

        //linearLayout.removeViewsInLayout(NUMBER_VIEWS_STATIC,linearLayout.getChildCount()-NUMBER_VIEWS_STATIC);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int margin = (int) context.getResources().getDimension(R.dimen.activity_vertical_margin);
        params.setMargins(margin,margin,margin,margin);
        linearLayout.setLayoutParams(params);

        LinearLayout.LayoutParams paramsViews = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (ViewGeneral viewGeneral : pantalla.getViewGenerals()) {
            String control = viewGeneral.getAttributes().get("control").toString();
            String label = viewGeneral.getAttributes().get("label").toString();
            if (control.equalsIgnoreCase("texto")) {
                TextView view = new TextView(context);
                view.setLayoutParams(paramsViews);
                view.setText(label);
                linearLayout.addView(view);
            } else if (control.equalsIgnoreCase("caja")) {
                EditText view = new EditText(context);
                view.setLayoutParams(paramsViews);
                view.setText(label);
                linearLayout.addView(view);
            } else if (control.equalsIgnoreCase("boton")) {
                Button view = new Button(context);
                view.setLayoutParams(paramsViews);
                view.setText(label);
                Boton boton = (Boton) viewGeneral;
                if(boton.getFondo()!=null) {
                    if (boton.getFondo().equalsIgnoreCase("primario")) {
                        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_selector_primary));
                    } else if (boton.getFondo().equalsIgnoreCase("positivo")) {
                        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_selector_green));
                    }else if (boton.getFondo().equalsIgnoreCase("negativo")) {
                        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_selector_red));
                    }
                    view.setTextColor(context.getResources().getColor(android.R.color.white));
                }
                linearLayout.addView(view);
            } else {
                Log.e("MainActivity", "No se encontro el tipo de vista: " + control);
            }

        }

        return linearLayout;


    }

    public static View generateForm(AppCompatActivity context, Form form) {

        //linearLayout.removeViewsInLayout(NUMBER_VIEWS_STATIC,linearLayout.getChildCount()-NUMBER_VIEWS_STATIC);

        LinearLayout.LayoutParams paramsParent = getParamsMatchWrap();
        int margin = (int) context.getResources().getDimension(R.dimen.activity_vertical_margin);
        paramsParent.setMargins(margin,margin,margin,margin);

        //Parent
        LinearLayout linearLayoutParent = new LinearLayout(context);
        linearLayoutParent.setLayoutParams(paramsParent);

        //Child
        LinearLayout linearLayoutChild = new LinearLayout(context);
        linearLayoutChild.setOrientation(LinearLayout.VERTICAL);
        linearLayoutChild.setLayoutParams(getParamsMatchWrap());


        LinearLayout.LayoutParams paramsScroll = getParamsMatchWrap();
        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(paramsScroll);
        scrollView.addView(linearLayoutChild);

        linearLayoutParent.addView(scrollView);

        LinearLayout.LayoutParams paramsViews = getParamsMatchWrap();

        for (Section section : form.getSections()) {

            String controlSection = (String)section.getAttributes().get("control");
            String labelSection = (String)section.getAttributes().get("title");

            LinearLayout.LayoutParams paramsSection = getParamsMatchWrap();
            margin = (int) context.getResources().getDimension(R.dimen.activity_vertical_margin);
            paramsSection.setMargins(0,margin,0,0);

            LinearLayout linearLayoutSection = new LinearLayout(context);
            linearLayoutSection.setOrientation(LinearLayout.VERTICAL);
            linearLayoutSection.setLayoutParams(paramsSection);

            //titulo de section
            TextView textView = new TextView(context);
            textView.setText(labelSection);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.text_section));
            textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            textView.setLayoutParams(paramsViews);

            Log.e("Utils","titulo Section: "+labelSection);

            linearLayoutSection.addView(textView);

            for(ViewGeneral viewGeneral : section.getViewGenerals()){

                String control = (String)viewGeneral.getAttributes().get("control");


                String label = (String)viewGeneral.getAttributes().get("label");

                /*if(viewGeneral.getAttributes().get("label")!=null){
                    label = viewGeneral.getAttributes().get("label").toString();
                }*/

                if(control.equalsIgnoreCase("subsection")){

                    //titulo subsection
                    LinearLayout.LayoutParams paramsSubSection = getParamsMatchWrap();
                    margin = (int) context.getResources().getDimension(R.dimen.activity_vertical_margin);
                    paramsSubSection.setMargins(0,margin,0,0);

                    Log.e("Utils","titulo subsection: "+label);
                    TextView textViewElement = new TextView(context);
                    textViewElement.setText(label);
                    textViewElement.setLayoutParams(paramsSubSection);
                    textViewElement.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.text_subsection));

                    //linea separadora
                    LinearLayout.LayoutParams paramsView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)context.getResources().getDimension(R.dimen.view_line));
                    View view = new View(context);
                    view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                    view.setLayoutParams(paramsView);

                    linearLayoutSection.addView(textViewElement);
                    linearLayoutSection.addView(view);

                }else if(control.equalsIgnoreCase("text") || control.equalsIgnoreCase("fecha")){

                    EditText editText = new EditText(context);
                    editText.setLayoutParams(paramsViews);

                    if(viewGeneral instanceof ViewCustom){
                        ViewCustom viewCustom = (ViewCustom)viewGeneral;

                        String labelViewCustom = (String)viewCustom.getAttributes().get("label");
                        Boolean editable = (Boolean) viewCustom.getAttributes().get("editable");
                        Integer maxSize = (Integer) viewCustom.getAttributes().get("maxSize");
                        String pattern = (String)viewCustom.getAttributes().get("pattern");
                        editText.setHint(labelViewCustom);

                        if(editable!=null && !editable){
                            editText.setEnabled(false);
                            //editText.setTag(editText.getKeyListener());
                            //editText.setKeyListener(null);
                        }

                        ArrayList<InputFilter> filters = new ArrayList<>();
                        if(maxSize!=null && maxSize>0){
                            Log.e("Utils","MaxSize maxSize: "+maxSize);
                            filters.add(new InputFilter.LengthFilter(maxSize));
                        }

                        if(pattern!=null && !pattern.isEmpty()){
                            final String patternAux = new String((Base64.decode(pattern, Base64.DEFAULT)));

                            Log.e("Utils","Pattern pattern: "+pattern);
                            InputFilter filter = new InputFilter() {
                                @Override
                                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                                    for (int i = start; i < end; ++i)
                                    {
                                        if (!Pattern.compile(patternAux).matcher(String.valueOf(source.charAt(i))).matches())
                                        {
                                            return "";
                                        }
                                    }

                                    return null;
                                }
                            };

                            //filters.add(filter);

                        }

                        InputFilter[] filtersArray = new InputFilter[filters.size()];
                        for(int i = 0;i<filters.size();i++){
                            filtersArray[i] = filters.get(i);
                        }


                        editText.setFilters(filtersArray);

                        for(ObjectGeneral value : viewCustom.getValues()){
                            Object valueAux = value.getAttributes().get("value");
                            editText.setText(valueAux.toString());
                        }

                    }

                    LinearLayout.LayoutParams paramsTitulos = getParamsMatchWrap();
                    paramsTitulos.setMargins(0,margin,0,0);

                    TextView textViewTitulo = new TextView(context);
                    textViewTitulo.setText(label);
                    textViewTitulo.setLayoutParams(paramsTitulos);

                    linearLayoutSection.addView(textViewTitulo);
                    linearLayoutSection.addView(editText);
                }else if(control.equalsIgnoreCase("options")){
                    RadioGroup rg = new RadioGroup(context);
                    rg.setOrientation(RadioGroup.HORIZONTAL);
                    rg.setLayoutParams(paramsViews);
                    //rg.setEnabled(false);


                    if(viewGeneral instanceof ViewCustom){
                        ViewCustom viewCustom = (ViewCustom)viewGeneral;
                        for(ObjectGeneral value : viewCustom.getValues()){

                            String labelValue = (String)value.getAttributes().get("label");
                            Boolean selectedValue = (Boolean)value.getAttributes().get("selected");
                            Boolean editable = (Boolean)viewCustom.getAttributes().get("editable");


                            RadioButton rbOption = new RadioButton(context);
                            rbOption.setId(generateViewId());
                            rbOption.setText(labelValue);
                            rbOption.setTextColor(Color.BLACK);
                            rbOption.setChecked(selectedValue);
                            if(editable!=null && !editable){
                                rbOption.setEnabled(false);
                            }

                            rg.addView(rbOption);
                            if(selectedValue!=null && selectedValue){
                                rg.check(rbOption.getId());
                            }

                        }
                    }

                    LinearLayout.LayoutParams paramsTitulos = getParamsMatchWrap();
                    paramsTitulos.setMargins(0,margin,0,0);

                    TextView textViewTitulo = new TextView(context);
                    textViewTitulo.setText(label);
                    textViewTitulo.setLayoutParams(paramsTitulos);

                    linearLayoutSection.addView(textViewTitulo);
                    linearLayoutSection.addView(rg);

                }else if(control.equalsIgnoreCase("multicheck")){

                    LinearLayout.LayoutParams paramsTitulos = getParamsMatchWrap();
                    paramsTitulos.setMargins(0,margin,0,0);

                    if(label!=null && !label.isEmpty()) {
                        TextView textViewTitulo = new TextView(context);
                        textViewTitulo.setText(label);
                        textViewTitulo.setLayoutParams(paramsTitulos);
                        linearLayoutSection.addView(textViewTitulo);
                    }

                    if(viewGeneral instanceof ViewCustom){
                        ViewCustom viewCustom = (ViewCustom)viewGeneral;
                        for(ObjectGeneral value : viewCustom.getValues()) {

                            String labelValue = (String)value.getAttributes().get("label");
                            Boolean selectedValue = (Boolean)value.getAttributes().get("selected");
                            Boolean editable = (Boolean)viewCustom.getAttributes().get("editable");

                            CheckBox checkBox = new CheckBox(context);
                            checkBox.setText(labelValue);
                            checkBox.setTextColor(Color.BLACK);
                            if(selectedValue!=null){
                                checkBox.setChecked(selectedValue);
                            }

                            checkBox.setId(generateViewId());
                            if(editable!=null && !editable){
                                checkBox.setEnabled(false);
                            }
                            linearLayoutSection.addView(checkBox);
                        }

                    }
                }else if(control.equalsIgnoreCase("list")){
                    LinearLayout.LayoutParams paramsTitulos = getParamsMatchWrap();
                    paramsTitulos.setMargins(0,margin,0,0);

                    TextView textViewTitulo = new TextView(context);
                    textViewTitulo.setText(label);
                    textViewTitulo.setLayoutParams(paramsTitulos);
                    linearLayoutSection.addView(textViewTitulo);

                    Spinner spinner = new Spinner(context);

                    final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                            context,android.R.layout.simple_dropdown_item_1line);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    spinner.setAdapter(spinnerArrayAdapter);

                    if(viewGeneral instanceof ViewCustom){
                        ViewCustom viewCustom = (ViewCustom)viewGeneral;

                        Boolean editable = (Boolean)viewCustom.getAttributes().get("editable");

                        int selected = 0;
                        for(int i = 0;i<viewCustom.getValues().size();i++){
                            ObjectGeneral value = viewCustom.getValues().get(i);
                            String labelValue = (String)value.getAttributes().get("label");
                            Boolean selectedValue = (Boolean)value.getAttributes().get("selected");
                            spinnerArrayAdapter.add(labelValue);
                            if(selectedValue!=null && selectedValue){
                                selected = i;
                            }
                        }
                        spinnerArrayAdapter.notifyDataSetChanged();
                        spinner.setSelection(selected);



                        if(editable!=null && !editable){
                            spinner.setEnabled(false);
                        }
                    }
                    linearLayoutSection.addView(spinner);
                }else if(control.equalsIgnoreCase("catalog")){

                    if(viewGeneral instanceof ViewCustom) {
                        ViewCustom viewCustom = (ViewCustom) viewGeneral;
                        for (ObjectGeneral value : viewCustom.getValues()) {
                            ObjectGeneral catalogObjectGeneral = (ObjectGeneral) value.getAttributes().get("catalog");
                            Catalog catalog = Utils.instanceCatalog(catalogObjectGeneral);

                            ArrayList<Row> data = new ArrayList<>();
                            ArrayList<Row> dataAux = new ArrayList<>();
                            CustomAdapter<Row> adapter = new CustomAdapter<>(data);

                            data.addAll(catalog.getTable().getRows());
                            dataAux.addAll(catalog.getTable().getRows());

                            RecyclerView recyclerView = new RecyclerView(context);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);

                            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                            adapter.notifyDataSetChanged();

                            linearLayoutSection.addView(recyclerView);

                        }
                    }
                }

            }
            linearLayoutChild.addView(linearLayoutSection);
        }
        return linearLayoutParent;
    }

    public static JSONArray getRoles(){
        JSONArray jsonArray = new JSONArray();
        for(String rolAux : Singleton.getInstance().getUser().getRoles()){
            jsonArray.put(rolAux);
        }
        return jsonArray;
    }

    public static int getDpsFromPixels(Context context, int pixels){
        /*
            public abstract Resources getResources ()
                Return a Resources instance for your application's package.
        */
        Resources r = context.getResources();
        int  dps = Math.round(pixels/(r.getDisplayMetrics().densityDpi/160f));
        Log.e("Utils","dps: "+dps);
        return dps;
    }

    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

    public static int getPixelValueText(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }


    public static void customView(View v, int backgroundColor, int borderColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[]{8, 8, 8, 8, 0, 0, 0, 0});
        shape.setColor(backgroundColor);
        shape.setStroke(3, borderColor);
        v.setBackgroundDrawable(shape);
    }

    public static StateListDrawable selectorBackgroundColor(Context context, int normal
            , int pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressed));
        states.addState(new int[]{}, new ColorDrawable(normal));
        return states;
    }

    public static ColorStateList selectorText(Context context, int normal, int pressed) {
        ColorStateList colorStates = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{}
                },
                new int[]{pressed,normal});


        return colorStates;
    }

    public static StateListDrawable selectorBackgroundDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    public static LinearLayout.LayoutParams getParamsMatch(){
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    public static LinearLayout.LayoutParams getParamsMatchWrap(){
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    public static HashMap<String,Object> setAttributesOfJson(JSONObject jsonObject){

        HashMap<String,Object> attributes = new HashMap<>();
        JSONArray names = jsonObject.names();

        for(int i = 0;i<names.length();i++){

            String name = names.optString(i);

            Object element = jsonObject.opt(name);

            if(element!=null && (!(element instanceof JSONObject) && !(element instanceof JSONArray))){
                attributes.put(name,jsonObject.opt(name));
            }

        }
        return attributes;
    }

    public static void changueIconAndTextColorNavigation(NavigationView navigationView){
        // FOR NAVIGATION VIEW ITEM TEXT COLOR
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},  // unchecked
                new int[]{android.R.attr.state_checked},   // checked
                new int[]{}                                // default
        };

        // Fill in color corresponding to state defined in state
        int[] colors = new int[]{
                Color.parseColor("#747474"),
                Color.parseColor("#007f42"),
                Color.parseColor("#747474"),
        };

        ColorStateList navigationViewColorStateList = new ColorStateList(states, colors);

        navigationView.setItemTextColor(navigationViewColorStateList);
        navigationView.setItemIconTintList(navigationViewColorStateList);
    }



    @SuppressLint("NewApi")
    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }

    }


    /*
     if(Build.VERSION.SDK_INT >= 16) {

                btnBgSelector.setBackground(selectorBackgroundColor(this,
                     getResources().getColor(R.color.blue),
                     getResources().getColor(R.color.orange)));

            } else {

                btnBgSelector.setBackgroundDrawable (selectorBackgroundColor(this,
                     getResources().getColor(R.color.blue),
                     getResources().getColor(R.color.orange)));

            }
     */


    private static Catalog instanceCatalog(ObjectGeneral objectGeneralCatalog){

        Catalog catalog = new Catalog();

        catalog.addAttributesOfObjectGeneral(objectGeneralCatalog);

        catalog.setToolbar(getToolBarGeneral((List)objectGeneralCatalog.getAttributes().get("toolbar")));
        catalog.setTable(getTable((ObjectGeneral)objectGeneralCatalog.getAttributes().get("table")));
        catalog.setRootEvent(getEvent((ObjectGeneral) objectGeneralCatalog.getAttributes().get("root_event")));

        return catalog;
    }

    private static MenuGeneral getToolBarGeneral(List<ObjectGeneral> listObjetoGeneralToolbar){


        for(int i = 0;i<listObjetoGeneralToolbar.size();i++){

            ObjectGeneral objectGeneralToolbar = listObjetoGeneralToolbar.get(i);
            MenuGeneral toolbarGeneral = new MenuGeneral();
            toolbarGeneral.addAttributesOfObjectGeneral(objectGeneralToolbar);
            toolbarGeneral.setEvent(getEvent((ObjectGeneral) objectGeneralToolbar.getAttributes().get("event")));


            return toolbarGeneral;
        }

        return null;

    }

    private static Table getTable(ObjectGeneral objectGeneralTable){

        Table table = new Table();

        table.addAttributesOfObjectGeneral(objectGeneralTable);

        List<ObjectGeneral> objectGeneralArray = (List)objectGeneralTable.getAttributes().get("headers");

        ArrayList<ObjectGeneral> headers = new ArrayList<>();

        for(int i = 0;i<objectGeneralArray.size();i++){
            ObjectGeneral objectGeneralHeader = objectGeneralArray.get(i);

            ObjectGeneral objectGeneral = new ObjectGeneral();
            objectGeneral.addAttributesOfObjectGeneral(objectGeneralHeader);

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

        List<ObjectGeneral> objectGeneralArrayData = (List)objectGeneralTable.getAttributes().get("data");

        ArrayList<Row> rows = new ArrayList<>();
        for(int i = 0;i<objectGeneralArrayData.size();i++){
            ObjectGeneral objectGeneralData = objectGeneralArrayData.get(i);


            Row row = new Row();

            //agrega cada campo con su respectivo header
            ArrayList<DataRow> fields = new ArrayList<>();
            for(int j = 0;j<table.getHeaders().size();j++){
                ObjectGeneral header = table.getHeaders().get(j);
                Object data = objectGeneralData.getAttributes().get(header.getAttributes().get("field").toString());
                DataRow dataRow = new DataRow();
                dataRow.setHeader(header);
                dataRow.setData(data);
                fields.add(dataRow);
            }

            row.setFields(fields);


            List<ObjectGeneral> objectGeneralArrayActions = (List)objectGeneralData.getAttributes().get("actions");
            if(objectGeneralArrayActions!=null) {
                ArrayList<Action> actions = new ArrayList<>();
                for (int j = 0; j < objectGeneralArrayActions.size(); j++) {
                    ObjectGeneral objectGeneralAction = objectGeneralArrayActions.get(j);

                    if(objectGeneralAction!=null) {
                        Action action = new Action();
                        action.addAttributesOfObjectGeneral(objectGeneralAction);
                        action.setEvent(getEvent((ObjectGeneral) objectGeneralAction.getAttributes().get("event")));
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

    private static Event getEvent(ObjectGeneral objectGeneral){
        if (objectGeneral != null) {
            Event event = new Event();

            event.addAttributesOfObjectGeneral(objectGeneral);

            ObjectGeneral headersObjetoGeneral = (ObjectGeneral)objectGeneral.getAttributes().get("headers");
            if(headersObjetoGeneral!=null){
                ObjectGeneral headers = new ObjectGeneral();
                headers.addAttributesOfObjectGeneral(headersObjetoGeneral);
                event.setHeaders(headers);
            }

            ObjectGeneral parametersObjectoGeneral = (ObjectGeneral)objectGeneral.getAttributes().get("parameters");
            if(parametersObjectoGeneral!=null){
                ObjectGeneral parameters = new ObjectGeneral();
                parameters.addAttributesOfObjectGeneral(parametersObjectoGeneral);
                event.setParameters(parameters);
            }

            return event;
        }

        return null;
    }
}
