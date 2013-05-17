package org.jboss.jbossesb.eclipse.plugin.view.dialog;

import java.util.Map;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.widgets.Text;
import org.jboss.jbossesb.eclipse.plugin.controller.PropertiesManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLAttribute;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

/**
 * Dialog class used for data modification of ESB objects.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-04-08
 */
public class EditDialog extends TitleAreaDialog {

	private XMLElement data;
	private QueueTuple root;

	public EditDialog(Shell parentShell, XMLElement data) {
		super(parentShell);
		this.data = data;
	}

	@Override
	public void create() { 
		super.create();

		setTitle(data.getName());
		setDefaultMessage();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		// layout
		//FIXME correct dialog layout
		Composite composite = new Composite(parent, SWT.V_SCROLL | SWT.H_SCROLL);
        composite.setLayout(new GridLayout());
		
		// add content
		root = new QueueTuple(data.getAddress(), composite, null, null);
		addAttributes(root, data); // root XMLElement attributes
		addElements(root, data); // add child elements
		addOperations(root); // add edit options
		
		return parent;
	}
	

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		// layout
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.CENTER;
		parent.setLayoutData(gridData);
		
		// create OK button
		createOkButton(parent, OK, "OK", true);

		// create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
		
		// add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	protected void okPressed() {
		
