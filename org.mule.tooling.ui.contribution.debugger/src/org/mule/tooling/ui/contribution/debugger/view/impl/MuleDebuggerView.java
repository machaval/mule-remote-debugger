
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.ViewPart;
import org.mule.tooling.ui.contribution.debugger.controller.ConnectionPropertiesController;
import org.mule.tooling.ui.contribution.debugger.controller.DebuggerResponseCallback;
import org.mule.tooling.ui.contribution.debugger.controller.MuleDebuggerPayloadController;
import org.mule.tooling.ui.contribution.debugger.controller.MuleDebuggerPropertiesController;
import org.mule.tooling.ui.contribution.debugger.controller.MuleDebuggerViewController;
import org.mule.tooling.ui.contribution.debugger.controller.ScriptEvaluationController;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.view.IMuleDebuggerEditor;
import org.mule.tooling.ui.contribution.debugger.view.actions.ConnectAction;
import org.mule.tooling.ui.contribution.debugger.view.actions.NextMessageAction;

public class MuleDebuggerView extends ViewPart implements IMuleDebuggerEditor
{

    private MuleDebuggerPayloadComposite muleDebuggerComposite;
    private ConnectionPropertiesEditorComposite connectionEditor;
    private MuleDebuggerPropertiesView debuggerPropertiesView;
    private EventBus eventBus;
    private DebuggerResponseCallback callback;
    private Composite debuggerEditor;
    private StackLayout debuggerEditorLayout;
    private CLabel disconnectedLabel;
    private CLabel waitingLabel;
    private TabFolder tabFolder;

    @Override
    public void createPartControl(Composite parent)
    {
        eventBus = new EventBus();
        callback = new DebuggerResponseCallback(eventBus);

        Composite editor = new Composite(parent, SWT.NULL);
        editor.setLayout(new GridLayout());

        connectionEditor = new ConnectionPropertiesEditorComposite(editor, SWT.NULL);
        connectionEditor.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        new ConnectionPropertiesController(connectionEditor, eventBus);

        debuggerEditor = new Composite(editor, SWT.NULL);
        debuggerEditor.setLayoutData(new GridData(GridData.FILL_BOTH));

        debuggerEditorLayout = new StackLayout();
        debuggerEditor.setLayout(debuggerEditorLayout);

        disconnectedLabel = new CLabel(debuggerEditor, SWT.CENTER);
        disconnectedLabel.setText("Debugger is disconnected!!");
        
        waitingLabel = new CLabel(debuggerEditor, SWT.CENTER);
        waitingLabel.setText("Waiting for mule message");
        
        tabFolder = new TabFolder(debuggerEditor, SWT.NULL);
        muleDebuggerComposite = new MuleDebuggerPayloadComposite(tabFolder, SWT.NULL);
        muleDebuggerComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        new MuleDebuggerPayloadController(muleDebuggerComposite, eventBus);
        
        

        TabItem payload = new TabItem(tabFolder, SWT.NULL);
        payload.setText("Payload");
        payload.setControl(muleDebuggerComposite);
        
        
        debuggerPropertiesView = new MuleDebuggerPropertiesView(tabFolder, SWT.NULL);
        new MuleDebuggerPropertiesController(debuggerPropertiesView, eventBus);
        TabItem properties = new TabItem(tabFolder, SWT.NULL);
        properties.setText("Properties");
        properties.setControl(debuggerPropertiesView);

        ScriptEvaluationComposite scriptEvaluationComposite = new ScriptEvaluationComposite(tabFolder,
            SWT.NULL);
        scriptEvaluationComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        new ScriptEvaluationController(scriptEvaluationComposite, eventBus);

        TabItem evaluate = new TabItem(tabFolder, SWT.NULL);
        evaluate.setText("Evaluate");
        evaluate.setControl(scriptEvaluationComposite);

        createToolBar();

        new MuleDebuggerViewController(this, eventBus);
    }

    public void createToolBar()
    {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        mgr.add(new ConnectAction(connectionEditor, eventBus, callback));
        mgr.add(new NextMessageAction(eventBus));
    }

    @Override
    public void setFocus()
    {

    }

    @Override
    public void dispose()
    {
        super.dispose();
        eventBus.cleanAllListeners();
        debuggerEditor = null;
    }

    /*
     * (non-Javadoc)
     * @see org.mule.tooling.ui.contribution.debugger.view.impl.IMuleDebuggerEditor#
     * setMuleMessageDebuggerEnabled(java.lang.Boolean)
     */
    @Override
    public void setDebuggerConnected(Boolean enabled)
    {
        if (debuggerEditor != null)
        {
            if (enabled)
            {
                debuggerEditorLayout.topControl = waitingLabel;
            }
            else
            {
                debuggerEditorLayout.topControl = disconnectedLabel;
            }
            debuggerEditor.layout();
        }

    }

    @Override
    public void setDebuggerWaiting(Boolean waiting)
    {
        if (debuggerEditor != null)
        {
            if (!waiting)
            {
                debuggerEditorLayout.topControl = tabFolder;
            }
            else
            {
                debuggerEditorLayout.topControl = waitingLabel;
            }
            debuggerEditor.layout();
        }
        
    }

}
