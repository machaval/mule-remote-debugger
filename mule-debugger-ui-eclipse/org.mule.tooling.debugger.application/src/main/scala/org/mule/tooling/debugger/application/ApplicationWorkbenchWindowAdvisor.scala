package org.mule.tooling.debugger.application

import org.eclipse.swt.graphics.Point
import org.eclipse.ui.application.IWorkbenchWindowConfigurer
import org.eclipse.ui.application.WorkbenchWindowAdvisor
import org.eclipse.ui.application.ActionBarAdvisor
import org.eclipse.ui.application.IActionBarConfigurer

class ApplicationWorkbenchWindowAdvisor(configurer : IWorkbenchWindowConfigurer) extends WorkbenchWindowAdvisor(configurer) {
  
  override def createActionBarAdvisor(configurer2 : IActionBarConfigurer) : ActionBarAdvisor  = {
        return new ApplicationActionBarAdvisor(configurer2);
    }

    override def preWindowOpen() : Unit = {
        configurer.setInitialSize(new Point(400, 300));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        configurer.setShowPerspectiveBar(false);
        print("go")
    }
}