package org.jboss.jbossesb.eclipse.plugin.view.factory;

import org.eclipse.gef.requests.CreationFactory;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

/**
 * Factory class for creation of a connection.
 *  
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-24
 */
public class LinkFactory implements CreationFactory {

	@Override
	public Object getNewObject() {
		
		XMLElement service = new XMLElement();
		service.setAddress("/jbossesb/services/service/listeners");
		
		return service;
	}

	@Override
	public Object getObjectType() {
		return XMLElement.class;
	}
	

}
