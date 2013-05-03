package org.jboss.jbossesb.eclipse.plugin.view.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.EditDialog;

/**
 * Common Edit Part extracts mutual methods of every part.
 * Provides an observer's ability. 
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-04-06
 */
public abstract class CommonObjectPart extends AbstractGraphicalEditPart implements PropertyChangeListener {
	
	@Override
	public void activate() {
		if(!isActive()) {
			((XMLElement) getModel()).addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		((XMLElement) getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refreshVisuals();
		refreshSourceConnections();
	    refreshTargetConnections();
	}
	
	@Override
	public void performRequest(Request req) {
		
		// double click on a figure
	    if(req.getType() == RequestConstants.REQ_OPEN) {
	    	Dialog dialog = new EditDialog(new Shell(), (XMLElement)getModel());
	    	if(dialog.open() == Window.OK) {
	    		System.out.println("Your favorite Java UI framework is:");
	        }else{
	            System.out.println("Action cancelled");
	        }
	    } 
	}
}
