package com.amazon.infra.restapi.type;

import org.json.simple.JSONArray;
import com.amazon.infra.restapi.DataType;

public class JSONArrayDataType<T> extends ArrayDataType<JSONArray, T>
{   
    public JSONArrayDataType(DataType<T> itemType)
    {
        super(JSONArray.class, itemType);
    }
}
