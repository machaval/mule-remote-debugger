
package org.mule.tooling.ui.contribution.debugger.controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.mule.debugger.response.MuleMessageInfo;
import org.mule.tooling.ui.contribution.debugger.controller.events.NewMuleMessageArrivedEvent;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.event.IEventHandler;
import org.mule.tooling.ui.contribution.debugger.view.IMuleDebuggerProperties;

public class MuleDebuggerPropertiesController
{
    private final class MapLabelProvider extends LabelProvider implements ITableLabelProvider
    {

        @Override
        public Image getColumnImage(Object element, int columnIndex)
        {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex)
        {

            switch (columnIndex)
            {
                case 0 :
                    return ((Map.Entry) element).getKey().toString();

                case 1 :
                    return ((Map.Entry) element).getValue().toString();

            }
            return null;
        }

    }

    private final class MapContentProvider implements IStructuredContentProvider
    {
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void dispose()
        {
            // TODO Auto-generated method stub

        }

        @Override
        public Object[] getElements(Object inputElement)
        {
            Map<String, String> properties = (Map<String, String>) inputElement;
            Set<Entry<String, String>> entrySet = properties.entrySet();
            return entrySet.toArray(new Map.Entry[entrySet.size()]);

        }
    }

    private IMuleDebuggerProperties propertiesView;
    private EventBus bus;

    public MuleDebuggerPropertiesController(IMuleDebuggerProperties propertiesView, EventBus bus)
    {
        super();
        this.propertiesView = propertiesView;
        this.bus = bus;
        bind();
    }

    protected void bind()
    {

        IStructuredContentProvider contentProvider = new MapContentProvider();
        
        propertiesView.getInboudProperties().setContentProvider(contentProvider);
        propertiesView.getInboudProperties().setLabelProvider(new MapLabelProvider());
        
        propertiesView.getInvocationProperties().setContentProvider(contentProvider);
        propertiesView.getInvocationProperties().setLabelProvider(new MapLabelProvider());
        
        propertiesView.getSessionProperties().setContentProvider(contentProvider);
        propertiesView.getSessionProperties().setLabelProvider(new MapLabelProvider());
        
        this.bus.registerListener(DebuggerEventType.MULE_MESSAGE_ARRIVED,
            new IEventHandler<NewMuleMessageArrivedEvent>()
            {

                @Override
                public void onEvent(final NewMuleMessageArrivedEvent event)
                {
                    Display.getDefault().asyncExec(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            MuleMessageInfo muleMessageInfo = event.getMuleMessageInfo();
                            propertiesView.getInboudProperties().setInput(
                                muleMessageInfo.getInboundProperties());
                            propertiesView.getInvocationProperties().setInput(
                                muleMessageInfo.getInvocationProperties());
                            propertiesView.getSessionProperties().setInput(
                                muleMessageInfo.getSessionProperties());
                        }
                    });

                }
            });
    }

}
