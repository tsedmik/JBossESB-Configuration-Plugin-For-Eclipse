package org.jboss.jbossesb.eclipse.plugin.view.command;

import org.eclipse.draw2d.geometry.Point;
//import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

//TODO Add JavaDoc
public class CreateProviderCommand extends Command {

	private static final String defaultName = "<...>";

	private XMLElement provider;
	// TODO How to set position of figure?
	//private Rectangle constraints;
	private XMLDocument document;

	@Override
	public void execute() {
		provider.setName(defaultName);
		provider.setAddress("/jbossesb/providers/jms-provider");
		document.addProvider(provider);
	}

	@Override
	public void undo() {
		document.removeElement(provider);
	}

	public void setLocation(Point location) {
		//constraints = new Rectangle(location, defaultDimension);
	}

	public void setParent(XMLDocument document) {
		this.document = document;
	}

	public void setThing(XMLElement provider) {
		this.provider = provider;
	}

}
