package com.amazon.infra.restapi.type;

import java.util.HashMap;
import java.util.Map;
import com.amazon.infra.restapi.DataType;

@SuppressWarnings("rawtypes")
public class MapDataType<M extends Map, K> extends DataType<M>
{
    Map<K, DataType<?>> requiredValueTypes = new HashMap<K, DataType<?>>();
    Map<K, DataType<?>> optionalValueTypes = new HashMap<K, DataType<?>>();

    public MapDataType(Class<M> mapCls, Map<K, DataType<?>> requiredValueTypes, Map<K, DataType<?>> optionalValueTypes)
    {
        super(mapCls);
        if(requiredValueTypes!=null)
            this.requiredValueTypes.putAll(requiredValueTypes);
        if(optionalValueTypes!=null)
            this.optionalValueTypes.putAll(optionalValueTypes);
    }

    public Object getRequiredValue(Object instance, Object key)
    {
        if (requiredValueTypes.containsKey(key)) {
            return cast(instance).get(key);
        }
        return null;
    }

    public Object getOptionalValue(Object instance, Object key)
    {
        if (optionalValueTypes.containsKey(key)) {
            return cast(instance).get(key);
        }
        return null;
    }

    public Object getValue(Object instance, Object key)
    {
        return cast(instance).get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean validate(Object obj)
    {
        if (!super.validate(obj)) {
            return false;
        }

        Map tempMap = new HashMap();
        tempMap.putAll(cast(obj));

        for (K k : requiredValueTypes.keySet()) {
            if (!tempMap.containsKey(k)) {
                System.out.println(String.format("Missing required key[%s] in [%s]", k, tempMap.keySet()));
                return false;
            }
            if (!requiredValueTypes.get(k).instanceOf(tempMap.get(k))) {
                System.out.println(String.format("Wrong required value[%s][%s]  for key[%s][%s]", tempMap.get(k), tempMap.get(k).getClass().getName(), k, requiredValueTypes.get(k)));
                return false;
            }
            tempMap.remove(k);
        }

        for (K k : optionalValueTypes.keySet()) {
            if (!tempMap.containsKey(k)) {
                System.out.println(String.format("Missing optional key[%s] in [%s]. It is ok.", k, tempMap.keySet()));
                continue;
            }
            if (tempMap.get(k) != null && !optionalValueTypes.get(k).instanceOf(tempMap.get(k))) {
                System.out.println(String.format("Wrong optional value[%s][%s]  for key[%s][%s]", tempMap.get(k), tempMap.get(k).getClass().getName(), k, optionalValueTypes.get(k)));
                return false;
            }
            tempMap.remove(k);
        }

        if (tempMap.size() > 0) {
            System.out.println(String.format("Having unknown keys %s", tempMap.keySet()));
//            return false;
        }

        return true;
    }
    
    @Override
    public String toString()
    {
        return new StringBuffer().append(super.toString()).append(",requiredValueTypes=").append(requiredValueTypes).append(",optionalValueTypes=").append(optionalValueTypes).toString();
    }
}
