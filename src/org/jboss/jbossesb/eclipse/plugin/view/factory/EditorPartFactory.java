package org.jboss.jbossesb.eclipse.plugin.view.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.jboss.jbossesb.eclipse.plugin.model.XMLDocument;
import org.jboss.jbossesb.eclipse.plugin.model.XMLElement;
import org.jboss.jbossesb.eclipse.plugin.view.part.EditorPart;
import org.jboss.jbossesb.eclipse.plugin.view.part.JBossESBPart;
import org.jboss.jbossesb.eclipse.plugin.view.part.ProviderPart;
import org.jboss.jbossesb.eclipse.plugin.view.part.ServicePart;

/**
 * The factory class creates GUI parts.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-07
 */
public class EditorPartFactory implements EditPartFactory {
	
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		
		EditPart part = null;
		
		if (model instanceof XMLDocument) {
			
			part = new EditorPart();
			
		} else if (model instanceof XMLElement) {
			
			String adress = ((XMLElement) model).getAddress();
			
			if (adress.equals("/jbossesb")) {
				part = new JBossESBPart();
			} else if (adress.startsWith("/jbossesb/providers")) {
				part = new ProviderPart();
			} else if (adress.startsWith("/jbossesb/services")) {
				part = new ServicePart();
			}
		}

		if (part != null) {
			part.setModel(model);
		}

		return part;
	}
}
