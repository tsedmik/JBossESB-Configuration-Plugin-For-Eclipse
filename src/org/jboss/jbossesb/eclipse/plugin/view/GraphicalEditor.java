package org.jboss.jbossesb.eclipse.plugin.view;

import java.io.File;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.jboss.jbossesb.eclipse.plugin.controller.XMLException;
import org.jboss.jbossesb.eclipse.plugin.controller.XMLManipulator;
import org.jboss.jbossesb.eclipse.plugin.controller.XMLManipulatorImpl;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.view.factory.EditorPartFactory;

/**
 * Basic class of the graphical editor.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2012-11-24
 */
public class GraphicalEditor extends GraphicalEditorWithFlyoutPalette {

	private XMLManipulator manipulator;
	private XMLDocument document;

	public GraphicalEditor() {
		setEditDomain(new DefaultEditDomain(this)); 
	}

	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		try {
			getGraphicalViewer().setContents(document);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		getGraphicalViewer().setEditPartFactory(new EditorPartFactory());
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return new GraphicalEditorPalette();
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		//TODO Add logging
		//TODO Add control on null value of document
		try {
			manipulator.saveConfiguration(document);
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		IFile ifile = (IFile) input.getAdapter(IFile.class);
		String path = ifile.getRawLocation().toOSString();
		manipulator = new XMLManipulatorImpl(new File(path));
		try {
			document = manipulator.loadConfiguration();
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	  * Fire a {@link IEditorPart#PROP_DIRTY} property change and
	  * call super implementation.
	  */
	@Override public void commandStackChanged(EventObject event) {
		firePropertyChange(PROP_DIRTY);
		super.commandStackChanged(event);
	} 
}
