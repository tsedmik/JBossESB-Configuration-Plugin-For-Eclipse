package org.jboss.jbossesb.eclipse.plugin.view;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.actions.ActionFactory;

/**
 * Action Bar
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-04-06
 */
public class GraphicalEditorActionBarContributor extends ActionBarContributor {

	@Override
	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
	    addRetargetAction(new RedoRetargetAction());
	    addRetargetAction(new DeleteRetargetAction());
	}

	@Override
	protected void declareGlobalActionKeys() {
		// TODO Auto-generated method stub
	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
	    toolBarManager.add(getAction(ActionFactory.REDO.getId()));
	    toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
	}
}
