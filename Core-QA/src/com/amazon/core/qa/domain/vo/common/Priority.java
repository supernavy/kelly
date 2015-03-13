package com.amazon.core.qa.domain.vo.common;

import java.util.ArrayList;
import java.util.List;

public enum Priority
{
    HiP0(0, "HiPri0 - Must Test"), P0(1,"P0 - Preflight weekly (BVT)"), P1(2,"P1 - Preflight weekly"), P2(3,"P2"), P3(4,"P3");
    
    int v;
    String title;
    private Priority(int v,String title)
    {
        this.v = v;
        this.title = title;
    }
    
    public int getV()
    {
        return v;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public static List<Priority> equalAndBlow(Priority p) 
    {
        List<Priority> ret = new ArrayList<Priority>();
        for(Priority pp : Priority.values())
        {
            if(pp.getV()<=p.getV())
            {
                ret.add(pp);
            }
        }
        
        return ret;
    }
}
