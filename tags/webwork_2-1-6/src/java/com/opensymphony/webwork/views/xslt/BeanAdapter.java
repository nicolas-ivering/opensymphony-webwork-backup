/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 *         Date: 10.10.2003
 *         Time: 20:08:17
 */
public class BeanAdapter extends DefaultElementAdapter {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Object[] NULLPARAMS = new Object[0];

    /**
     * Cache can savely be static because the cached information is
     * the same for all instances of this class.
     */
    private static Map propertyDescriptorCache;

    //~ Instance fields ////////////////////////////////////////////////////////

    private Log log = LogFactory.getLog(this.getClass());

    //~ Constructors ///////////////////////////////////////////////////////////

    public BeanAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getTagName() {
        return getPropertyName();
    }

    protected List buildChildrenAdapters() {
        List newAdapters = new ArrayList();
        Class type = getValue().getClass();
        PropertyDescriptor[] props = getPropertyDescriptors(getValue());

        if (props.length > 0) {
            for (int i = 0; i < props.length; i++) {
                Method m = props[i].getReadMethod();

                if (m == null) {
                    //FIXME: write only property or indexed access
                    continue;
                }

                String propertyName = props[i].getName();
                Object propertyValue;

                /** 999 white magic hack start 999 **
                 * some property accessors will throw exceptions, e.g. getLocale() in webwork.ActionSupport *grrr*
                 * IMHO property accessors should not have those side effects - meier@meisterbohne.de
                 */
                try {
                    propertyValue = m.invoke(getValue(), NULLPARAMS);
                } catch (Exception e) {
                    continue;
                }

                /** 999 white magic hack end 999 **/
                AdapterNode childAdapter;

                if (propertyValue == null) {
                    childAdapter = getRootAdapter().adaptNullValue(getRootAdapter(), this, propertyName);
                } else {
                    childAdapter = getRootAdapter().adapt(getRootAdapter(), this, propertyName, propertyValue);
                }

                newAdapters.add(childAdapter);

                if (log.isDebugEnabled()) {
                    log.debug(this + " adding adapter: " + childAdapter);
                }
            }
        } else {
            // No properties found
            log.info("Class " + type.getName() + " has no readable properties, " + " trying to adapt " + getPropertyName() + " with ToStringAdapter...");

            //newAdapters.add(new ToStringAdapter(getRootAdapter(), this, getPropertyName(), getValue()));
        }

        return newAdapters;
    }

    /**
     * Caching facade method to Introspector.getBeanInfo(Class, Class).getPropertyDescriptors();
     */
    private synchronized PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        try {
            if (propertyDescriptorCache == null) {
                propertyDescriptorCache = new HashMap();
            }

            PropertyDescriptor[] props = (PropertyDescriptor[]) propertyDescriptorCache.get(bean.getClass());

            if (props == null) {
                log.debug("Caching property descriptor for " + bean.getClass().getName());
                props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
                propertyDescriptorCache.put(bean.getClass(), props);
            }

            return props;
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting property descriptors for " + bean + " : " + e.getMessage());
        }
    }
}