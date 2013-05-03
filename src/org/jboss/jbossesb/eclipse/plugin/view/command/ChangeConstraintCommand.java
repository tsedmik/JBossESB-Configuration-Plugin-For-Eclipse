package org.jboss.jbossesb.eclipse.plugin.view.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

/**
 * Command that resize GUI objects
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-04-06
 */
public class ChangeConstraintCommand extends Command {
	
	private Rectangle oldConstraint;
	private Rectangle newConstraint;
	private XMLElement model;
	
	@Override
	public void execute() {
		if (oldConstraint == null) {
			oldConstraint = model.getRectangle();
		}
		model.setRectangle(newConstraint);
	}
	
	@Override
	public void undo() {
		model.setRectangle(oldConstraint);
	}

	public void setNewConstraint(Rectangle newConstraint) {
		this.newConstraint = newConstraint;
	}

	public void setModel(XMLElement model) {
		this.model = model;
	}
}
