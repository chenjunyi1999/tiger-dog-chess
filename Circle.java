package buhuqi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Circle extends JLabel{
	private int x = 45, y = 285;
	public void setxy(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	public void paint(Graphics g) {
	    super.paint(g);
	    drawArc(g);
	}
	private void drawArc(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2d.setColor(Color.ORANGE);
		g2d.drawArc(x + 2, y + 2, 56, 56, 0, 360);
		g2d.dispose();
	}
}