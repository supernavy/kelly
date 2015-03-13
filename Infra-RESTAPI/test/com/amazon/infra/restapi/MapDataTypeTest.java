package com.amazon.infra.restapi;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.amazon.infra.restapi.DataType;
import com.amazon.infra.restapi.PrimitiveDataType;
import com.amazon.infra.restapi.type.MapDataType;

@SuppressWarnings("rawtypes")
public class MapDataTypeTest
{
    static String Name = "name";
    static String Id = "id";
    static String Desc = "description";
    
    @Test
    public void testSimple() {
        Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
        requiredValueType.put(Id, PrimitiveDataType.LONG);
        requiredValueType.put(Name, PrimitiveDataType.STRING);
        Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
        optionalValueType.put(Desc, PrimitiveDataType.STRING);
        
        MapDataType<Map, String> type = new MapDataType<Map, String>(Map.class, requiredValueType, optionalValueType);
        
        Map<String,Object> instance = new HashMap<String, Object>();
        String name = "navy";
        Long id = 100L;
        String desc = "li haijun";
        instance.put(Name, name);
        instance.put(Id, id);
        instance.put(Desc, desc);
        
        type.cast(instance);
        Assert.assertTrue(type.instanceOf(instance));
        Assert.assertTrue(type.validate(instance));
        Assert.assertEquals(type.getRequiredValue(instance, Name), name);
        Assert.assertEquals(type.getRequiredValue(instance, Id), id);
        Assert.assertEquals(type.getValue(instance, Name), name);
        Assert.assertEquals(type.getValue(instance, Id), id);
        Assert.assertNull(type.getRequiredValue(instance, Desc));
        Assert.assertNull(type.getOptionalValue(instance, Name));
        Assert.assertNull(type.getOptionalValue(instance, Id));
        Assert.assertEquals(type.getValue(instance, Desc), desc);
        Assert.assertEquals(type.getOptionalValue(instance, Desc), desc);
    }
    
    @Test
    public void testMissingRequired() {
        Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
        requiredValueType.put(Id, PrimitiveDataType.LONG);
        requiredValueType.put(Name, PrimitiveDataType.STRING);
        Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
        optionalValueType.put(Desc, PrimitiveDataType.STRING);
        
        MapDataType<Map, String> type = new MapDataType<Map, String>(Map.class, requiredValueType, optionalValueType);
        
        Map<String,Object> instance = new HashMap<String, Object>();
        String name = "navy";
        String desc = "li haijun";
        instance.put(Name, name);
        instance.put(Desc, desc);
        
        Assert.assertTrue(type.instanceOf(instance));
        Assert.assertFalse(type.validate(instance));
        type.cast(instance);
    }
    
    @Test
    public void testMissingOptional() {
        Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
        requiredValueType.put(Id, PrimitiveDataType.LONG);
        requiredValueType.put(Name, PrimitiveDataType.STRING);
        Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
        optionalValueType.put(Desc, PrimitiveDataType.STRING);
        
        MapDataType<Map, String> type = new MapDataType<Map, String>(Map.class, requiredValueType, optionalValueType);
        
        Map<String,Object> instance = new HashMap<String, Object>();
        String name = "navy";
        Long id = 100L;
        instance.put(Name, name);
        instance.put(Id, id);
        
        Assert.assertTrue(type.instanceOf(instance));
        Assert.assertTrue(type.validate(instance));
        type.cast(instance);
    }
    
    @Test
    public void testHavingUnknownKey() {
        Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
        requiredValueType.put(Id, PrimitiveDataType.LONG);
        requiredValueType.put(Name, PrimitiveDataType.STRING);
        Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
        optionalValueType.put(Desc, PrimitiveDataType.STRING);
        
        MapDataType<Map, String> type = new MapDataType<Map, String>(Map.class, requiredValueType, optionalValueType);
        
        Map<String,Object> instance = new HashMap<String, Object>();
        String name = "navy";
        Long id = 100L;
        instance.put(Name, name);
        instance.put(Id, id);
        instance.put("Bad", "I am bad guy");
        
        Assert.assertTrue(type.instanceOf(instance));
        Assert.assertTrue(type.validate(instance));
    }
    
    @Test
    public void testHavingWrongTypeValue() {
        Map<String, DataType<?>> requiredValueType = new HashMap<String, DataType<?>>();
        requiredValueType.put(Id, PrimitiveDataType.LONG);
        requiredValueType.put(Name, PrimitiveDataType.STRING);
        Map<String, DataType<?>> optionalValueType = new HashMap<String, DataType<?>>();
        optionalValueType.put(Desc, PrimitiveDataType.STRING);
        
        MapDataType<Map, String> type = new MapDataType<Map, String>(Map.class, requiredValueType, optionalValueType);
        
        Map<String,Object> instance = new HashMap<String, Object>();
        String name = "navy";
        String id = "100";
        instance.put(Name, name);
        instance.put(Id, id);
        instance.put("Bad", "I am bad guy");
        
        Assert.assertTrue(type.instanceOf(instance));
        Assert.assertFalse(type.validate(instance));
    }
}
