/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.AbstractHttpHeaderPopulatingResult;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * <!-- START SNIPPET: description -->
 *
 * A custom Result type for evaluating HTTP headers against the ValueStack.
 * 
 * <!-- END SNIPPET: description -->
 * <p/>
 * <b>This result type takes the following parameters:</b>
 *
 * <!-- START SNIPPET: params -->
 *
 * <ul>
 *
 * <li><b>status</b> - the http servlet response status code that should be set on a response.</li>
 *
 * <li><b>parse</b> - true by default. If set to false, the headers param will not be parsed for Ognl expressions.</li>
 *
 * <li><b>headers</b> - header values.</li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: params -->
 *
 * <b>Example:</b>
 *
 * <pre><!-- START SNIPPET: example -->
 * &lt;result name="success" type="httpheader"&gt;
 *   &lt;param name="status"&gt;204&lt;/param&gt;
 *   &lt;param name="headers.a"&gt;a custom header value&lt;/param&gt;
 *   &lt;param name="headers.b"&gt;another custom header value&lt;/param&gt;
 * &lt;/result&gt;
 * <!-- END SNIPPET: example --></pre>
 *
 * @author Jason Carreira
 * @author tmjee
 * @version $Date$ $Id$
 */
public class HttpHeaderResult extends AbstractHttpHeaderPopulatingResult {

    public static final String DEFAULT_PARAM = "status";


    private int status = -1;


    /**
     * Sets whether or not the HTTP header values should be evaluated against the ValueStack (by default they are).
     *
     * @param parse <tt>true</tt> if HTTP header values should be evaluated agains the ValueStack, <tt>false</tt>
     *              otherwise.
     */
    public void setParse(boolean parse) {
        this.parse = parse;
    }

    /**
     * Sets the http servlet response status code that should be set on a response.
     *
     * @param status the Http status code
     * @see javax.servlet.http.HttpServletResponse#setStatus(int)
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Sets the optional HTTP response status code and also re-sets HTTP headers after they've
     * been optionally evaluated against the ValueStack.
     *
     * @param finalLocation
     * @param invocation an encapsulation of the action execution state.
     * @throws Exception if an error occurs when re-setting the headers.
     */
    protected void afterHttpHeadersPopulatedExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();

        if (status != -1) {
            response.setStatus(status);
        }

        
    }
}
