
package org.mule.tooling.ui.contribution.debugger.controller;

import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.view.IConnectionPropertiesEditor;

public class ConnectionPropertiesController
{

    private IConnectionPropertiesEditor connectionProperties;
    private EventBus eventBus;

    public ConnectionPropertiesController(IConnectionPropertiesEditor connectionProperties, EventBus eventBus)
    {
        super();
        this.connectionProperties = connectionProperties;
        this.eventBus = eventBus;
        bind();
    }

    protected void bind()
    {
        this.connectionProperties.setURL("localhost");
        this.connectionProperties.setPort(6666);
    }

}