		saveInput();
		super.okPressed();
	}

	/**
	 * Clears error message and set standard information message
	 */
	private void setDefaultMessage() {
		
		setErrorMessage(null);
		
		// Set the hint
		if (data.getHint() != null) {
			setMessage(data.getHint(), IMessageProvider.INFORMATION);
		} else {
			setMessage("");
		}
	}
	
	/**
	 * Creates a group object as a container for other objects
	 * 
	 * @param parent parent object
	 * @param name text displays as name of the group
	 * @return composite object (container) that is child of the group
	 */
	private Composite setGroupLayout(Composite parent, String name) {
		
		// layout (group)
		Group group = new Group(parent, 0);
		group.setText(name);
		group.setLayout(new FillLayout());
				
		Composite composite = new Composite(group, 0);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = GridData.FILL;
		composite.setLayout(layout);
		
		return composite;
	}
	
	/**
	 * Searches whether the dialog already contains object with defined addresss 
	 *  
	 * @param parent parental tree object
	 * @param address address of searched object
	 * @return true - object is already displayed, false - otherwise
	 */
	private boolean isObjectExists(QueueTuple parent, String address) {
		
		if (parent == null || address == null) {
			return false;
		}
		
		if (parent.getChildren() != null) {
			for (QueueTuple tuple : parent.getChildren()) {
				if (tuple.getAddress().equals(address)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Creates an OK button with input validation.
	 * 
	 * @param parent Parent.
	 * @param id Button id.
	 * @param label Text on the button.
	 * @param defaultButton Is button default?
	 * @return The OK Button.
	 */
	private Button createOkButton(Composite parent, int id, String label, boolean defaultButton) {
		
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (isValidInput()) {
					okPressed();
				}
			}
		});
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		setButtonLayoutData(button);
		return button;
	}

	/**
	 * Input validation.
	 * 
	 * @return true - input is valid, false - otherwise
	 */
	private boolean isValidInput() {
		// TODO add form validation
		
		boolean valid = true;
		/*
		if (firstNameText.getText().length() == 0) {
			setErrorMessage("Please maintain the first name");
			valid = false;
		}
		if (lastNameText.getText().length() == 0) {
			setErrorMessage("Please maintain the last name");
			valid = false;
		}
		*/
		return valid;
		
	}

	/**
	 * Saves the state of input objects of GUI to the XMLElement data model
	 */
	private void saveInput() {
		// TODO save input to the model
	}

	/**
	 * Adds attributes of given element to the dialog
	 * 
	 * @param parent parental tree object
	 * @param element element with data
	 */
	private void addAttributes(QueueTuple parent, XMLElement element) {
		
		// loading configuration
		PropertiesManipulator propManipulator = new PropertiesManipulator(parent.getAddress());
		Map<String, String> tempProperties = PropertiesManipulator.convertResourceBundleToMap(propManipulator.getResource());
		Map<String, String> properties = PropertiesManipulator.alterProperties(tempProperties);
		
		// set layout
		Composite composite = new Composite((Composite)parent.getControl(), 0);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = GridData.FILL;
		composite.setLayout(layout);
		
		// get all attributes attached the element
		Map<String, String> attributes = PropertiesManipulator.getAttributesToElement(properties, parent.getAddress());
		
		// print attributes
		for (String address : attributes.keySet()) {
			
			// set label (with char * if is required)
			Label label = new Label(composite, SWT.NONE);
			if (propManipulator.getSomeAttributeValue(address, AttributeValues.REQUIRED).equals("R")) {
				label.setText(propManipulator.getSomeAttributeValue(address, AttributeValues.NAME) + "*");
			} else {
				label.setText(propManipulator.getSomeAttributeValue(address, AttributeValues.NAME));
			}
			
			// create text field
			// TODO distinct different data types of attributes and allow only such values belongs in the definition set
			Text text = new Text(composite, SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
			
			// add to the tree representation of dialog
			QueueTuple object = new QueueTuple(address, text, null, parent);
			parent.addChildren(object);
			
			// set data from the model (if exists)
			String[] temp = address.split("/");
			String attributeXMLName = temp[temp.length - 1];
			XMLAttribute attribute = element.getAttribute(attributeXMLName);
			if (attribute != null && attribute.getValue() != null) {
				text.setText(attribute.getValue());
			} else {

				// TODO if no data exists set a default value (save only if a user confirms it)
				
			}
		}
	}
	
	/**
	 * Adds Elements to the dialog
	 * 
	 * @param parent parental tree object
	 * @param element element with data
	 */
	private  void addElements(QueueTuple parent, XMLElement element) {
		
		// hack for "global settings" only
		if (parent.getAddress().equals("/jbossesb")) {
			
			// add only element <globals>, if exists
			for (XMLElement child : element.getChildren()) {
				if (child.getAddress().equals("/jbossesb/globals")) {
					addElement(parent, child);
				}
			}
			return;
		}
		
		// everything else
		for (XMLElement child : element.getChildren()) {
			addElement(parent, child);
		}
		
		// add required elements (based on properties file)
		PropertiesManipulator propManipulator = new PropertiesManipulator(parent.getAddress());
		Map<String, String> temp = PropertiesManipulator.convertResourceBundleToMap(propManipulator.getResource());
		Map<String, String> properties = PropertiesManipulator.alterProperties(temp);
		Map<String, String> tempelements = PropertiesManipulator.getElementsFromMap(properties);
		Map<String, String> elements = PropertiesManipulator.getElementsUnderElement(tempelements, parent.getAddress());
		for (Map.Entry<String, String> entry : elements.entrySet()) {
			String key = entry.getKey();
			if (propManipulator.getSomeElementValue(key, ElementValues.REQUIRED).startsWith("R")) {
				if (!isObjectExists(parent, key)) {
					XMLElement temp2 = new XMLElement();
					temp2.setAddress(key);
					temp2.setName(propManipulator.getSomeElementValue(key, ElementValues.NAME));
					addElement(parent, temp2);
				}
			}
		}
		
	}
	
	/**
	 * Adds single element to the dialog
	 * 
	 * @param parent parental tree object
	 * @param element element with data
	 */
	private void addElement(QueueTuple parent, XMLElement element) {
		
		// layout
		Composite composite = setGroupLayout((Composite)parent.getControl(), element.getName());
		
		// add element to the tree
		QueueTuple tuple = new QueueTuple(element.getAddress(), composite, null, parent);
		parent.addChildren(tuple);
		
		// add remove button
		createRemoveButton(tuple);
		
		// add content
		addAttributes(tuple, element);
		addElements(tuple, element);
		addOperations(tuple);
	}
	
	/**
	 * Adds operations with objects to the dialog
	 * 
	 * @param parent parental tree object
	 */
	private void addOperations(QueueTuple parent) {
		
		// layout (group)
		Composite composite = null;
		QueueTuple tuple = null;
		
		// loading configuration
		PropertiesManipulator propManipulator;
		// hack for "Global settings"
		if (parent.getAddress().equals("/jbossesb")) {
			propManipulator = new PropertiesManipulator("/jbossesb/globals");
		} else {
			propManipulator = new PropertiesManipulator(parent.getAddress());
		}
		Map<String, String> temp = PropertiesManipulator.convertResourceBundleToMap(propManipulator.getResource());
		Map<String, String> properties = PropertiesManipulator.alterProperties(temp);
		Map<String, String> tempelements = PropertiesManipulator.getElementsFromMap(properties);
		Map<String, String> elements = PropertiesManipulator.getElementsUnderElement(tempelements, parent.getAddress());
		
		// compare with current model and add buttons
		for (String key : elements.keySet()) {
			
			String required = propManipulator.getSomeElementValue(key, ElementValues.REQUIRED).substring(1);
			String name = propManipulator.getSomeElementValue(key, ElementValues.NAME);
			
			// add "add buttons" if can
			if (isObjectExists(parent, key)) {
				if (required.equals("1N") || required.equals("0N")) {
					if (composite == null) {
						composite = setGroupLayout((Composite)parent.getControl(), "Operations");
						tuple = new QueueTuple("o", composite, null, parent);
						parent.addChildren(tuple);
					}
					createAddButton(tuple, key, name);
				}
			} else {
				if (composite == null) {
					composite = setGroupLayout((Composite)parent.getControl(), "Operations");
					tuple = new QueueTuple("o", composite, null, parent);
					parent.addChildren(tuple);
				}
				createAddButton(tuple, key, name);
			}	
		}
	}
	
	/**
	 * Call this method after every object manipulation (addition, remove). Renewss allowable operations.
	 * 
	 * @param parent parental tree object
	 */
	private void renewOperations(QueueTuple parent) {
		
		// dispose old group with operations
		for (QueueTuple tuple : parent.getChildren()) {
			if (tuple.getAddress().equals("o")) {
				parent.removeChildren(tuple);
				tuple.getControl().getParent().dispose();
				break;
			}
		}
		
		// create new group with operations
		addOperations(parent);
		
		// refresh view
		parent.getControl().getShell().pack();
		((Composite)parent.getControl()).layout();
	}
	
	/**
	 * Creates a button for adding objects to the form
	 * 
	 * @param parent parental tree object
	 * @param address defined which object is added based on address in XML file
	 * @param name button label
	 */
	private void createAddButton(final QueueTuple parent, final String address, String name) {
		
		// create button
		Button button = new Button((Composite)parent.getControl(), SWT.PUSH);
		button.setText("Add " + name);
		button.setFont(JFaceResources.getDialogFont());
		
		// add reaction on click
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setDefaultMessage();
				createObject(parent.getParent(), address);
				renewOperations(parent.getParent());
			}
		});
	}
	
	/**
	 * Creates object and puts it into the dialog
	 * 
	 * @param parent parental tree object
	 * @param address defined which object is added based on address in XML file
	 */
	private void createObject(QueueTuple parent, String address) {
		
		// create mock object (only for store "address" and "name")
		PropertiesManipulator prop = new PropertiesManipulator(address);
		XMLElement temp = new XMLElement();
		temp.setAddress(address);
		temp.setName(prop.getSomeElementValue(address, ElementValues.NAME));
		
		// add objects to the view
		addElement(parent, temp);
		
		// refresh view
		parent.getControl().getShell().pack();
		((Composite)parent.getControl()).layout();
	}
	
	/**
	 * Adds remove button into the dialog
	 * 
	 * @param target parental tree object
	 */
	private void createRemoveButton(final QueueTuple target) {
		
		// can be object removed?
		PropertiesManipulator prop = new PropertiesManipulator(target.getAddress());
		final String required = prop.getSomeElementValue(target.getAddress(), ElementValues.REQUIRED);
		if (required.startsWith("R") && required.endsWith("1")) {
			return;
		}
		
		// create button
		Button button = new Button((Composite)target.getControl(), SWT.PUSH);
		button.setText("Remove");
		button.setFont(JFaceResources.getDialogFont());
				
		// add reaction on click
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				setDefaultMessage();
				
				// check if object can be removed
				// if is required and is last one => can't be removed
				if (required.startsWith("R")) {
					int count = 0;
					for (QueueTuple tuple : target.getParent().getChildren()) {
						if (tuple.getAddress().equals(target.getAddress())) {
							count++;
						}
					}
					if (count == 1) {
						// can't be removed
						setErrorMessage("Element cannot be remove - at least one is required");
						return;
					}
				}
				
				// delete from the tree
				target.getParent().removeChildren(target);
				
				// delete from view
				target.getControl().getParent().dispose();
				// TODO resize dialog window and reorder objects 
				
				renewOperations(target.getParent());
			}
		});
	}
	

	
	
}

