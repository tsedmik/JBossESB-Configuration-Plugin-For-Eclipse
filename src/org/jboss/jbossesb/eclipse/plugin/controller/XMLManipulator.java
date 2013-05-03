package org.jboss.jbossesb.eclipse.plugin.controller;

import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;

/**
 * The class allows load and store JBossESB configuration file 
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2012-10-02
 */
public interface XMLManipulator {

	/**
	 * Load whole XML document into a memory for further processing
	 * 
	 * @return Set of JBossESB configuration
	 * @throws XMLException
	 */
	XMLDocument loadConfiguration() throws XMLException;
	
	/**
	 * Save JBossESB configuration into a file
	 * 
	 * @param document JBossESB configuration file
	 * @throws XMLException
	 */
	void saveConfiguration(XMLDocument document) throws XMLException;
}
