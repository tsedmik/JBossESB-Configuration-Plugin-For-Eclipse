package org.jboss.jbossesb.eclipse.plugin.model;

import org.w3c.dom.Node;

/**
 * Class represents a tuple (XML node and its parent) for BFS algorithm used in loadConfiguration method in XMLManipulator.java
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2012-11-04
 */
public class QueueTuple {

	private Node node;
	private XMLElement parent;
	
	public QueueTuple(Node node, XMLElement parent) {
		super();
		this.node = node;
		this.parent = parent;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public XMLElement getParent() {
		return parent;
	}
	public void setParent(XMLElement parent) {
		this.parent = parent;
	}
}
