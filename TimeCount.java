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
			ttime.setText("����ʣ��ʱ��:300��");
			ttime.setFont(new java.awt.Font("����", 1, 22));
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
					l.setFont(new java.awt.Font("����", 1, 22));
				    l.setText("����ʣ��ʱ��:"+ss+"��");
				}else if(check==2)
				{
					l.setFont(new java.awt.Font("����", 1, 22));
					l.setText("����ʣ��ʱ��:"+ss+"��");	
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(time == 0&&check == 1)
			{
				JOptionPane.showMessageDialog(j, "ʱ��������ϻ�Ӯ��"); 
			}
			if(time == 0&&check == 2)
			{
				JOptionPane.showMessageDialog(j, "ʱ��������Թ�Ӯ��"); 
			}
		}
}