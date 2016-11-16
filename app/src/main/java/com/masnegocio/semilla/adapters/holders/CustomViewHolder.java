package com.masnegocio.semilla.adapters.holders;


import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.masnegocio.semilla.R;
import com.masnegocio.semilla.models.Action;
import com.masnegocio.semilla.models.DataRow;
import com.masnegocio.semilla.models.ObjectGeneral;
import com.masnegocio.semilla.models.Row;
import com.masnegocio.semilla.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tadeo-developer on 18/01/16.
 */
public class CustomViewHolder extends ViewHolder{

    public static String ID_BTN_DELETE = "btnBorrar";
    public static String ID_BTN_EDIT = "btnEditar";
    public static String ID_BTN_PRINT = "btnImprimir";

    public interface OnClickItemIconListener{
        void onClickItemIcon(Row row, Action action);
    }

    public static OnClickItemIconListener onClickItemIconListener;

    private Row row;
    private ArrayList<ViewDraw> viewDraws;

    public CustomViewHolder(View itemView) {
        super(itemView);

    }

    public void setOnClickItemIconListener(OnClickItemIconListener onClickItemIconListener){
        this.onClickItemIconListener = onClickItemIconListener;
    }

    @Override
    public void bind(Object obj) {

        if(obj instanceof Row){

            row = (Row)obj;

            viewDraws = new ArrayList<>();
            ArrayList<DataRow> fields = row.getFields();

            for (DataRow dataRow : fields) {

                TextView textViewHeader = new TextView(itemView.getContext());
                TextView textView = new TextView(itemView.getContext());


                textViewHeader.setId(Utils.generateViewId());
                textView.setId(Utils.generateViewId());


                textViewHeader.setText(dataRow.getHeader().getAttributes().get("label").toString());
                textViewHeader.setTextColor(itemView.getResources().getColor(android.R.color.darker_gray));
                textViewHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX,itemView.getResources().getDimension(R.dimen.title_row));


                if(dataRow.getData()!=null) {
                    textView.setText(dataRow.getData().toString());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,itemView.getResources().getDimension(R.dimen.text_row));

                }

                ViewDraw viewDraw = new ViewDraw();
                viewDraw.setVistaHeader(textViewHeader);
                viewDraw.setVista(textView);
                viewDraw.setHeader(dataRow.getHeader());

                LinearLayout linearView = new LinearLayout(itemView.getContext());
                linearView.setId(Utils.generateViewId());
                linearView.setOrientation(LinearLayout.VERTICAL);

                viewDraw.setLinearLayout(linearView);

                viewDraws.add(viewDraw);

                /*if(itemView instanceof LinearLayout){
                    linearLayout.addView(textViewHeader);
                    linearLayout.addView(textView);
                }*/


            }


            LinearLayout linearLayout = (LinearLayout)itemView;

            linearLayout.removeAllViews();

            RelativeLayout relativeLayout = new RelativeLayout(itemView.getContext());
            RelativeLayout.LayoutParams paramsRelative = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(paramsRelative);

            linearLayout.addView(relativeLayout);


            LinearLayout linearIcons = generateItemIcons();
            if(linearIcons!=null){
                linearLayout.addView(linearIcons);
            }


            for(ViewDraw viewDraw : viewDraws){

               // Log.e("CustomViewHolder","view: "+viewDraw.getHeader().getAttributes().get("field"));
                ArrayList<Rule> rules = getRules(viewDraw);

                if(rules!=null && !rules.isEmpty() && rules.size()>0){
                    ViewDraw viewDrawAux = searchFieldView((String)rules.get(0).getValue());

                    if(viewDrawAux!=null && viewDrawAux.getVista()!=null) {
                        Log.e("CustomViewHolder","viewEncontrada: "+viewDrawAux.getHeader().getAttributes().get("field"));

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        int margin = (int) itemView.getResources().getDimension(R.dimen.activity_vertical_margin);
                        params.setMargins(margin,margin,margin,margin);

                        for(Rule rule : rules){
                            //params.addRule(rule.getRuleRelative(), viewDrawAux.getLinearLayout().getId());

                            ViewDraw vdAux = searchFieldView((String)rule.getValue());
                            if(vdAux!=null && vdAux.getLinearLayout()!=null) {
                                params.addRule(rule.getRuleRelative(),vdAux.getLinearLayout().getId());
                            }
                        }

                        viewDraw.getLinearLayout().setLayoutParams(params);

                        viewDraw.getLinearLayout().addView(viewDraw.getVistaHeader());
                        viewDraw.getLinearLayout().addView(viewDraw.getVista());

                        relativeLayout.addView(viewDraw.getLinearLayout());



                    }else{
                        Log.e("CustomViewHolder","viewDrawAux y vista son null");
                    }
                }else{

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    int margin = (int) itemView.getResources().getDimension(R.dimen.activity_vertical_margin);
                    params.setMargins(margin,margin,margin,margin);

                    viewDraw.getLinearLayout().setLayoutParams(params);

                    viewDraw.getLinearLayout().addView(viewDraw.getVistaHeader());
                    viewDraw.getLinearLayout().addView(viewDraw.getVista());

                    //descomentar para agregar las posiciones desde el ws
                    //relativeLayout.addView(viewDraw.getLinearLayout());

                    linearLayout.addView(viewDraw.getLinearLayout());



                }



            }

