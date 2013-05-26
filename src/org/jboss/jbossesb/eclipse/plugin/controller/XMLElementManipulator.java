package org.jboss.jbossesb.eclipse.plugin.controller;

import java.util.ArrayList;
import java.util.List;

import org.jboss.jbossesb.eclipse.plugin.model.XMLAttribute;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

/**
 * Class facilitate access to XMLElement data
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-04-02
 */
public class XMLElementManipulator {

	/**
	 * Extracts actions from service (XMLElement).
	 * 
	 * @param service Object represents a JBossESB service.
	 * @return List of actions refer to a particular JBossESB service. If no action is present return an empty list. 
	 */
	public static List<XMLElement> getActions(XMLElement service) {
		
		// is a service?
		// TODO more specific control
		if (!service.getAddress().startsWith("/jbossesb/services/")) {
			return new ArrayList<XMLElement>();
		}
		
		// locate actions
		for (XMLElement child : service.getChildren()) {
			if (child.getAddress().equals("/jbossesb/services/service/actions")) {
				return child.getChildren();
			}
		}
		
		return new ArrayList<XMLElement>();
	}
	
	/**
	 * Extracts value of the attribute of given element
	 * 
	 * @param element Object represents a JBossESB service.
	 * @param attribute name of attribute
	 * @return value of the attribute 'name' if exists else 'null'
	 */
	public static String getAttrValue(XMLElement element, String attribute) {
		
		String name = null;
		for (XMLAttribute attr : element.getAttributes()) {
			if (attr.getXmlName().equals(attribute)) {
				name = attr.getValue();
				break;
			}
		}
		return name;
	}
	
	/**
	 * Extracts buses from provider (XMLElement).
	 * 
	 * @param provider Object represents a JBossESB provider.
	 * @return List of buses refer to a particular JBossESB provider. If no bus is present return an empty list. 
	 */
	public static List<XMLElement> getBuses(XMLElement provider) {
		
		// is a provider?
		// TODO more specific control
		if (!provider.getAddress().startsWith("/jbossesb/providers/")) {
			return new ArrayList<XMLElement>();
		}
		
		// locate buses
		List<XMLElement> buses = new ArrayList<XMLElement>();
		for (XMLElement child : provider.getChildren()) {
			
			boolean isBus = false;
			for (XMLAttribute attr : child.getAttributes()) {
				if (attr.getXmlName().equals("busid")) {
					isBus = true;
					break;
				}
			}
			
			if (isBus) {
				buses.add(child);
			}
		}
		
		return buses;
	}
}
