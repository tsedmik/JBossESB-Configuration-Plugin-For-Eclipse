package org.jboss.jbossesb.eclipse.plugin.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jboss.jbossesb.eclipse.plugin.model.XMLAttribute;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLManipulatorImplTest {

	@Test
	public void testIsValid() {
				
		XMLManipulatorImpl manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/valid1.xml"));
		assertTrue(manipulator.isValid());
		manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/nonvalid1.xml"));
		assertFalse(manipulator.isValid());
		manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/valid3.xml"));
		assertTrue(manipulator.isValid());
	}
	
	@Test
	public void testGetAddress() {
		
		// initialization
		XMLManipulatorImpl manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/valid1.xml"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File("META-INF/test_ConfFiles/valid1.xml"));
		} catch (Exception e) {
			fail("internal error");
		}
		Node temp = doc.getFirstChild();
		NodeList temp2 = doc.getElementsByTagName("action");
		
		// tests
		assertNull(manipulator.getAddress(null));
		assertEquals("/jbossesb", manipulator.getAddress(temp));
		assertEquals("/jbossesb/services/service/actions/action", manipulator.getAddress(temp2.item(0)));
	}
	
	@Test
	public void testCreateXMLElement() {
		
		// initialization
		XMLManipulatorImpl manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/valid1.xml"));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File("META-INF/test_ConfFiles/valid1.xml"));
		} catch (Exception e) {
			fail("internal error");
		}
		Node temp = doc.getFirstChild();
		
		// tests
		assertNull(manipulator.createXMLElement(null, null));
		assertNull(manipulator.createXMLElement(null, new HashMap<String, String>()));
		assertNull(manipulator.createXMLElement(null, PropertiesManipulator.extractConfiguration("/jbossesb")));
		assertNotNull(manipulator.createXMLElement(temp, PropertiesManipulator.extractConfiguration("/jbossesb")));
		temp = doc.getElementsByTagName("bus-provider").item(0);
		XMLElement element = manipulator.createXMLElement(temp, PropertiesManipulator.extractConfiguration("/jbossesb/providers/bus-provider"));
		assertNotNull(element);
		assertEquals("Bus Provider", element.getName());
		assertEquals(null, element.getHint());
		assertEquals("/jbossesb/providers/bus-provider", element.getAddress());
		assertEquals(1, element.getAttributes().size());
	}
	
	@Test
	public void testLoadConfiguration() {
		 
		// initialization
		XMLManipulatorImpl manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/valid1.xml"));
		try {
			XMLDocument document = manipulator.loadConfiguration();
			
			// tests
			assertNotNull(document);
			assertNull(document.getGlobals());
			assertNotNull(document.getJbossesb());			
			
			assertNotNull(document.getJbossesb().getChildren());
			assertTrue(document.getJbossesb().getChildren().size() > 0);
			
			assertEquals(5, document.getProviders().size());
			assertEquals(3, document.getServices().size());
			assertEquals(4, document.getJbossesb().getAttributes().size());
			
			boolean notFound = true;
			for (XMLElement element : document.getProviders()) {
				if (element.getName().equals("HTTP Provider")) {
					notFound = false;
					assertEquals("HTTP Provider", element.getHint());					
					assertEquals(1, element.getAttributes().size());
					assertEquals("Name", element.getAttributes().get(0).getName());
					assertEquals("JBossHTTP", element.getAttributes().get(0).getValue());
					assertEquals("Name", element.getAttributes().get(0).getHint());
					assertEquals("String", element.getAttributes().get(0).getType());
					assertEquals("name", element.getAttributes().get(0).getXmlName());
					assertEquals(0, element.getAttributes().get(0).getAllowedValues().size());
					assertEquals(true, element.getAttributes().get(0).isRequired());
					assertEquals(1, element.getChildren().size());
				}
			}
			if (notFound) {
				fail("Providers don't contain elemement - HTTP Provider");
			}	
		} catch (XMLException e) {
			fail();
		}
		
		manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/valid3.xml"));
		try {
			manipulator.loadConfiguration();
		} catch (XMLException e) {
			fail();
		}
	}
	
	@Test
	public void testSaveConfiguration() {
		
		// initialization
		XMLManipulatorImpl manipulator = new XMLManipulatorImpl(new File("META-INF/test_ConfFiles/valid1.xml"));
		XMLDocument document = null;
		try {
			document = manipulator.loadConfiguration();
		} catch (XMLException e) {
			fail("Some error during processing");
		}
		
		XMLElement provider = new XMLElement();
		List<XMLAttribute> attributes = new ArrayList<XMLAttribute>();
		List<XMLElement> children = new  ArrayList<XMLElement>();
		provider.setAddress("/jbossesb/providers/bus-provider");		
		XMLAttribute attr = new XMLAttribute("String", true, null, "Pokus", "name", null, "name");
		attributes.add(attr);
		provider.setAttributes(attributes);
		
		XMLElement element = new XMLElement();
		children.add(element);
		provider.setChildren(children);
		element.setAddress("/jbossesb/providers/bus-provider/bus");
		attributes = new ArrayList<XMLAttribute>();
		attr = new XMLAttribute("String", true, null, "pokus", "Bus id", null, "busid");
		attributes.add(attr);
		element.setAttributes(attributes);
		document.addProvider(provider);
		
		List<XMLAttribute> temp = document.getJbossesb().getChildren().get(0).getChildren().get(0).getAttributes();
		temp.remove(1);
		temp.get(0).setValue("1");
		
		try {
			manipulator.saveConfiguration(document);
		} catch (XMLException e) {
			fail("Some error during saving configuration");
		}
		
		// tests
		try {
			assertTrue(Arrays.equals(createChecksum("META-INF/test_ConfFiles/valid1.xml"), createChecksum("META-INF/test_ConfFiles/valid1modify.xml")));
		} catch (Exception e) {
			fail("Can't read needed files");
		}
	}
	
	@BeforeClass
	public static void copyXMLFile() {
		try {
			copyFile(new File("META-INF/test_ConfFiles/valid1.xml"), new File("META-INF/test_ConfFiles/valid1.temp"));
		} catch (IOException e1) {
			fail("Error during initialization");
		}
	}
	
	@AfterClass
	public static void pasteXMLFile() {
		try {
			File source = new File("META-INF/test_ConfFiles/valid1.temp");			
			copyFile(source, new File("META-INF/test_ConfFiles/valid1.xml"));
			source.delete();
		} catch (IOException e1) {
			fail("Error during initialization");
		}
	}
	
	private static void copyFile(File source, File target) throws IOException {
		
		InputStream in = new FileInputStream(source);
        OutputStream out = new FileOutputStream(target);
     
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        in.close();
        out.close();
	}
	
	private static byte[] createChecksum(String filename) throws Exception {
	       InputStream fis =  new FileInputStream(filename);

	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	}
	       
}
