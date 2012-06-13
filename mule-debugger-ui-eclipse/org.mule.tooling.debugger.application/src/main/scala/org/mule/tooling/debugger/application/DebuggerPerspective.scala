package org.mule.tooling.debugger.application

import org.eclipse.ui.IPerspectiveFactory
import org.eclipse.ui.IPageLayout

class DebuggerPerspective extends IPerspectiveFactory {

  def createInitialLayout(layout: IPageLayout): Unit = {
    layout.setEditorAreaVisible(false);
    layout.addStandaloneView("org.mule.tooling.ui.contribution.debugger.view", false, IPageLayout.TOP, IPageLayout.RATIO_MAX, IPageLayout.ID_EDITOR_AREA)
  }

}