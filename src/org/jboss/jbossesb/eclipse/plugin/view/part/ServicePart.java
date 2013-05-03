package org.jboss.jbossesb.eclipse.plugin.view.part;

import java.util.List;
import java.util.Random;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.jboss.jbossesb.eclipse.plugin.controller.XMLElementManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.figure.ProviderBusFigure;
import org.jboss.jbossesb.eclipse.plugin.view.figure.ServiceFigure;
import org.jboss.jbossesb.eclipse.plugin.view.policy.CommonObjectComponentEditPolicy;
import org.jboss.jbossesb.eclipse.plugin.view.policy.CommonObjectGraphicalNodeEditPolicy;

/**
 * TODO JavaDoc
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-25
 */
public class ServicePart extends CommonObjectPart implements NodeEditPart {

	private Random rand = new Random();

	@Override
	protected IFigure createFigure() {
		return new ServiceFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new CommonObjectComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new CommonObjectGraphicalNodeEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		ServiceFigure figure = (ServiceFigure) getFigure();
		XMLElement model = (XMLElement) getModel();
		EditorPart parent = (EditorPart) getParent();

		// set label - service name
		figure.getLabel().setText(
				XMLElementManipulator.getAttrValue(model, "name"));

		// set actions
		// TODO do it better. It is not necessary to remove all actions during
		// every repaint.
		figure.getActions().removeAll();
		List<XMLElement> actions = XMLElementManipulator.getActions(model);
		if (actions.isEmpty()) {
			Label label = new Label();
			label.setText("--- No Actions ---");
			label.setFont(new Font(null, "Arial", 12, SWT.ITALIC));
			figure.getActions().add(label);
		} else {
			int counter = 1;
			for (XMLElement action : actions) {

				// extract name
				String name = XMLElementManipulator
						.getAttrValue(action, "name");
				figure.getActions().add(new Label(counter + ". " + name));
				counter++;
			}
		}

		// set size and position
		Rectangle layout = model.getRectangle();
		if (layout == null) {
			layout = new Rectangle(rand.nextInt(300), rand.nextInt(300), 200, 50);
			model.setRectangle(layout);
		}
		parent.setLayoutConstraint(this, figure, layout);
	}

	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return ((ServiceFigure) getFigure()).getConnectionAnchor();
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return ((ProviderBusFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return ((ServiceFigure) getFigure()).getConnectionAnchor();
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return ((ProviderBusFigure) getFigure()).getConnectionAnchor();
	}

}
