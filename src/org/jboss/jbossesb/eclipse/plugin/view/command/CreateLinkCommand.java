package org.jboss.jbossesb.eclipse.plugin.view.command;

import org.eclipse.gef.commands.Command;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

// TODO add class description
public class CreateLinkCommand extends Command {

	private XMLElement source;
	private XMLElement target;
	
	@Override
	public boolean canExecute() {
		return source != null && target != null;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		super.execute();
	}
	
	@Override
	public void undo() {
		// TODO Auto-generated method stub
		super.undo();
	}

	public void setSource(XMLElement source) {
		this.source = source;
	}

	public void setTarget(XMLElement target) {
		this.target = target;
	}
}
