package org.jboss.jbossesb.eclipse.plugin.view.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.command.DeleteCommand;

// TODO add class description
public class CommonObjectComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		DeleteCommand deleteCommand = new DeleteCommand();
		deleteCommand.setElement((XMLElement) getHost().getModel());
		deleteCommand.setDocument((XMLDocument) getHost().getParent().getModel());
		return deleteCommand;
	}
}
