package org.jboss.jbossesb.eclipse.plugin.view.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.swt.graphics.Color;

/**
 * Figure represents a bus of JBossESB provider.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-29
 */
public class ProviderBusFigure extends Figure {
	
	private Label label;
	private ConnectionAnchor connectionAnchor;

	/**
	 * Create figure represents a bus of JBossESB provider.
	 */
	public ProviderBusFigure() {
		
		// layout
		ToolbarLayout layout = new ToolbarLayout();
		layout.setSpacing(4);
		layout.setStretchMinorAxis(false);
		layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
		setLayoutManager(layout);
		setBackgroundColor(new Color(null,205,97,58));
		setOpaque(true);
		setBorder(new LineBorder());
			
		// figures
		label = new Label();
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
	 * Method accesses connection for configuration
	 * 
	 * @return Connection
	 */
	public ConnectionAnchor getConnectionAnchor() {
		if (connectionAnchor == null) {
			connectionAnchor = new ChopboxAnchor(this);
		}
		return connectionAnchor;
	}
	
	/**
	 * Border of Bus Figure.
	 * 
	 * @author Tomas Sedmik, tomas.sedmik@gmail.com
	 * @since 2013-03-29
	 */
	public class ProviderBusFigureBorder extends AbstractBorder {
	
		public Insets getInsets(IFigure figure) {
			return new Insets(1,0,0,0);
		}

		@Override
		public void paint(IFigure arg0, Graphics arg1, Insets arg2) {
			arg1.drawLine(getPaintRectangle(arg0, arg2).getTopLeft(), tempRect.getTopRight());
		}	
	}
}
