package org.jboss.jbossesb.eclipse.plugin.view.factory;

import org.eclipse.gef.requests.CreationFactory;
import org.jboss.jbossesb.eclipse.plugin.controller.PropertiesManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.ElementValues;

/**
 * Class create a new XMLElement (service).
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-23
 */
public class ServiceFactory implements CreationFactory {

	@Override
	public Object getNewObject() {
		
		XMLElement service = new XMLElement();
		service.setAddress("/jbossesb/services/service");
		PropertiesManipulator prop = new PropertiesManipulator(service.getAddress());
		service.setName(prop.getSomeElementValue(service.getAddress(), ElementValues.NAME));
		service.setHint(prop.getSomeElementValue(service.getAddress(), ElementValues.HINT));
		
		return service;
	}

	@Override
	public Object getObjectType() {
		return XMLElement.class;
	}
}
