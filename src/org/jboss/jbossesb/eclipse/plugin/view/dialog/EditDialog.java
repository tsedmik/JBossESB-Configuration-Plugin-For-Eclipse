package org.jboss.jbossesb.eclipse.plugin.view.dialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
	private PropertiesManipulator propManipulator;
	private Map<String, String> properties;
	private Map<String, Control> objects; // address + value in the form

	public EditDialog(Shell parentShell, XMLElement data) {
		super(parentShell);
		this.data = data;
		propManipulator = new PropertiesManipulator(data.getAddress());
		objects = new HashMap<String, Control>();
		
		Map<String, String> temp = PropertiesManipulator.convertResourceBundleToMap(propManipulator.getResource());
		properties = PropertiesManipulator.alterProperties(temp);
	}

	@Override
	public void create() { 
		super.create();

		// Set the title
		setTitle(data.getName());

		// Set the hint
		if (data.getHint() != null) {
			setMessage(data.getHint(), IMessageProvider.INFORMATION);
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		// layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = GridData.FILL;
		parent.setLayout(layout);
		
		// content
		addAttributes(parent, data); // root XMLElement attributes
		
		// TODO Add exception for 'Global settings' add to stack only root and globals XMLElement
		Stack<XMLElement> stack = new Stack<XMLElement>();
		for (XMLElement child : data.getChildren()) {
			stack.push(child);
		}
		
		while (!stack.isEmpty()) {
			XMLElement element = stack.pop();
			for (XMLElement child : element.getChildren()) {
				stack.push(child);
			}
			
			Label label = new Label(parent, SWT.BOLD);
			label.setText(element.getName());
			label.setFont(new Font(parent.getDisplay(), getFont(element.getAddress())));
			label = new Label(parent, SWT.NONE);
			
			addAttributes(parent, element);
			
		}
		
		//addElements(parent, data);
		
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
	 * Create an OK button with input validation.
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
	 * Save the state of input objects of GUI to the XMLElement data model
	 */
	private void saveInput() {
		// TODO save input to the model
	}

	/**
	 * Add element's attributes to the form.
	 * 
	 * @param parent The parent object.
	 */
	private void addAttributes(Composite parent, XMLElement element) {
		
		Map<String, String> attributes = PropertiesManipulator.getAttributesToElement(properties, element.getAddress());
		
		for (String address : attributes.keySet()) {
			Label label = new Label(parent, SWT.NONE);
			if (propManipulator.getSomeAttributeValue(address, AttributeValues.REQUIRED).equals("R")) {
				label.setText(propManipulator.getSomeAttributeValue(address, AttributeValues.NAME) + "*");
			} else {
				label.setText(propManipulator.getSomeAttributeValue(address, AttributeValues.NAME));
			}
			
			// for attribute's type - String
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
			objects.put(address, text);
			// set data (if exists)
			// TODO if no data exists set a default value (save only if a user confirms it)
			String[] temp = address.split("/");
			String attributeXMLName = temp[temp.length - 1];
			XMLAttribute attribute = element.getAttribute(attributeXMLName);
			if (attribute != null && attribute.getValue() != null) {
				text.setText(attribute.getValue());
			}
		}
	}
	
	private  void addElements(Composite parent, XMLElement element) {
				
		// get elements direct under 'data' variable
		Map<String, String> elements = PropertiesManipulator.getElementsFromMap(properties);
		elements = PropertiesManipulator.getElementsUnderElement(elements, element.getAddress());
		
		for (String address : elements.keySet()) {
			Label label = new Label(parent, SWT.BOLD);
			label.setText(propManipulator.getSomeElementValue(address, ElementValues.NAME));
			label = new Label(parent, SWT.NONE);
		}
	}
	
	/**
	 * Get font size based on depth of XMLElement
	 * @param address
	 * @return
	 */
	private FontData getFont(String address) {
		
		FontData fd = new FontData();
		
		if (address == null) {
			return fd;
		}
		
		int depth = address.split("/").length;
		fd.setHeight(30-depth*2);
		fd.setStyle(SWT.BOLD);
		
		return fd;
	}
}
