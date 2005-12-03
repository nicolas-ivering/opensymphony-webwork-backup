package com.opensymphony.webwork.example;

import org.apache.commons.logging.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.opensymphony.webwork.interceptor.ParameterAware;
import com.opensymphony.xwork.ActionSupport;

/**
 *	Simple test action.
 *
 *	@see <related>
 *	@author Rickard �berg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class Test extends ActionSupport implements ParameterAware {
    // Constants -----------------------------------------------------

    // Attributes ----------------------------------------------------
    String name = "HelloWorld";
    String foo;
    ArrayList list;
    Properties settings = new Properties();
    Map params;

    // Static --------------------------------------------------------

    // Constructors --------------------------------------------------

    // Public --------------------------------------------------------
    public String getName() {
        return name;
    }

    public List getPeople() {
        return list;
    }

    public Map getSettings() {
        return settings;
    }

    public Map getParameters() {
        return params;
    }

    public Person getPerson() {
        return new Person();
    }

    public Boolean getBool(String b) {
        return new Boolean(b);
    }

    public String getSecond(String one, String two) {
        return two;
    }

    public boolean getStringsEqual(String one, String two) {
        return one.equals(two);
    }

    public Integer getInteger(int i) {
        return new Integer(i);
    }

    public void setFoo(String aValue) {
        foo = aValue;
    }

    public String getFoo() {
        return foo;
    }

    public boolean[] getBools() {
        boolean[] b = new boolean[]{true, false, false, true};
        return b;
    }

    public String concat(String s1, String s2) {
        return s1 + s2;
    }

    public String getSameString(String s) {
        return s;
    }

    // ParameterAware implementation ---------------------------------
    public void setParameters(Map parameters) {
        this.params = parameters;
    }

    // Action implementation -----------------------------------------
    protected String doExecute() throws Exception {
        list = new ArrayList();
        list.add(new Person());
        list.add(new Person("Maurice C. Parker", "maurice@vineyardenterprise.com"));

        settings.put("foo", "bar");
        settings.put("black", "white");

        return SUCCESS;
    }

    /**
     * Test of CommandDriven interface
     */
    public String doFoo() {
        LogFactory.getLog(this.getClass()).debug("Foo command executed");
        return SUCCESS;
    }

}