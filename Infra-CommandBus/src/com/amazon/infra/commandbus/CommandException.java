package com.amazon.infra.commandbus;

public class CommandException extends Exception
{

    /**
     * default serial id
     */
    private static final long serialVersionUID = -1190961943333454030L;

    public CommandException(String arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    public CommandException(Throwable arg0)
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    
}
