package com.masnegocio.semilla.utils;


import com.masnegocio.semilla.models.DataRow;
import com.masnegocio.semilla.models.Row;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Tadeo-developer on 09/11/16.
 */

public class Compare implements Comparator<Row> {


    private String fieldOrder;

    public Compare(String fieldOrder){
        this.fieldOrder = fieldOrder;
    }

    @Override
    public int compare(Row row1, Row row2) {

        DataRow dataRow1 = getDataRowOfHeader(row1.getFields(),fieldOrder);
        DataRow dataRow2 = getDataRowOfHeader(row2.getFields(),fieldOrder);

        if(dataRow1 != null && dataRow2 != null) {
            if (dataRow1.getData() instanceof String && dataRow2.getData() instanceof String) {
                String data = (String) dataRow1.getData();
                String data2 = (String) dataRow2.getData();

                return data.compareTo(data2);
            }else if(dataRow1.getData() instanceof Integer && dataRow2.getData() instanceof Integer){
                Integer data = (Integer) dataRow1.getData();
                Integer data2 = (Integer) dataRow2.getData();

                if(data>data2){
                    return 1;
                }else if(data<data2){
                    return -1;
                }else{
                    return 0;
                }
            }else if(dataRow1.getData() instanceof Float && dataRow2.getData() instanceof Float){
                Float data = (Float) dataRow1.getData();
                Float data2 = (Float) dataRow2.getData();

                if(data>data2){
                    return 1;
                }else if(data<data2){
                    return -1;
                }else{
                    return 0;
                }
            }else if(dataRow1.getData() instanceof Double && dataRow2.getData() instanceof Double){
                Double data = (Double) dataRow1.getData();
                Double data2 = (Double) dataRow2.getData();

                if(data>data2){
                    return 1;
                }else if(data<data2){
                    return -1;
                }else{
                    return 0;
                }
            }
        }

        return 0;
    }


    private DataRow getDataRowOfHeader(ArrayList<DataRow> fields, String field){

        for(DataRow dataRow : fields){
            String header = (String)dataRow.getHeader().getAttributes().get("field");

            if(header.equalsIgnoreCase(field)){
                return dataRow;
            }
        }

        return null;

    }

}
