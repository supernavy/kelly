package com.amazon.integration.demo.system;

public interface DemoSystem
{
    public static String Repository_MyProductQA = "integ.productqa.repository";
    public static String Repository_MyBuildQA = "integ.buildqa.repository";
    public static String Repository_MyTestrailProject = "integ.testrailproject.repository";
    public static String Repository_MyTestrailPlan = "integ.testrailplan.repository";
    public static String Repository_ExternalSignoff = "externalSignoff.repository";
    
    public static String System_QA = "qa.commandbus";
    public static String System_PM = "pm.commandbus";
    public static String System_RM = "rm.commandbus";
    public static String System_Testrail = "testrail.commandbus";
}
