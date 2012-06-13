package org.mule.tooling.debugger.application

import org.eclipse.equinox.app.IApplication
import org.eclipse.equinox.app.IApplicationContext
import org.eclipse.ui.PlatformUI;

class Application extends IApplication {
  @throws(classOf[Exception])
  def start(context: IApplicationContext): Object = {
    val display = PlatformUI.createDisplay();
    try {
      val returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdivsor());
      if (returnCode == PlatformUI.RETURN_RESTART)
        return IApplication.EXIT_RESTART;
      else
        return IApplication.EXIT_OK;
    } finally {
      display.dispose();
    }
  }

  def stop(): Unit = {
   val workbench = PlatformUI.getWorkbench();
        if (workbench == null)
            return;
        val display = workbench.getDisplay();
        display.syncExec(new Runnable() {
            def run() : Unit = {
                if (!display.isDisposed())
                    workbench.close();
            }
        });
  }

}