package org.jboss.jbossesb.eclipse.plugin.view.figure;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * Figure represents a JBoss ESB Service.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-25
 */
public class ServiceFigure extends Figure {
	
	private Label label;
	private ServiceActionFigure actions = new ServiceActionFigure();
	private ConnectionAnchor connectionAnchor;

	public ServiceFigure() {
		
		// layout
		setLayoutManager(new ToolbarLayout());
		setBackgroundColor(new Color(null,155,160,160));
		setBorder(new LineBorder(ColorConstants.black,1));
		setOpaque(true);
		
		// figures
		label = new Label();
		label.setFont(new Font(null, "Arial", 12, SWT.BOLD));
		add(label);
		
		Label actionsLabel = new Label();
		actionsLabel.setText("Actions");
		add(actionsLabel);
		add(actions);
	}

	public Label getLabel() {
		return label;
	}
	
	public ServiceActionFigure getActions() {
		return actions;
	}

	public ConnectionAnchor getConnectionAnchor() {
		if (connectionAnchor == null) {
			connectionAnchor = new ChopboxAnchor(this);
		}
		return connectionAnchor;
	}

}
