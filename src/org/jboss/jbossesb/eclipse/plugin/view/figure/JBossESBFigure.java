package org.jboss.jbossesb.eclipse.plugin.view.figure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class JBossESBFigure extends Figure {

	private Label label;
	private RectangleFigure rectangle;

	public JBossESBFigure() {
		setLayoutManager(new XYLayout());
		setBackgroundColor(new Color(null,0,255,70));
		rectangle = new RectangleFigure();
		add(rectangle);
		label = new Label();
		add(label);
	}

	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
		setConstraint(rectangle, new Rectangle(0, 0, r.width, r.height));
		setConstraint(label, new Rectangle(0, 0, r.width, r.height));
	}
	
	public Label getLabel() {
		return label;
	}
}
