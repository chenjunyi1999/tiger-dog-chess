package buhuqi;

import java.util.ArrayList;

public class Point {
	private int x, y;
	private int [][]diagonal = new int[4][2];					//¶Ô½ÇÏß±àºÅ
	private ArrayList<Integer> connect = new ArrayList<Integer>();
	private boolean isempty;
	public Point(int x, int y,boolean bool,int lu,int u,int ru,int l,int r,int ld,int d,int rd)
	{
			this.x = x;
			this.y = y;
			this.isempty = bool;
			diagonal[0][0] = lu; diagonal[0][1] = rd;
			diagonal[1][0] = u; diagonal[1][1] = d;
			diagonal[2][0] = ru; diagonal[2][1] = ld;
			diagonal[3][0] = l; diagonal[3][1] = r;
			if(lu!=-1) setconnect(lu);
			if(u!=-1) setconnect(u);
			if(ru!=-1) setconnect(ru);
			if(l!=-1) setconnect(l);
			if(r!=-1) setconnect(r);
			if(ld!=-1) setconnect(ld);
			if(d!=-1) setconnect(d);
			if(rd!=-1) setconnect(rd);
	}
	public void setconnect(int connected)
	{
			 connect.add(connected);  
	}
	public boolean empty()
	{
		return this.isempty;
	}
	public int getx()
	{
		return x;
	}
	public int gety()
	{
		return y;
	}
	public int getdia(int i,int j)
	{
		int k = diagonal[i][j];
		return k;
	}
	public ArrayList<Integer> getconnect()
	{
		return connect;
	}
	public void changeEmpty()
	{
		if(isempty) isempty = false;
		else isempty = true;
	}
	public void changeEmpty(boolean t)
	{
		isempty = t;
	}
}


