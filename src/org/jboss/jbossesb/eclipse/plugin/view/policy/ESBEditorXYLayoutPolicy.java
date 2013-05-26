package org.jboss.jbossesb.eclipse.plugin.view.policy;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.command.ChangeConstraintCommand;
import org.jboss.jbossesb.eclipse.plugin.view.command.CreateProviderCommand;
import org.jboss.jbossesb.eclipse.plugin.view.command.CreateServiceCommand;

/**
 * Default policy that create objects and perform their movement.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-23
 */
public class ESBEditorXYLayoutPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		
		Command retVal = null;
		Object object = request.getNewObject();
		
		if (object instanceof XMLElement) {
			
			XMLElement temp = (XMLElement) object;
			
			// provider
			if (temp.getAddress().startsWith("/jbossesb/providers")) {
				CreateProviderCommand command = new CreateProviderCommand();
				command.setLocation(request.getLocation());
				command.setParent((XMLDocument) (getHost().getModel()));
				command.setThing((XMLElement) (request.getNewObject()));
				retVal = command;
			}
			
			// service
			else if (temp.getAddress().startsWith("/jbossesb/services")) {
				CreateServiceCommand command = new CreateServiceCommand();
				command.setLocation(request.getLocation());
				command.setDocument((XMLDocument) (getHost().getModel()));
				command.setService((XMLElement) (request.getNewObject()));
				retVal = command;
			}
		}
		
		return retVal;
	}
	
	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		ChangeConstraintCommand command = new ChangeConstraintCommand();
		command.setModel((XMLElement) child.getModel());
		command.setNewConstraint((Rectangle) constraint);
		return command;
	}
	
}
