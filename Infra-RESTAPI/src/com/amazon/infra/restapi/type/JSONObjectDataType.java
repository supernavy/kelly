package com.amazon.infra.restapi.type;

import java.util.Map;
import org.json.simple.JSONObject;
import com.amazon.infra.restapi.DataType;

public class JSONObjectDataType extends MapDataType<JSONObject, String>
{   
    public static JSONObjectDataType EMPTY = new JSONObjectDataType(null, null);
    
    public JSONObjectDataType(Map<String, DataType<?>> requiredValueTypes, Map<String, DataType<?>> optionalValueTypes)
    {
        super(JSONObject.class, requiredValueTypes, optionalValueTypes);
    }
}
