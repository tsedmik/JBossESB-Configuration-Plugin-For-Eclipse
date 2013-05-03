package org.jboss.jbossesb.eclipse.plugin.view.factory;

import org.eclipse.gef.requests.CreationFactory;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

//TODO Add JavaDoc
public class ProviderFactory implements CreationFactory {
	
	public ProviderFactory(String provider) {
		super();
		// TODO set correct provider
	}

	@Override
	public Object getNewObject() {
		
		// TODO set some basic attributes - address
		XMLElement provider = new XMLElement();
		provider.setAddress("/jbossesb/providers");
		
		return provider;
	}

	@Override
	public Object getObjectType() {
		return XMLElement.class;
	}
}
