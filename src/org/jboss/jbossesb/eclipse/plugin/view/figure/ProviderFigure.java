package org.jboss.jbossesb.eclipse.plugin.view.figure;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

/**
 * Figure represents a JBossESB provider.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-29
 */
public class ProviderFigure extends Figure {

	private Label label;
	private List<ProviderBusFigure> buses;

	/**
	 * Create figure represents a JBossESB Provider (without buses and set Label).
	 */
	public ProviderFigure() {
		
		// layout
		ToolbarLayout layout = new ToolbarLayout();
		layout.setSpacing(2);
		setLayoutManager(layout);
	 	setOpaque(true);
		
		// figures
		label = new Label();
		label.setFont(new Font(null, "Arial", 12, SWT.BOLD));
		add(label);
	}

	/**
	 * Method accesses label for configuration.
	 * 
	 * @return Main figure label
	 */
	public Label getLabel() {
		return label;
	}
	
	/**
	 * Access provider's buses for configuration
	 * 
	 * @return provider's buses
	 */
	public List<ProviderBusFigure> getBuses() {
		return buses;
	}

	/**
	 * Set new buses:
	 * - remove old
	 * - add new
	 * 
	 * @param buses a new list of provider's buses
	 */
	public void setBuses(List<ProviderBusFigure> buses) {
		
		// remove old buses
		if (this.buses != null) {
			for (ProviderBusFigure bus : this.buses) {
				remove(bus);
			}
		}
		
		// add new buses
		this.buses = buses;
		for (ProviderBusFigure bus : this.buses) {
			add(bus);
		}
		
		repaint();
	}
}
