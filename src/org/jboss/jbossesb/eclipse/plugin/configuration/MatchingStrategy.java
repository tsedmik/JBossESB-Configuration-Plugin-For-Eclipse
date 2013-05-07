package org.jboss.jbossesb.eclipse.plugin.configuration;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.w3c.dom.Document;

/**
 * Class matches input files and decides whether the input file is a configuration
 * file of JBoss ESB based on defined XML namespace.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-04
 */
public class MatchingStrategy implements IEditorMatchingStrategy {

	@Override
	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		
		IFile ifile = (IFile) input.getAdapter(IFile.class);
		String path = ifile.getRawLocation().toOSString();
		File file = new File(path);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);
		} catch (Exception e) {
			return false;
		}
		
		if (doc.getDocumentElement().getAttribute("xmlns").equals("http://anonsvn.labs.jboss.com/labs/jbossesb/trunk/product/etc/schemas/xml/jbossesb-1.3.0.xsd")) {
			return true;
		}
		
		return false;
	}

}
