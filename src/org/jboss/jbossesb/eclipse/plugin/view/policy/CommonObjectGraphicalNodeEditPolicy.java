package org.jboss.jbossesb.eclipse.plugin.view.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.command.CreateLinkCommand;

// TODO add class description
public class CommonObjectGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		CreateLinkCommand result = (CreateLinkCommand) request.getStartCommand();
	    result.setTarget((XMLElement)getHost().getModel());
	    return result;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		CreateLinkCommand result = new CreateLinkCommand();
	    result.setSource((XMLElement)getHost().getModel());
	    request.setStartCommand(result);
	    return result;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest arg0) {
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest arg0) {
		return null;
	}

}
