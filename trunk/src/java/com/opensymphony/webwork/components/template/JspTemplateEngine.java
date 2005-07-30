package com.opensymphony.webwork.components.template;

import com.opensymphony.webwork.views.jsp.IncludeTag;
import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.PageContext;

/**
 * JspTemplateEngine
 * Date: Sep 28, 2004 12:01:59 PM
 *
 * @author jcarreira
 */
public class JspTemplateEngine extends BaseTemplateEngine {
    private static final Log LOG = LogFactory.getLog(JspTemplateEngine.class);

    public void renderTemplate(TemplateRenderingContext templateContext) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rendering template '" + templateContext.getTemplateName() + "'");
        }
        UIBean tag = templateContext.getTag();
        OgnlValueStack stack = templateContext.getStack();
        stack.push(tag);
        PageContext pageContext = (PageContext) stack.getContext().get(ServletActionContext.PAGE_CONTEXT);
        IncludeTag.include(templateContext.getTemplateName(), pageContext);
        stack.pop();
    }

    protected String getSuffix() {
        return "jsp";
    }
}
