
package org.mule.tooling.ui.contribution.debugger.view.impl;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.part.ViewPart;
import org.mule.tooling.ui.contribution.debugger.controller.ConnectionPropertiesController;
import org.mule.tooling.ui.contribution.debugger.controller.DebuggerResponseCallback;
import org.mule.tooling.ui.contribution.debugger.controller.MessageDefinitionController;
import org.mule.tooling.ui.contribution.debugger.controller.MuleDebuggerPropertiesController;
import org.mule.tooling.ui.contribution.debugger.controller.MuleDebuggerViewController;
import org.mule.tooling.ui.contribution.debugger.controller.ScriptEvaluationController;
import org.mule.tooling.ui.contribution.debugger.event.EventBus;
import org.mule.tooling.ui.contribution.debugger.view.IMuleDebuggerEditor;
import org.mule.tooling.ui.contribution.debugger.view.actions.ConnectAction;
import org.mule.tooling.ui.contribution.debugger.view.actions.NextStepAction;
import org.mule.tooling.ui.contribution.debugger.view.actions.OpenContributorsAction;
import org.mule.tooling.ui.contribution.debugger.view.actions.ResumeAction;

public class MuleDebuggerView extends ViewPart implements IMuleDebuggerEditor
{

    private ObjectFieldDefinitionComposite muleDebuggerComposite;
    private ConnectionPropertiesEditorComposite connectionEditor;
    private MuleDebuggerPropertiesView debuggerPropertiesView;
    private EventBus eventBus;
    private DebuggerResponseCallback callback;
    private Composite debuggerEditor;
    private StackLayout debuggerEditorLayout;
    
    private CLabel waitingLabel;
    private SashForm messageView ;
    
    

    @Override
    public void createPartControl(Composite parent)
    {
        eventBus = new EventBus();
        callback = new DebuggerResponseCallback(eventBus);
        
        Composite editor = new Composite(parent, SWT.NULL);
        editor.setLayout(new FillLayout());

        debuggerEditor = new Composite(editor, SWT.NULL);
        debuggerEditorLayout = new StackLayout();
        debuggerEditor.setLayout(debuggerEditorLayout);
        
        connectionEditor = new ConnectionPropertiesEditorComposite(debuggerEditor, SWT.NULL);
        connectionEditor.setLayoutData(new GridData(GridData.FILL_BOTH));
        new ConnectionPropertiesController(connectionEditor, eventBus);
        
        waitingLabel = new CLabel(debuggerEditor, SWT.CENTER);
        waitingLabel.setText("Connected with mule ESB.\nWaiting for a mule message to arrive!!!");
        messageView = new SashForm(debuggerEditor, SWT.NULL);
        
        final Group messageGroup = new Group(messageView,SWT.NULL);
        messageGroup.setText("Message");
        messageGroup.setLayout(new FillLayout());
        muleDebuggerComposite = new ObjectFieldDefinitionComposite(messageGroup, SWT.NULL);
        muleDebuggerComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        new MessageDefinitionController(muleDebuggerComposite, eventBus);

        final Group propertiesGroup = new Group(messageView,SWT.NULL);
        propertiesGroup.setText("Properties");
        propertiesGroup.setLayout(new FillLayout());
        debuggerPropertiesView = new MuleDebuggerPropertiesView(propertiesGroup, SWT.NULL);
        new MuleDebuggerPropertiesController(debuggerPropertiesView, eventBus);
        
        final Group evalGroup = new Group(messageView,SWT.NULL);
        evalGroup.setText("Evaluate");
        evalGroup.setLayout(new FillLayout());
        ScriptEvaluationComposite scriptEvaluationComposite = new ScriptEvaluationComposite(evalGroup,
            SWT.NULL);
        scriptEvaluationComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        new ScriptEvaluationController(scriptEvaluationComposite, eventBus);

        
        

        createToolBar();

        new MuleDebuggerViewController(this, eventBus);
    }

    public void createToolBar()
    {
        IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
        ConnectAction connectAction = new ConnectAction(eventBus, callback);
        connectAction.setConnectionProperties(connectionEditor);
        mgr.add(connectAction);
        mgr.add(new ResumeAction(eventBus));
        mgr.add(new NextStepAction(eventBus));
        mgr.add(new OpenContributorsAction());
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
                debuggerEditorLayout.topControl = connectionEditor;
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
                debuggerEditorLayout.topControl = messageView;
                
            }
            else
            {
                debuggerEditorLayout.topControl = waitingLabel;
            }
            debuggerEditor.layout();
        }
        
    }

    
    

}
