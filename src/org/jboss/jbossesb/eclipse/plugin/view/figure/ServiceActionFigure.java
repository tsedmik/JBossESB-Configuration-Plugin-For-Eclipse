package org.jboss.jbossesb.eclipse.plugin.view.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;

/**
 * Figure represents actions of the JBoss ESB's service section.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-05-26
 */
public class ServiceActionFigure extends Figure {

	public ServiceActionFigure() {
		ToolbarLayout layout = new ToolbarLayout();
		layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		layout.setStretchMinorAxis(false);
		layout.setSpacing(2);
		setLayoutManager(layout);
		setBorder(new ServiceActionBorder());
	}

	/**
	 * Class creates a border around service actions.
	 * 
	 * @author Tomas Sedmik, tomas.sedmik@gmail.com
	 * @since 2013-05-26
	 */
	public class ServiceActionBorder extends AbstractBorder {
		
		public Insets getInsets(IFigure figure) {
			return new Insets(1, 0, 0, 0);
		}

		public void paint(IFigure figure, Graphics graphics, Insets insets) {
			graphics.drawLine(getPaintRectangle(figure, insets).getTopLeft(),
					tempRect.getTopRight());
		}
	}

}
