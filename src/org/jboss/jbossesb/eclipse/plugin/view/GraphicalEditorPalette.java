package org.jboss.jbossesb.eclipse.plugin.view;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.jboss.jbossesb.eclipse.plugin.controller.PropertiesManipulator;
import org.jboss.jbossesb.eclipse.plugin.view.factory.LinkFactory;
import org.jboss.jbossesb.eclipse.plugin.view.factory.ProviderFactory;
import org.jboss.jbossesb.eclipse.plugin.view.factory.ServiceFactory;

/**
 * Class represents a tool palette (for creation new objects).
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-04-05
 */
public class GraphicalEditorPalette extends PaletteRoot {

	PaletteGroup head;
	PaletteGroup provider;
	PaletteGroup service;
	PaletteGroup connection;

	public GraphicalEditorPalette() {
		addGroup();
		addSelectionTool();
		addProviderTool();
		addServiceTool();
		addConnectionTool();
	}
	
	/**
	 * Create a separation of the palette.
	 */
	private void addGroup() {
		head = new PaletteGroup("General Controls");
		add(head);
		add(new PaletteSeparator());
		provider = new PaletteGroup("Provider Controls");
		add(provider);
		add(new PaletteSeparator());
		service = new PaletteGroup("Service Controls");
		add(service);
		add(new PaletteSeparator());
		connection = new PaletteGroup("Listeners Controls");
		add(connection);
		
	}

	/**
	 * Adds a selection tool.
	 */
	private void addSelectionTool() {
		SelectionToolEntry entry = new SelectionToolEntry();
		head.add(entry);
		setDefaultEntry(entry);
	}

	/**
	 * Adds a providers.
	 */
	private void addProviderTool() {
		
		ResourceBundle bundle = ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model.providers");
		Map<String, String> providers = PropertiesManipulator.convertResourceBundleToMap(bundle);
		Set<String> keys = providers.keySet();
		
		for (String key : keys) {
			String value = providers.get(key);
			CreationToolEntry entry = new CreationToolEntry(value, "Create a new " + value + " Provider", new ProviderFactory(key), null, null);
			provider.add(entry);
		}
	}
	
	/**
	 * Add a service.
	 */
	private void addServiceTool() {
		CreationToolEntry entry = new CreationToolEntry("Service", "Create a new Service", new ServiceFactory(), null, null);
		service.add(entry);
	}
	
	/**
	 * Add a connection.
	 */
	private void addConnectionTool() {
		ConnectionCreationToolEntry entry = new ConnectionCreationToolEntry("Connection", "Create a new Connection", new LinkFactory(), null, null);
		connection.add(entry);
	}
}
