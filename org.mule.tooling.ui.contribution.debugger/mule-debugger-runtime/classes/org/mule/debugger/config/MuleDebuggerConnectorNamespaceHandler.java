
package org.mule.debugger.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>debugger</code>.
 * 
 */
public class MuleDebuggerConnectorNamespaceHandler
    extends NamespaceHandlerSupport
{


    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        registerBeanDefinitionParser("config", new MuleDebuggerConnectorConfigDefinitionParser());
        registerBeanDefinitionParser("breakpoint", new BreakpointDefinitionParser());
    }

}
