package org.jboss.jbossesb.eclipse.plugin.view.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.jboss.jbossesb.eclipse.plugin.view.figure.JBossESBFigure;

/**
 * Controller for the "Global Settings" figure in the editor.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-26
 */
public class JBossESBPart extends CommonObjectPart {
	
	@Override
	protected IFigure createFigure() {
		return new JBossESBFigure();
	}

	@Override
	protected void createEditPolicies() {
		// no policies needed
	}
	
	@Override
	protected void refreshVisuals() {
		JBossESBFigure figure = (JBossESBFigure) getFigure();
		EditorPart parent = (EditorPart) getParent();
		
		figure.getLabel().setText("Global Settings");
	    Rectangle layout = new Rectangle(2, 2, 100, 30);
	    parent.setLayoutConstraint(this, figure, layout);
	}
}
