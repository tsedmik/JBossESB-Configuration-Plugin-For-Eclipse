package org.jboss.jbossesb.eclipse.plugin.view.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.policy.ESBEditorXYLayoutPolicy;

/**
 * Controller for the whole graphical editor.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-07
 */
public class EditorPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		FreeformLayer layer = new FreeformLayer();
		layer.setLayoutManager(new FreeformLayout());
		layer.setBorder(new LineBorder(1));
		return layer;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ESBEditorXYLayoutPolicy());
	}

	@Override
	protected List<XMLElement> getModelChildren() {
		XMLDocument document = (XMLDocument) getModel();
		List<XMLElement> objects = new ArrayList<XMLElement>();
		objects.add(document.getJbossesb());
		if (document.getProviders() != null) {
			objects.addAll(document.getProviders());
		}
		if (document.getServices() != null) {
			objects.addAll(document.getServices());
		}
		if (document.getGlobals() != null) {
			objects.add(document.getGlobals());
		}

		return objects;
	}

	@Override
	public void activate() {
		if(!isActive()) {
			((XMLDocument) getModel()).addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		((XMLDocument) getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refreshChildren();
	}
}
