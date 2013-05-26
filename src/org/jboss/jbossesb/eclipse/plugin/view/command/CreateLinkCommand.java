package org.jboss.jbossesb.eclipse.plugin.view.command;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.jboss.jbossesb.eclipse.plugin.controller.PropertiesManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLAttribute;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.AttributeValues;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.EditDialog;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.ElementValues;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.ListenersDialog;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.ProvidersDialog;

/**
 * Perform add a connection.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-24
 */
public class CreateLinkCommand extends Command {

	private XMLElement source;
	private XMLElement target;
	private StringBuilder bus = new StringBuilder();

	@Override
	public boolean canExecute() {

		return source != null && target != null;
	}

	@Override
	public void execute() {

		setBus();
		if (bus.length() > 0) {
			setListener();
		}
		
		super.execute();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		super.undo();
	}

	public void setSource(XMLElement source) {

		// allow only providers
		if (source.getAddress().startsWith("/jbossesb/providers")) {
			this.source = source;
		}
	}

	public void setTarget(XMLElement target) {

		// allow only services
		if (target.getAddress().startsWith("/jbossesb/services")) {
			this.target = target;
		}
	}

	/**
	 * Sets bus from source or target (provider).
	 */
	private void setBus() {

		Dialog dialog = null;

		if (source.getAddress().startsWith("/jbossesb/providers")) {
			dialog = new ProvidersDialog(new Shell(), source, bus);
		} else {
			dialog = new ProvidersDialog(new Shell(), target, bus);
		}

		dialog.open();

	}

	/**
	 * Sets a listener from source of target (service)
	 */
	private void setListener() {
		
		// which is a service
		XMLElement service = null;
		if (source.getAddress().startsWith("/jbossesb/services")) {
			service = source;
		} else {
			service = target;
		}
		
		// TODO choose right listener (based on provider's type)
		StringBuilder listener = new StringBuilder();
		Dialog dialog = new ListenersDialog(new Shell(), listener);
		if (dialog.open() != Window.OK) {
			return;
		}

		// check if exists listeners section
		boolean listenersExists = false;
		for (XMLElement child : service.getChildren()) {
			if (child.getAddress().equals("/jbossesb/services/service/listeners")) {
				listenersExists = true;
			}
		}
		
		// create listeners section (if doesn't exists)
		if (!listenersExists) {
			XMLElement xmlListeners = new XMLElement();
			xmlListeners.setAddress("/jbossesb/services/service/listeners");
			xmlListeners.setName("Listeners");
			service.addChildElement(xmlListeners);
		}
		
		// create listener in service
		String address = "/jbossesb/services/service/listeners/" + listener;
		PropertiesManipulator prop = new PropertiesManipulator(address);
		XMLElement xmlListener = new XMLElement();
		xmlListener.setAddress("/jbossesb/services/service/listeners/" + listener);
		xmlListener.setName(prop.getSomeElementValue(address, ElementValues.NAME));
		xmlListener.setHint(prop.getSomeElementValue(address, ElementValues.HINT));
		dialog = new EditDialog(new Shell(), xmlListener);
		if (dialog.open() != Window.OK) {
			undo();
			return;
		}
		
		// set busid
		XMLAttribute busid = xmlListener.getAttribute("busidref");
		if (busid == null) {
			String name = prop.getSomeAttributeValue(address + "\busidref", AttributeValues.NAME);
			String type = prop.getSomeAttributeValue(address + "\busidref", AttributeValues.TYPE);
			String hint = prop.getSomeAttributeValue(address + "\busidref", AttributeValues.HINT);
			busid = new XMLAttribute(type, false, null, bus.toString(), name, hint, "busidref");
			xmlListener.addAttribute(busid);
		} else {
			busid.setValue(bus.toString());
		}
		
		
		// add to the model
		for (XMLElement child : service.getChildren()) {
			if (child.getAddress().equals("/jbossesb/services/service/listeners")) {
				child.addChildElement(xmlListener);
				service.refresh();
				break;
			}
		}
	}
}
