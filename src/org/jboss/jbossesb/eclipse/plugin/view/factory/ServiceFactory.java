package org.jboss.jbossesb.eclipse.plugin.view.factory;

import org.eclipse.gef.requests.CreationFactory;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

//TODO Add JavaDoc
public class ServiceFactory implements CreationFactory {

	@Override
	public Object getNewObject() {
		
		// TODO set some basic attributes - address
		return new XMLElement();
	}

	@Override
	public Object getObjectType() {
		return XMLElement.class;
	}
}
