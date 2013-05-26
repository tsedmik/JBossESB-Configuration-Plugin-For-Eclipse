package org.jboss.jbossesb.eclipse.plugin.view.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.jboss.jbossesb.eclipse.plugin.controller.XMLElementManipulator;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;

public class ProvidersDialog extends TitleAreaDialog {

	private XMLElement provider;
	private StringBuilder bus;
	private Combo combo;

	public ProvidersDialog(Shell parentShell, XMLElement provider, StringBuilder bus) {
		super(parentShell);
		this.provider = provider;
		this.bus = bus;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Select Bus");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = new Composite(parent, 0);
        composite.setLayout(new GridLayout());
        
        Label label = new Label(parent, 0);
        label.setText("Select bus:");
        combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        List<XMLElement> buses =  XMLElementManipulator.getBuses(provider);
        for (XMLElement bus : buses) {
        	combo.add(XMLElementManipulator.getAttrValue(bus, "busid"));
        	combo.setText(XMLElementManipulator.getAttrValue(bus, "busid"));
        }
		
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
		Button okButton = createButton(parent, OK, "OK", true);

		// create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);

		// add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(OK);
				
			}
		});
	}
	
	@Override
	protected boolean isResizable() {
		return false;
	}
	
	@Override
	protected void okPressed() {
		bus.append(combo.getText());
		super.okPressed();
	}

}
