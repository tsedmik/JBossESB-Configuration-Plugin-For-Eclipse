package org.jboss.jbossesb.eclipse.plugin.view.command;

import org.eclipse.gef.commands.Command;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

/**
 * Command that delete a provider or a service
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-04-06
 */
public class DeleteCommand extends Command {

	private XMLElement element;
	private XMLDocument document;

	@Override
	public void execute() {
		document.removeElement(element);
	}

	@Override
	public void undo() {
		
		if (element.getAddress().startsWith("/jbossesb/providers")) {
			document.addProvider(element);
		} else {
			document.addService(element);
		}
	}

	public void setElement(XMLElement element) {
		this.element = element;
	}

	public void setDocument(XMLDocument document) {
		this.document = document;
	}
}
