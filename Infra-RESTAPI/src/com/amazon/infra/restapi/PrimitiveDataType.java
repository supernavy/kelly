package com.amazon.infra.restapi;

public class PrimitiveDataType
{
    public static DataType<String> STRING = new DataType<String>(String.class);
    public static DataType<Integer> INT = new DataType<Integer>(Integer.class);
    public static DataType<Long> LONG = new DataType<Long>(Long.class);
    public static DataType<Boolean> BOOL = new DataType<Boolean>(Boolean.class);
    public static DataType<Void> VOID = new DataType<Void>(Void.class);
    public static NullDataType NULL = new NullDataType();
    
    @SuppressWarnings("rawtypes")
    public static class NullDataType extends DataType
    {

        @SuppressWarnings("unchecked")
        public NullDataType()
        {
            super(null);
        }
        
        @Override
        public boolean validate(Object obj)
        {
            if(obj==null)
                return true;
            return false;
        }
        
        @Override
        public String toString()
        {
            return "NullType";
        }
        
        @Override
        public boolean instanceOf(Object obj)
        {
            if(obj == null)
                return true;
            return false;
        }
        
        @Override
        public Object cast(Object obj)
        {
            return null;
        }
    }
}
