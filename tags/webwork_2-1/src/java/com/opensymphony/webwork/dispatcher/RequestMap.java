/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.io.Serializable;

import java.util.*;

import javax.servlet.http.HttpServletRequest;


/**
 * A simple implementation of the {@link java.util.Map} interface to handle a collection of request attributes.
 *
 * @author Patrick Lightbody
 * @author Bill Lynch (docs)
 */
public class RequestMap extends AbstractMap implements Serializable {
    //~ Instance fields ////////////////////////////////////////////////////////

    HashSet entrySet = new HashSet();

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Enumerates over all request attributes and creates a simple Map of attributes.
     *
     * @param request the http servlet request.
     */
    public RequestMap(final HttpServletRequest request) {
        for (Enumeration attrNames = request.getAttributeNames();
                attrNames.hasMoreElements();) {
            final String attrName = (String) attrNames.nextElement();
            entrySet.add(new Map.Entry() {
                    public Object getKey() {
                        return attrName;
                    }

                    public Object getValue() {
                        return request.getAttribute(attrName);
                    }

                    public Object setValue(Object value) {
                        request.setAttribute(attrName, value);

                        return value;
                    }

                    public int hashCode() {
                        return ((getKey() == null) ? 0 : getKey().hashCode()) ^ ((getValue() == null) ? 0 : getValue().hashCode());
                    }

                    public boolean equals(Object obj) {
                        if ((obj != null) && obj instanceof Entry) {
                            Entry me = (Entry) obj;

                            Object key = me.getKey();
                            Object value = me.getValue();

                            if (key == null) {
                                return false;
                            }

                            if (value == null) {
                                if (getValue() != null) {
                                    return false;
                                } else {
                                    return key.equals(getKey());
                                }
                            }

                            return key.equals(getKey()) && value.equals(getValue());
                        }

                        return false;
                    }
                });
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns a Set of all request attributes.
     *
     * @return a Set of all request attributes.
     */
    public Set entrySet() {
        return entrySet;
    }
}