package org.jboss.jbossesb.eclipse.plugin.view.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Control;

/**
 * Class represents a tuple for BFS algorithm used in creation of editing dialog.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2012-13-05
 */
public class QueueTuple {
	
	private String address;
	private Control control;
	private List<QueueTuple> children;
	private QueueTuple parent;
	
	public QueueTuple(String address, Control control, List<QueueTuple> children, QueueTuple parent) {
		super();
		this.address = address;
		this.control = control;
		this.children = children;
		this.parent = parent;
	}
	
	public QueueTuple getParent() {
		return parent;
	}

	public void setParent(QueueTuple parent) {
		this.parent = parent;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public List<QueueTuple> getChildren() {
		return children;
	}

	public void addChildren(QueueTuple object) {
		if (children == null) {
			children = new ArrayList<QueueTuple>();
		}
		children.add(object);
	}
	
	public void removeChildren(QueueTuple object) {
		if (children != null && object != null) {
			children.remove(object);
		}
	}
}
