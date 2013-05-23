package org.jboss.jbossesb.eclipse.plugin.view.command;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.jboss.jbossesb.eclipse.plugin.controller.XMLElementManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.EditDialog;

/**
 * Command that creates new provider
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-23 
 */
public class CreateProviderCommand extends Command {

	private XMLElement provider;
	private Point location;
	private XMLDocument document;

	@Override
	public void execute() {
			
		// open editing dialog
		Dialog dialog = new EditDialog(new Shell(), provider);
    	if(dialog.open() == Window.OK) {
    		
    		// set position
    		if(location != null) {
    			int height = 28 + XMLElementManipulator.getBuses(provider).size() * 28;
				Rectangle layout = new Rectangle(location.x, location.y, 300, height);
				provider.setRectangle(layout);
    		}
    		
    		document.addProvider(provider);
    	}
	}

	@Override
	public void undo() {
		document.removeElement(provider);
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public void setParent(XMLDocument document) {
		this.document = document;
	}

	public void setThing(XMLElement provider) {
		this.provider = provider;
	}

}
