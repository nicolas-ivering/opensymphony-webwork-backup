/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockJspWriter;

import com.opensymphony.webwork.TestAction;
import com.opensymphony.webwork.config.Configuration;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;


/**
 * AbstractTagTest
 * User: jcarreira
 * Created: Oct 17, 2003 10:24:34 PM
 */
public class AbstractTagTest extends TestCase {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected Action action;
    protected Map context;
    protected Map session;
    protected OgnlValueStack stack;

    /**
    * contains the buffer that our unit test will write to.  we can later verify this buffer for correctness.
    */
    protected StringWriter writer;
    protected WebWorkMockHttpServletRequest request;
    protected WebWorkMockPageContext pageContext;
    private HttpServletResponse response;

    //~ Constructors ///////////////////////////////////////////////////////////

    public AbstractTagTest() {
        super();
    }

    public AbstractTagTest(String s) {
        super(s);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Constructs the action that we're going to test against.  For most UI tests, this default action should be enough.
    * However, simply override getAction to return a custom Action if you need something more sophisticated.
    * @return the Action to be added to the OgnlValueStack as part of the unit test
    */
    public Action getAction() {
        return new TestAction();
    }

    protected void setUp() throws Exception {
        super.setUp();

        /**
        * create our standard mock objects
        */
        action = this.getAction();
        stack = new OgnlValueStack();
        context = stack.getContext();
        stack.push(action);

        request = new WebWorkMockHttpServletRequest();
        request.setAttribute("webwork.valueStack", stack);
        response = new WebWorkMockHttpServletResponse();
        request.setSession(new WebWorkMockHttpSession());
        ActionContext.getContext().setValueStack(stack);

        writer = new StringWriter();

        JspWriter jspWriter = new TestJspWriter(writer);

        pageContext = new WebWorkMockPageContext();
        pageContext.setRequest(request);
        pageContext.setResponse(response);
        pageContext.setJspWriter(jspWriter);

        session = new HashMap();
        ActionContext.getContext().setSession(session);

        Configuration.setConfiguration(null);
    }

    protected void tearDown() throws Exception {
        pageContext.verify();
        request.verify();
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
    * Unforunately, the MockJspWriter throws a NotImplementedException when any of the Writer methods are invoked and
    * as you might guess, Velocity uses the Writer methods.  I'velocityEngine subclassed the MockJspWriter for the time being so
    * that we can do testing on the results until MockJspWriter gets fully implemented.
    *
    * todo replace this once MockJspWriter implements Writer correctly (i.e. doesn't throw NotImplementException)
    */
    public class TestJspWriter extends MockJspWriter {
        StringWriter writer;

        public TestJspWriter(StringWriter writer) {
            this.writer = writer;
        }

        public void write(String str) throws IOException {
            writer.write(str);
        }

        public void write(int c) throws IOException {
            writer.write(c);
        }

        public void write(char[] cbuf) throws IOException {
            writer.write(cbuf);
        }

        public void write(String str, int off, int len) throws IOException {
            writer.write(str, off, len);
        }
    }
}
