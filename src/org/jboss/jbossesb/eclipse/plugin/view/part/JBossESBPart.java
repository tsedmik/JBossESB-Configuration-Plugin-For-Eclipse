package org.jboss.jbossesb.eclipse.plugin.view.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.jboss.jbossesb.eclipse.plugin.view.figure.JBossESBFigure;

public class JBossESBPart extends CommonObjectPart {
	
	@Override
	protected IFigure createFigure() {
		return new JBossESBFigure();
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub	
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
