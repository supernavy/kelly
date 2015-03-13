package com.amazon.infra.restapi.type;

import com.amazon.infra.restapi.DataType;

public class EnumDataType<T> extends DataType<T>
{
    DataType<T> type;
    T[] options;
    
    public EnumDataType(DataType<T> type, T[] options)
    {
        super(type.getCls());
        this.type = type;
        this.options = options;
        validateOptions();
    }

    private void validateOptions()
    {
        for (T o : options) {
            type.cast(o);
        }
    }
    
    public T[] options() {
        return options;
    }

    @Override
    public boolean validate(Object obj)
    {
        if (!super.validate(obj))
            return false;
        for (Object t : options) {
            if (obj.equals(t)) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public String toString()
    {
        return new StringBuffer().append(super.toString()).append(",options=").append(options).toString();
    }
}
