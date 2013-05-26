package org.jboss.jbossesb.eclipse.plugin.view.part;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.jboss.jbossesb.eclipse.plugin.controller.XMLElementManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.figure.ProviderBusFigure;
import org.jboss.jbossesb.eclipse.plugin.view.figure.ProviderCoverFigure;
import org.jboss.jbossesb.eclipse.plugin.view.policy.CommonObjectComponentEditPolicy;
import org.jboss.jbossesb.eclipse.plugin.view.policy.CommonObjectGraphicalNodeEditPolicy;

/**
 * Controller for a figure of JBossESB provider (ProviderFigure).
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-29
 */
public class ProviderPart extends CommonObjectPart {
	
	private Random rand = new Random();
	
	@Override
	protected IFigure createFigure() {
		return new ProviderCoverFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new CommonObjectComponentEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new CommonObjectGraphicalNodeEditPolicy());
	}
	
	@Override
	protected void refreshVisuals() {
		ProviderCoverFigure figure = (ProviderCoverFigure) getFigure();
		XMLElement model = (XMLElement) getModel();
		EditorPart parent = (EditorPart) getParent();
		
		// set label - "provider name (type)"
		String name = XMLElementManipulator.getAttrValue(model, "name");
		figure.getProvider().getLabel().setText(name + " (" + model.getName() + ")");
		
		// set buses
		List<XMLElement> busesData = XMLElementManipulator.getBuses(model);
		List<ProviderBusFigure> buses = new ArrayList<ProviderBusFigure>();
		for (XMLElement busData : busesData) {
			
			ProviderBusFigure bus = new ProviderBusFigure();
			if (busData.getName() != null) {
				bus.getLabel().setText(XMLElementManipulator.getAttrValue(busData, "busid"));
			}
			buses.add(bus);
		}
		figure.getProvider().setBuses(buses);
		
		// set size of figure
		Rectangle layout = model.getRectangle();
		if (layout == null) {
			layout = new Rectangle(rand.nextInt(300), rand.nextInt(300), 300, 28 + busesData.size() * 28);
			model.setRectangle(layout);
		}
	    parent.setLayoutConstraint(this, figure, layout);
	}
	
}
