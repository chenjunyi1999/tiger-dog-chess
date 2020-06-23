package buhuqi;
import java.util.*;
import javax.swing.JLabel;

public class Tiger extends Point{
	public boolean turn;
	private int address;
	public int bufferT;
	public Tiger(Point p, int k) {
		super(p.getx(), p.gety(), false, p.getdia(0, 0), p.getdia(0, 1), p.getdia(1, 0), p.getdia(1, 1), p.getdia(2, 0), p.getdia(2, 1), p.getdia(3, 0), p.getdia(3, 1));
		turn = false;
		address = k;
	}
	public int walk(int x, int y,Point []pp)
	{
		if(turn)
		{
			int isize= pp[address].getconnect().size();
			for(int i = 0;i < isize; i++)
			{
				int k = pp[address].getconnect().get(i);
				if(x - pp[k].getx() <= 75&& x - pp[k].getx() >= -20&&y - pp[k].gety() <= 75&&y - pp[k].gety() >= -20&&pp[k].empty())
				{
					bufferT=address;
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
	public void huiqi(Point []p)
	{
		if(!turn)
		{
			p[address].changeEmpty();
			address=bufferT;
			p[address].changeEmpty();
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
	public void reset(int k,JLabel j,Point []point)
	{
		address = k;
		turn = false;
		point[k].changeEmpty(false);
		j.setVisible(true);
		j.setLocation(point[k].getx(), point[k].gety());
	}
	public boolean turn()
	{
		return turn;
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
	public int getaddress()
	{
		return address;
	}
	public boolean getturn()
	{
		return turn;
	}
	public Set<Integer> eat(Point []pp, Dog []dog)
	{
		Set<Integer> s = new HashSet<Integer>();
		for(int i = 0;i < 4;i++)
		{
			int k1 = pp[address].getdia(i, 0);
			int k2 = pp[address].getdia(i, 1);
			if(k1 != -1&&k2 != -1)
			{
				if(!pp[k1].empty()&&!pp[k2].empty())
				{
					for(int j = 0;j < 16;j++)
					{
						if(!dog[j].getdie()&&(dog[j].getaddress()==k1||dog[j].getaddress()==k2))
							{
								s.add(j);
								dog[j].changedie();
								pp[dog[j].getaddress()].changeEmpty();
							}
					}
				}
			}
		}
		return s;
	}
	public boolean win(Dog []d)
	{
		int sum = 0;
		for(int i =0;i < 16;i++)
		{
			if(!d[i].getdie())
				sum++;
		}
		if(sum == 2)
			return true;
		else return false;
	}
}