            /*
        EditText editText = new EditText(this);
        Button buttonR = new Button(this);
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        buttonR.setId(View.generateViewId());
        buttonR.setText("Dame Touch");
        buttonR.setLayoutParams(buttonParams);

        RelativeLayout.LayoutParams paramsEditText = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsEditText.addRule(RelativeLayout.BELOW,buttonR.getId());
        editText.setLayoutParams(paramsEditText);*/
        }

    }

    private LinearLayout generateItemIcons(){
        //agregar iconos
        if(row.getActions()!=null && !row.getActions().isEmpty()){
            // Log.e("CustomViewHolder","Se encontraron Actions");
            LinearLayout linearIcons = new LinearLayout(itemView.getContext());
            LinearLayout.LayoutParams paramsIcons = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsIcons.gravity = Gravity.RIGHT;
            linearIcons.setOrientation(LinearLayout.HORIZONTAL);
            linearIcons.setLayoutParams(paramsIcons);

            LinearLayout.LayoutParams paramsIcon = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int marginIcon = (int) itemView.getResources().getDimension(R.dimen.activity_vertical_margin);
            paramsIcon.setMargins(marginIcon,0,0,0);

            for(final Action action : row.getActions()){
                String idAction = (String)action.getAttributes().get("id");
                Boolean active = (Boolean)action.getAttributes().get("active");

                if(idAction!=null){
                    ImageView iconAction = new ImageView(itemView.getContext());
                    iconAction.setLayoutParams(paramsIcon);

                    iconAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.e("CustomViewHolder","boton accion");
                            if(onClickItemIconListener!=null){
                                onClickItemIconListener.onClickItemIcon(row,action);
                            }

                        }
                    });

                    if(idAction.equalsIgnoreCase(ID_BTN_EDIT)){
                        if(active)
                            iconAction.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_edit_24dp));
                        else
                            iconAction.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_edit_gray_24dp));

                    }else if(idAction.equalsIgnoreCase(ID_BTN_DELETE)){
                        if(active)
                            iconAction.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_delete_24dp));
                        else
                            iconAction.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_delete_gray_24dp));

                    }else{
                        if(active)
                            iconAction.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_error_24dp));
                        else
                            iconAction.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_error_gray_24dp));
                    }

                    linearIcons.addView(iconAction);

                }
            }

            return linearIcons;
        }

        return null;
    }

    public ArrayList<Rule> getRules(ViewDraw viewDraw){

        HashMap<String,Object> attrs = viewDraw.getHeader().getAttributes();

        ArrayList<Rule> rules = new ArrayList<>();
        for (String key : attrs.keySet()) {

            if(key.equalsIgnoreCase("toRightOf")){
                Rule rule = new Rule();
                rule.setKey(key);
                rule.setValue(attrs.get(key));
                rule.setRuleRelative(RelativeLayout.RIGHT_OF);
                rules.add(rule);
            }else if(key.equalsIgnoreCase("toLeftOf")){
                Rule rule = new Rule();
                rule.setKey(key);
                rule.setValue(attrs.get(key));
                rule.setRuleRelative(RelativeLayout.LEFT_OF);
                rules.add(rule);
            }else if(key.equalsIgnoreCase("toBelowOf")){
                Rule rule = new Rule();
                rule.setKey(key);
                rule.setValue(attrs.get(key));
                rule.setRuleRelative(RelativeLayout.BELOW);
                rules.add(rule);
            }else if(key.equalsIgnoreCase("toAboveOf")){
                Rule rule = new Rule();
                rule.setKey(key);
                rule.setValue(attrs.get(key));
                rule.setRuleRelative(RelativeLayout.ABOVE);
                rules.add(rule);
            }

        }

        return  rules;
    }

    public ViewDraw searchFieldView(String field){

        if(field!=null) {
            for (ViewDraw viewDraw : viewDraws) {
                String fieldViewDraw = (String) viewDraw.getHeader().getAttributes().get("field");
                if (fieldViewDraw != null && fieldViewDraw.equalsIgnoreCase(field)) {
                    return viewDraw;
                }
            }
        }

        return null;

    }

    class Rule{

        private int ruleRelative;
        private String key;
        private Object value;

        public int getRuleRelative() {
            return ruleRelative;
        }

        public void setRuleRelative(int ruleRelative) {
            this.ruleRelative = ruleRelative;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    class ViewDraw {
        private View vistaHeader;
        private View vista;
        private LinearLayout linearLayout;
        private ObjectGeneral header;



        public View getVistaHeader() {
            return vistaHeader;
        }

        public void setVistaHeader(View vistaHeader) {
            this.vistaHeader = vistaHeader;
        }

        public View getVista() {
            return vista;
        }

        public void setVista(View vista) {
            this.vista = vista;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setLinearLayout(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        public ObjectGeneral getHeader() {
            return header;
        }

        public void setHeader(ObjectGeneral header) {
            this.header = header;
        }
    }


}