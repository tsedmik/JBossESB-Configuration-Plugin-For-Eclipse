package org.jboss.jbossesb.eclipse.plugin.view.factory;

import org.eclipse.gef.requests.CreationFactory;
import org.jboss.jbossesb.eclipse.plugin.controller.PropertiesManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.ElementValues;

/**
 * Class create a new XMLElement (provider).
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-23
 */
public class ProviderFactory implements CreationFactory {
	
	private String address = null;
	
	public ProviderFactory(String provider) {
		super();
		address = "/jbossesb/providers/" + provider;
	}

	@Override
	public Object getNewObject() {
		
		XMLElement provider = new XMLElement();
		provider.setAddress(address);
		PropertiesManipulator prop = new PropertiesManipulator(address);
		provider.setName(prop.getSomeElementValue(address, ElementValues.NAME));
		provider.setHint(prop.getSomeElementValue(address, ElementValues.HINT));
		
		return provider;
	}

	@Override
	public Object getObjectType() {
		return XMLElement.class;
	}
}
