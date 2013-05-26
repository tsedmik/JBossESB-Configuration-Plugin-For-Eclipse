package org.jboss.jbossesb.eclipse.plugin.view.dialog;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import org.jboss.jbossesb.eclipse.plugin.controller.PropertiesManipulator;

/**
 * Wit the dialog a user can specifies a listener from all listeners.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-26
 */
public class ListenersDialog extends TitleAreaDialog {
	
	private StringBuilder listener;
	private Combo combo;
	private Map<String, String> listeners;
	

	public ListenersDialog(Shell parentShell, StringBuilder listener) {
		super(parentShell);
		this.listener = listener;
		
		ResourceBundle bundle = ResourceBundle.getBundle("org.jboss.jbossesb.eclipse.plugin.model.listeners");
		listeners = PropertiesManipulator.convertResourceBundleToMap(bundle);
	}
	
	@Override
	public void create() {
		super.create();
		setTitle("Select Listener");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite composite = new Composite(parent, 0);
        composite.setLayout(new GridLayout());
        
        Label label = new Label(parent, 0);
        label.setText("Select listener:");
        combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
        combo.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        
		
		Set<String> keys = listeners.keySet();
		for (String key : keys) {
			String value = listeners.get(key);
			combo.add(value);
			combo.setText(value);
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
		
		Set<String> keys = listeners.keySet();
		for (String key : keys) {
			String value = listeners.get(key);
			if (value.equals(combo.getText())) {
				listener.append(key);
				break;
			}
		}
		
		super.okPressed();
	}

}
