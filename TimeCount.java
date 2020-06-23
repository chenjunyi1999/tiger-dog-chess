package buhuqi;


import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class TimeCount extends Thread {
		public int time;
		public JLabel l, j;
		JOptionPane winner = new JOptionPane();
		int check;
		public TimeCount(int time,JLabel ttime,JLabel lblNewLabel,int a,int b,int c)
		{
			
            check=c;
			this.time = time;
			l = ttime;
			j = lblNewLabel;
			ttime.setText("虎的剩余时间:300秒");
			ttime.setFont(new java.awt.Font("楷体", 1, 22));
			ttime.setBounds(a, b, 300, 61);
			lblNewLabel.add(ttime);
		}
		public int time()
		{
			return time;
		}
		public void run()
		{
			while (time> 0) {
				time--;
				String ss = String.valueOf(time);
				if(check==1)
				{
					l.setFont(new java.awt.Font("楷体", 1, 22));
				    l.setText("狗的剩余时间:"+ss+"秒");
				}else if(check==2)
				{
					l.setFont(new java.awt.Font("楷体", 1, 22));
					l.setText("虎的剩余时间:"+ss+"秒");	
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(time == 0&&check == 1)
			{
				JOptionPane.showMessageDialog(j, "时间结束，老虎赢！"); 
			}
			if(time == 0&&check == 2)
			{
				JOptionPane.showMessageDialog(j, "时间结束，猎狗赢！"); 
			}
		}
}