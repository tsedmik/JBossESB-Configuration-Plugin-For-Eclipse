package org.jboss.jbossesb.eclipse.plugin.view.command;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.EditDialog;

/**
 * Command that creates a new service
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-23 
 */
public class CreateServiceCommand extends Command {

	private XMLElement service;
	private Point location;
	private XMLDocument document;

	public void setService(XMLElement service) {
		this.service = service;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public void setDocument(XMLDocument document) {
		this.document = document;
	}

	@Override
	public void execute() {
		
		// open editing dialog
		Dialog dialog = new EditDialog(new Shell(), service);
		if (dialog.open() == Window.OK) {

			// set position
			if (location != null) {
				Rectangle layout = new Rectangle(location.x, location.y, 200, 50);
				service.setRectangle(layout);
			}

			document.addService(service);
		}
	}

	@Override
	public void undo() {
		document.removeElement(service);
	}

}
