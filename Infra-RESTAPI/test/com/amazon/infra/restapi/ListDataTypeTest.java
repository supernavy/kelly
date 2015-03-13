package com.amazon.infra.restapi;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.amazon.infra.restapi.DataType;
import com.amazon.infra.restapi.PrimitiveDataType;
import com.amazon.infra.restapi.type.ListDataType;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListDataTypeTest
{

    @Test
    public void testSimple()
    {
        List<DataType<?>> itemTypes = new ArrayList<DataType<?>>();
        itemTypes.add(PrimitiveDataType.STRING);
        itemTypes.add(PrimitiveDataType.INT);

        ListDataType<List> dataType = new ListDataType<List>(List.class, new DataType<?>[] { PrimitiveDataType.STRING, PrimitiveDataType.INT }, new DataType<?>[] {});

        List instance = new ArrayList(); // valid
        instance.add("A");
        instance.add(1);

        Assert.assertTrue(dataType.instanceOf(instance));
        Assert.assertTrue(dataType.validate(instance));
        dataType.cast(instance);
        Assert.assertEquals(dataType.getSize(instance), 2);
        Assert.assertEquals(dataType.getValue(instance, 0), "A");
        Assert.assertEquals(dataType.getValue(instance, 1), 1);

        List instance1 = new ArrayList(); // invalid, type mismatch
        instance1.add(2);
        instance1.add(1);

        Assert.assertTrue(dataType.instanceOf(instance1));
        Assert.assertFalse(dataType.validate(instance1));
    }

    @Test
    public void testMinMaxDifferent()
    {
        List<DataType<?>> itemTypes = new ArrayList<DataType<?>>();
        itemTypes.add(PrimitiveDataType.STRING);
        itemTypes.add(PrimitiveDataType.INT);

        ListDataType<List> dataType = new ListDataType<List>(List.class, new DataType<?>[] { PrimitiveDataType.STRING, PrimitiveDataType.INT }, new DataType<?>[] { PrimitiveDataType.STRING });

        List instance = new ArrayList();//valid, max size
        instance.add("A");
        instance.add(1);
        instance.add("test");

        Assert.assertTrue(dataType.instanceOf(instance));
        Assert.assertTrue(dataType.validate(instance));
        dataType.cast(instance);
        Assert.assertEquals(dataType.getSize(instance), 3);
        Assert.assertEquals(dataType.getValue(instance, 0), "A");
        Assert.assertEquals(dataType.getValue(instance, 1), 1);
        Assert.assertEquals(dataType.getValue(instance, 2), "test");

        List instance1 = new ArrayList();//valid, min size
        instance1.add("A");
        instance1.add(1);

        Assert.assertTrue(dataType.instanceOf(instance1));
        Assert.assertTrue(dataType.validate(instance1));
        dataType.cast(instance1);
        Assert.assertEquals(dataType.getSize(instance1), 2);
        Assert.assertEquals(dataType.getValue(instance1, 0), "A");
        Assert.assertEquals(dataType.getValue(instance1, 1), 1);

        List instance2 = new ArrayList();//invalid, less than min size
        instance2.add("A");

        Assert.assertTrue(dataType.instanceOf(instance2));
        Assert.assertFalse(dataType.validate(instance2));
        dataType.cast(instance2);
        Assert.assertEquals(dataType.getSize(instance2), 1);
        Assert.assertEquals(dataType.getValue(instance2, 0), "A");

        List instance3 = new ArrayList();//invalid, larger than max size
        instance3.add("A");
        instance3.add(1);
        instance3.add("test");
        instance3.add("test");
        Assert.assertTrue(dataType.instanceOf(instance3));
        Assert.assertFalse(dataType.validate(instance3));
    }
}
