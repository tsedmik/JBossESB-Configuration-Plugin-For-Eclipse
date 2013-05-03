package org.jboss.jbossesb.eclipse.plugin.view.figure;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

/**
 * Figure represents a JBossESB provider.
 * 
 * @author Tomas Sedmik, tomas.sedmik@gmail.com
 * @since 2013-03-29
 */
public class ProviderCoverFigure extends Figure {
	
	private ProviderFigure provider;

	/**
	 * Create a figure represents a JBossESB Provider.
	 */
	public ProviderCoverFigure() {
		
		// layout
	    setLayoutManager(new XYLayout());	
	    setBorder(new ProviderCoverBorder());
		
	    // figures
	    provider = new ProviderFigure();	
	    add(provider);
	}

	/**
	 * Method accesses ProviderFigure for configuration.
	 * 
	 * @return provider figure (label, buses)
	 */
	public ProviderFigure getProvider() {
		return provider;
	}
	
	@Override
	protected void paintFigure(Graphics graphics) {
		Rectangle r = getBounds().getCopy();
	 	int x = (int)(0.15 * r.width);
		int y = (int)(0.20 * r.height);
		int width = (int)(0.70 * r.width);
		int height = (int)(0.60 * r.height);
		setConstraint(provider, new Rectangle(x, y, width, height));
	}

	/**
	 * Border of Provider Cover Figure = "Arrows".
	 * 
	 * @author Tomas Sedmik, tomas.sedmik@gmail.com
	 * @since 2013-03-29
	 */
	public class ProviderCoverBorder extends AbstractBorder {
	
		@Override
		public void paint(IFigure arg0, Graphics arg1, Insets arg2) {
			
			// paint "arrows"
			Rectangle r = arg0.getBounds();
			setBackgroundColor(new Color(null,255,255,206));
			PointList list = new PointList();
			list.addPoint(r.x,							r.y + r.height / 2);
			list.addPoint(r.x + (int)(0.15 * r.width), 	r.y);
			list.addPoint(r.x + (int)(0.15 * r.width), 	r.y + (int)(0.20 * r.height));
			list.addPoint(r.x + (int)(0.85 * r.width), 	r.y + (int)(0.20 * r.height));
			list.addPoint(r.x + (int)(0.85 * r.width), 	r.y);
			list.addPoint(r.x + r.width,				r.y + r.height / 2);
			list.addPoint(r.x + (int)(0.85 * r.width), 	r.y + r.height);
			list.addPoint(r.x + (int)(0.85 * r.width), 	r.y + (int)(0.80 * r.height));
			list.addPoint(r.x + (int)(0.15 * r.width), 	r.y + (int)(0.80 * r.height));
			list.addPoint(r.x + (int)(0.15 * r.width), 	r.y + r.height);
			arg1.drawPolygon(list);
			
			// left arrow
			list.removeAllPoints();
			list.addPoint(r.x + 1,						r.y + r.height / 2);
			list.addPoint(r.x + (int)(0.15 * r.width), 	r.y + 1);
			list.addPoint(r.x + (int)(0.15 * r.width), 	r.y + r.height);
			arg1.fillPolygon(list);
			
			// right arrow
			list.removeAllPoints();
			list.addPoint(r.x + (int)(0.85 * r.width), 	r.y);
			list.addPoint(r.x + r.width,				r.y + r.height / 2);
			list.addPoint(r.x + (int)(0.85 * r.width), 	r.y + r.height);
			arg1.fillPolygon(list);
			
		}

		@Override
		public Insets getInsets(IFigure arg0) {
			return new Insets(1,0,0,0);
		}	
	}

}
