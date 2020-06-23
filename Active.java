package buhuqi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Active extends JLabel implements Runnable{
	private int x1 = 0;
	private int x = 0, y = 0, r = 0;
	public void setloc(int x,int y,int r)
	{
		this.x = x;
		this.y = y;
		this.r = r;
	}
	public void paint(Graphics g) {
	    super.paint(g);
	    drawArc(g);
	  }
	private void drawArc(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.orange);
		g2d.drawArc(x + 1, y + 1, r + 2, r + 2, 0, 360);
		g2d.fillArc(x + 1, y + 1, r + 2, r + 2, x1, 240);
		g2d.drawArc(x + 50, y + 50, r - 98, r - 98, 0, 360);
		g2d.setColor(Color.WHITE);
		g2d.fillArc(x + 51, y + 51, r - 100, r - 100, 0, 360);
		g2d.dispose();
	}
	 public void run() {
		    while (true) {
		    	if (x1 < 360) {
					x1 = x1 - 1;
				} else {
					x1 = 0;  
				}
		    	repaint();
		      try {
		        Thread.sleep(10);
		      } catch (InterruptedException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		  }
	 }
}
