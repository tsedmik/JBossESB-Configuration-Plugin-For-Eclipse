package org.jboss.jbossesb.eclipse.plugin.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.jboss.jbossesb.eclipse.plugin.view.dialog.AttributeValues;
import org.jboss.jbossesb.eclipse.plugin.view.dialog.ElementValues;

/**
 * Class provide a set of methods for easy access to the properties files
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com	
 * @since 2012-10-21
 */
public class PropertiesManipulator {
	
	private ResourceBundle resource;
	
	public PropertiesManipulator(String address) {
		super();
		resource = getResourceBundle(address);
	}
	
	public ResourceBundle getResource() {
		return resource;
	}
	
	private static ResourceBundle getResourceBundle(String address) {
		
		if (address == null) {
			return null;
		}
		
		if (address.startsWith("/jbossesb/globals")) {
			 return ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model.globals");
		} else if (address.startsWith("/jbossesb/services/service")) {
			
			// listeners
			if (address.contains("listeners/")) {				
				String[] temp = address.split("/");
				return ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model." + temp[5]);
			} else {
				return ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model.service");
			}
		} else if (address.startsWith("/jbossesb/providers/")) {
			String[] temp = address.split("/");
			return ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model." + temp[3]);
		} else {
			return ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model.jbossesb");
		}
	}

	/**
	 * Method converts data stored in Resource Bundle into a Map 
	 * 
	 * @param resource
	 * @return map representation of resource bundle
	 */
	public static Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
		
		if (resource == null) {
			return null;
		}
	    
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> keys = resource.getKeys();
		
	    while (keys.hasMoreElements()) {
	    	String key = keys.nextElement();
	    	map.put(key, resource.getString(key));
	    }

	    return map;
	}
	
	/**
	 * Method returns only element's records from given Map (that represents data from properties file)
	 * 
	 * @param data Map that represents data from properties file
	 * @return Map of element's records
	 */
	public static Map<String, String> getElementsFromMap(Map<String, String> data) {
		
		Map<String, String> temp = new HashMap<String, String>();
		
		if (data == null) {
			return temp;
		}
		
		Iterator<String> iterator = data.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = data.get(key);
			
			if (value.startsWith("E#")) {
				temp.put(key, value);
			}
		}
		
		return temp;
	}
	
	/**
	 * Method returns only attributes of given element
	 * 
	 * @param data Map that represents data from properties file
	 * @param element key value of element
	 * @return Map of attributes of given element
	 */
	public static Map<String, String> getAttributesToElement(Map<String, String> data, String element) {
		
		Map<String, String> temp = new HashMap<String, String>();
		
		if (data == null || element == null) {
			return temp;
		}
		
		Iterator<String> iterator = data.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = data.get(key);
			
			if (value.startsWith("A#") && key.startsWith(element) && (key.split("/").length == element.split("/").length + 1)) {
				temp.put(key, value);
			}
		}
		
		return temp;
	}
	
	/**
	 * Method decides based on address string which properties file should be used and extract
	 * required data about element and its attributes
	 * 
	 * @param address address string in form "element/element/.../element"
	 * @return map which contains only element with key equals to the address and its attributes 
	 */
	public static Map<String, String> extractConfiguration(String address) {
		
		if (address == null) return null;
		
		ResourceBundle bundle = getResourceBundle(address);
		
		// fill up 
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> bundleMap = PropertiesManipulator.convertResourceBundleToMap(bundle);
		String bundleAddress = bundleMap.get("address");
		Iterator<String> iterator = bundleMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = bundleMap.get(key);
			key = bundleAddress.concat(key);
			
			// elements or attributes
			if (value.startsWith("E#")) {
				if (key.equals(address)) {
					result.put(key, value);
				}
			} else if (value.startsWith("A#")) {
				if ( key.startsWith(address) &&
					 key.split("/").length == address.split("/").length + 1) {
					result.put(key, value);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Change a Map with properties in such way that remove 'address' key and add its value
	 * to every key ('address' + 'key value').
	 * 
	 * @param properties A Map with properties.
	 * @return A new Map.
	 */
	public static Map<String, String> alterProperties(Map<String, String> properties) {
		
		if (properties == null) {
			return null;
		}
		
		Map<String, String> temp = new HashMap<String, String>();
		String address = properties.get("address");
		properties.remove("address");
		Iterator<String> iterator = properties.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = properties.get(key);
			
			temp.put(address + key, value);
		}
		
		return temp;
	}
	
	/**
	 * Return the requested attribute value of the attribute set with address.
	 *  
	 * @param address XML address of element (same format as in properties files).
	 * @param value requested value.
	 * @return type if attribute exists or null otherwise.
	 */	
	public String getSomeAttributeValue(String address, AttributeValues value) {
		
		// input control
		if (address == null || resource == null) {
			return null;
		}
		
		String shortAddress = address.substring(resource.getString("address").length());
		if (!resource.containsKey(shortAddress)) {
			return null;
		}
				
		String[] temp = resource.getString(shortAddress).split("#");
				
		return temp[value.ordinal()];
	}
	
	/**
	 * Return the requested element's value of the element set with address.
	 *  
	 * @param address XML address of element (same format as in properties files).
	 * @param value requested value.
	 * @return type if element exists or null otherwise.
	 */	
	public String getSomeElementValue(String address, ElementValues value) {
		
		// input control
		if (address == null || resource == null) {
			return null;
		}
				
		String shortAddress = address.substring(resource.getString("address").length());
		if (!resource.containsKey(shortAddress)) {
			return null;
		}
						
		String[] temp = resource.getString(shortAddress).split("#");
						
		return temp[value.ordinal()];
	}
	
	/**
	 * Return map contains only elements direct under element (set by address).
	 * Must be called on data altered with method 'alterProperties'.
	 * 
	 * @param data properties file transformed into map.
	 * @param address address of element.
	 * @return a map with element direct under given element. Empty map in case
	 * element doesn't exists, no child elements exits, null input.
	 */
	public static Map<String, String> getElementsUnderElement(Map<String, String> data, String address) {
		
		Map<String, String> tempElements = new HashMap<String, String>();
		
		// input control
		if (data == null || address == null) {
			return tempElements;
		}
		
		for (Map.Entry<String, String> entry : data.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			int addressLength = address.split("/").length;
			int keyLength = key.split("/").length;
			
			if (key.startsWith(address) && (addressLength + 1 == keyLength)) {
				tempElements.put(key, value);
			}
		}
		
		return tempElements;
	}
	
}
