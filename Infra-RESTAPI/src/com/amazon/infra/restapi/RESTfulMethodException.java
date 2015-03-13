package com.amazon.infra.restapi;

public class RESTfulMethodException extends Exception
{
    /**
     * default serial ID
     */
    private static final long serialVersionUID = 5169560302667600025L;

    public RESTfulMethodException(String arg0)
    {
        super(arg0);
    }

    public RESTfulMethodException(Throwable arg0)
    {
        super(arg0);
    }
    
    
}
