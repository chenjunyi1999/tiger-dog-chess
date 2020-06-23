package buhuqi;

import javax.swing.JLabel;

public class Dog extends Point{
	public boolean turn;
	private boolean die;
	private int address;
	private int buffer;
	public Dog(Point p, int k) {
		super(p.getx(), p.gety(), false, p.getdia(0, 0), p.getdia(0, 1), p.getdia(1, 0), p.getdia(1, 1), p.getdia(2, 0), p.getdia(2, 1), p.getdia(3, 0), p.getdia(3, 1));
		turn = true;
		die = false;
		address = k;
	}
	public int walk(int x, int y,Point []pp)
	{
		if(turn && !die)
		{
			int isize= pp[address].getconnect().size();
			for(int i = 0;i < isize; i++)
			{
				int k = pp[address].getconnect().get(i);
				if(x - pp[k].getx() <= 75&& x - pp[k].getx() >= -20&&y - pp[k].gety() <= 75&&y - pp[k].gety() >= -20&&pp[k].empty())
				{
					buffer=address;
					pp[address].changeEmpty();
					address = k;
					pp[k].changeEmpty();
					return k;
				}	
			}
			return -1;
		}
		else {
			return -1;
		}
	}
	public boolean isonplace(int x,int y,Point []pp)
	{
		if(x - pp[address].getx() <= 100&& x - pp[address].getx() >= -30&&y - pp[address].gety() <= 100&&y - pp[address].gety() >= -30)
		{
			return true;
		}	
		return false;
	}
	public void changeturn()
	{
		if(turn)
			turn = false;
		else turn = true;
	}
	public void changeturn(boolean b)
	{
		turn = b;
	}
	public int getbuffer()
	{
		return buffer;
	}
	public void huiqi(Point []p)
	{
		if (!turn)
		{
			p[address].changeEmpty();            //更换为this.changeEmpty()就不对
			address=buffer;
			p[address].changeEmpty();
		}
	}
	public void reset(int k,JLabel j,Point []point)
	{
		address = k;
		turn = true;
		die = false;
		point[k].changeEmpty(false);
		j.setEnabled(true);
		j.setVisible(true);
		j.setLocation(point[k].getx(), point[k].gety());
	}
	public void changealive()
	{
		die = false;
	}
	public int getaddress()
	{
		return address;
	}
	public boolean getturn()
	{
		return turn;
	}
	public void changedie()
	{
		die = !die;
	}
	public boolean getdie()
	{
		return die;
	}
	public boolean win(Tiger t, Point []p)
	{
		int k = t.getaddress();
		if(k == 0) return true;
		if(k >= 1&&k <= 3&&!p[1].empty()&&!p[2].empty()&&!p[3].empty()&&!p[6].empty())
			return true;
		int isize= p[k].getconnect().size();
		for(int i = 0;i < isize; i++)
		{
			int j = p[k].getconnect().get(i);
			if(p[j].empty())
				return false;
		}
		return true;
	}
}
