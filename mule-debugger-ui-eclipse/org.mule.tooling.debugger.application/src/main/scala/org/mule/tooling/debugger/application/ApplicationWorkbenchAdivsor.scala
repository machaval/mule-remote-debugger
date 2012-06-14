package org.mule.tooling.debugger.application

import org.eclipse.ui.application.WorkbenchWindowAdvisor
import org.eclipse.swt.graphics.Point
import org.eclipse.ui.application.IWorkbenchWindowConfigurer
import org.eclipse.ui.application.ActionBarAdvisor
import org.eclipse.ui.application.IActionBarConfigurer
import org.eclipse.ui.application.ActionBarAdvisor
import org.eclipse.ui.application.WorkbenchAdvisor

class ApplicationWorkbenchAdivsor extends WorkbenchAdvisor {
  
  val PERSPECTIVE_ID = "org.mule.tooling.debugger.perspective";
  
  def createActionBarAdvisor(actionBarConfigurer: IWorkbenchWindowConfigurer): WorkbenchWindowAdvisor = {
	return new ApplicationWorkbenchWindowAdvisor(actionBarConfigurer);
  }

  def getInitialWindowPerspectiveId() : String = {
        return PERSPECTIVE_ID;
    }
}