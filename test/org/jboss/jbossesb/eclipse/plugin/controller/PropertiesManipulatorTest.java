package org.jboss.jbossesb.eclipse.plugin.controller;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;

public class PropertiesManipulatorTest {
	
	@Test
	public void testExtractConfiguration() {
		
		assertNull(PropertiesManipulator.extractConfiguration(null));
		assertEquals(0, PropertiesManipulator.extractConfiguration("ABCD").size());
		assertEquals(0, PropertiesManipulator.extractConfiguration("ABCD/ABCD").size());		
		assertTrue(PropertiesManipulator.extractConfiguration("/jbossesb").size() > 0);
		assertEquals(0, PropertiesManipulator.extractConfiguration("/jbossesb/providers/bus-provider/property/asdasdasd").size());
		assertTrue(PropertiesManipulator.extractConfiguration("/jbossesb/providers/bus-provider/property").size() > 0);
		assertEquals(0, PropertiesManipulator.extractConfiguration("/jbossesb/providers/bus-provider/propertys").size());
		assertEquals(2, PropertiesManipulator.extractConfiguration("/jbossesb/providers/bus-provider").size());
		assertTrue(PropertiesManipulator.extractConfiguration("/jbossesb/globals").size() > 0);
	}

	@Test
	public void testConvertResourceBundleToMap() {
		
		assertNull(PropertiesManipulator.convertResourceBundleToMap(null));
		ResourceBundle bundle = ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model.globals");
		assertNotNull(PropertiesManipulator.convertResourceBundleToMap(bundle));
		assertEquals(5, PropertiesManipulator.convertResourceBundleToMap(bundle).size());
		assertEquals("A#String#O#BASIC,DIGEST,CLIENT-CERT##Method#", PropertiesManipulator.convertResourceBundleToMap(bundle).get("globals/war-security/method"));
	}

	@Test
	public void testGetElementsFromMap() {
		
		Map<String, String> data = new HashMap<String, String>();
		assertEquals(0, PropertiesManipulator.getElementsFromMap(null).size());
		assertEquals(0, PropertiesManipulator.getElementsFromMap(data).size());
		data.put("globals/war-security/method", "A#String#O#BASIC,DIGEST,CLIENT-CERT##Method#");
		assertEquals(0, PropertiesManipulator.getElementsFromMap(data).size());
		data.put("globals/war-security", "E#O11#WAR Security#");
		assertEquals(1, PropertiesManipulator.getElementsFromMap(data).size());
		assertTrue(PropertiesManipulator.getElementsFromMap(data).containsKey("globals/war-security"));
		assertEquals("E#O11#WAR Security#", PropertiesManipulator.getElementsFromMap(data).get("globals/war-security"));
		data.put("globals/test", "E#O11#WAR Security2#");		
		assertEquals(2, PropertiesManipulator.getElementsFromMap(data).size());
		data.put("aglobals/war-security/method", "A#String#O#BASIC,DIGEST,CLIENT-CERT##Method#");
		assertEquals(2, PropertiesManipulator.getElementsFromMap(data).size());
	}
	
	@Test
	public void testGetAttributesToElement() {
		
		Map<String, String> data = new HashMap<String, String>();
		assertEquals(0, PropertiesManipulator.getAttributesToElement(null, null).size());
		assertEquals(0, PropertiesManipulator.getAttributesToElement(data, null).size());
		assertEquals(0, PropertiesManipulator.getAttributesToElement(null, "text").size());
		assertEquals(0, PropertiesManipulator.getAttributesToElement(data, "text").size());
		assertEquals(0, PropertiesManipulator.getAttributesToElement(data, "").size());
		data.put("globals/war-security/method", "A#String#O#BASIC,DIGEST,CLIENT-CERT##Method#");
		assertEquals(0, PropertiesManipulator.getAttributesToElement(data, "").size());
		assertEquals(1, PropertiesManipulator.getAttributesToElement(data, "globals/war-security").size());
		assertEquals(0, PropertiesManipulator.getAttributesToElement(data, "globals").size());
		data.put("globals/war-security/methods", "E#O11#WAR Security#");		
		assertEquals(1, PropertiesManipulator.getAttributesToElement(data, "globals/war-security").size());
		data.put("globals/war-security/methods/test", "A#String#O#BASIC,DIGEST,CLIENT-CERT##Method#");
		assertEquals(1, PropertiesManipulator.getAttributesToElement(data, "globals/war-security/methods").size());
		assertEquals(1, PropertiesManipulator.getAttributesToElement(data, "globals/war-security").size());
		assertTrue(PropertiesManipulator.getAttributesToElement(data, "globals/war-security").containsKey("globals/war-security/method"));
		assertEquals("A#String#O#BASIC,DIGEST,CLIENT-CERT##Method#", PropertiesManipulator.getAttributesToElement(data, "globals/war-security").get("globals/war-security/method"));
	}

}
