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

//TODO Add JavaDoc
public class ESBEditorXYLayoutPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command retVal = null;
		// TODO adding more objects than providers only! (distinctions based on address)
		if (request.getNewObject() instanceof XMLElement) {
			CreateProviderCommand command = new CreateProviderCommand();
			command.setLocation(request.getLocation());
			command.setParent((XMLDocument) (getHost().getModel()));
			command.setThing((XMLElement) (request.getNewObject()));
			retVal = command;
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
