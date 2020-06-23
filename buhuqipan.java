package buhuqi;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.JButton;

public class buhuqipan {
	public static int turn;                                             //用于暂停的时候判断之前是谁的回合
	private JFrame frame;
	private int LstMoveDog;                                             //上次移动的狗
	private boolean CanHuiqi;                                           //能否悔棋
	private int LRNOfDog;                                               //狗剩余悔棋次数
	private int LRNOfTiger;                                             //虎剩悔棋次数
	public Set<Integer> s = new HashSet<Integer>();                     //用于存被吃的狗
	public JOptionPane open = new JOptionPane();						//启动时的规则
	public Circle lblNewLabel = new Circle();							//棋盘label
	public JLabel []doglabel = new JLabel[16];							//猎狗棋子的label
	public JLabel tiger1 = new JLabel();							    //老虎棋子的label
	public JLabel DRetractM = new JLabel("狗剩余悔棋数:");               //狗剩余悔棋数标签
	public JLabel TRetractM = new JLabel("虎剩余悔棋数:");               //虎剩余悔棋数标签
	public JLabel DRetractN = new JLabel("");                           //狗剩余悔棋数量
	public JLabel TRetractN = new JLabel("");                           //虎剩余悔棋数量
	public JButton restart = new JButton("");							//重新开始按钮
	public JButton retract = new JButton("");                           //悔棋按钮
	public JButton help = new JButton("");								//规则按钮
	public JButton Pause = new JButton("");								//游戏暂停
	public JButton msPause = new JButton("");							//音乐暂停
	public JLabel ttime = new JLabel();									//狗倒计时label
	public JLabel dtime = new JLabel();									//老虎倒计时label
	public JLabel ac1 = new JLabel();
	public Active ac = new Active();									//转圈动画
	public  int dogtime = 300;
	public  int tigertime =300;
	public ImageIcon pausee = new ImageIcon("src/niu_.gif"); 
	public ImageIcon background = new ImageIcon("src/background3.jpg");		//棋盘照片
	public ImageIcon dogpicture = new ImageIcon("src/dog1.png");    	//小狗的照片
	public ImageIcon tigerpicture = new ImageIcon("src/tiger.png");   	//老虎照片
	/**大圆外接矩形边长90*90 中圆 55*55 小圆 20*20 边长120*/
    public Point [] point = new Point[29];								//点的对象
    public Dog []dog = new Dog[16];										//猎狗对象
    public Tiger tiger;													//老虎对象
    public TimeCount time1, time2; 										//倒计时对象，1为猎狗，2为老虎
    public int pause = 1;
    public int mspause = 1;
    public Music ms = new Music("src/lushi.wav", 240000);
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					buhuqipan window = new buhuqipan();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public buhuqipan() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	void Win(){															//判断胜负
		if(tiger.win(dog))
		{
			time1.suspend();
			time2.suspend();
			JOptionPane.showMessageDialog(frame, "老虎赢，游戏结束！","游戏结束",JOptionPane.ERROR_MESSAGE); 
			for(int i = 0;i < 16;i++)
				dog[i].changeturn(false);
			tiger.changeturn(false);
		}
		if(dog[0].win(tiger, point))
		{
			tiger1.setVisible(false);
			time1.suspend();
			time2.suspend();
			JOptionPane.showMessageDialog(frame, "猎狗赢，游戏结束！","游戏结束",JOptionPane.ERROR_MESSAGE); 
			for(int i = 0;i < 16;i++)
				dog[i].changeturn(false);
			tiger.changeturn(false);
		}
	}
	
	@SuppressWarnings("deprecation")
	void initialize() {	
		// 设置按钮显示效果
		 UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("幼圆", Font.ITALIC, 15)));
		 // 设置文本显示效果
		 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("幼圆", Font.ITALIC, 18)));
		 JOptionPane.showMessageDialog(frame, "1.本棋供两人玩，四周摆猎犬，中间摆虎。\n"
		 		+ "2.由猎犬先走，双方每次只走一步。猎犬不能吃虎,只能围逼虎至陷阱致死或当猎犬仅剩四只\n时把虎围至任何角落无法走动致死。猎犬可在陷阱走动。\n"
					+ "3.当两只猎犬在一条直线上，中间空位时，老虎走入中间，可以吃掉两边一对猎犬（但如猎\n犬走动形成这种局面时，虎不能吃掉两边猎犬）。老虎吃到只剩两只猎犬时算胜。",
					"捕虎棋",JOptionPane.INFORMATION_MESSAGE);	  

		 time1 = new TimeCount(dogtime,ttime,lblNewLabel,background.getIconWidth() - 250, background.getIconHeight() - 800,1);		//狗的时间
		 time2 = new TimeCount(tigertime,dtime,lblNewLabel,background.getIconWidth() - 250, background.getIconHeight() - 700,2);		//老虎的时间
		 time1.start();
		 time2.start();
		 time2.suspend();
		 ms.start();
		 
		 point[0] = new Point(285, 45, true, -1, -1, -1, -1, -1, 1, 2, 3);
		 point[1] = new Point(165, 172, true, -1, -1, 0, -1, 2, -1, -1, 6);
		 point[2] = new Point(285, 172, true, -1, 0, -1, 1, 3, -1, 6, -1);
		 point[3] = new Point(405, 172, true, 0, -1, -1, 2, -1, 6, -1, -1);
		 point[4] = new Point(45, 285, false, -1, -1, -1, -1, 5, -1, 9, 10);
		 point[5] = new Point(165, 285, false, -1, -1, -1, 4, 6, -1, 10, -1);
		 point[6] = new Point(285, 285, false, 1, 2, 3, 5, 7, 10, 11, 12);
		 point[7] = new Point(405, 285, false, -1, -1, -1, 6, 8, -1, 12, -1);  
		 point[8] = new Point(525, 285, false, -1, -1, -1, 7, -1, 12, 13, -1);
		 point[9] = new Point(45, 405, false, -1, 4, -1, -1, 10, -1, 14, -1);
		 point[10] = new Point(165, 405, true, 4, 5, 6, 9, 11, 14, 15, 16);
		 point[11] = new Point(285, 405, true, -1, 6, -1, 10, 12, -1, 16, -1);
		 point[12] = new Point(405, 405, true, 6, 7, 8, 11, 13, 16, 17, 18);
		 point[13] = new Point(525, 405, false, -1, 8, -1, 12, -1, -1, 18, -1);
		 point[14] = new Point(45, 525, false, -1, 9, 10, -1, 15, -1, 19, 20);
		 point[15] = new Point(165, 525, true, -1, 10, -1, 14, 16, -1, 20, -1);
		 point[16] = new Point(285, 525, false, 10, 11, 12, 15, 17, 20, 21, 22);
		 point[17] = new Point(405, 525, true, -1, 12, -1, 16, 18, -1, 22, -1);
		 point[18] = new Point(525, 525, false, 12, 13, -1, 17, -1, 22, 23, -1);
		 point[19] = new Point(45, 645, false, -1, 14, -1, -1, 20, -1, 24, -1);
		 point[20] = new Point(165, 645, true, 14, 15, 16, 19, 21, 24, 25, 26);
		 point[21] = new Point(285, 645, true, -1, 16, -1, 20, 22, -1, 26, -1);
		 point[22] = new Point(405, 645, true, 16, 17, 18, 21, 23, 26, 27, 28);
		 point[23] = new Point(525, 645, false, -1, 18, -1, 22, -1, 27, 28, -1);
		 point[24] = new Point(45, 765, false, -1, 19, 20, -1, 25, -1, -1, -1);
		 point[25] = new Point(165, 765, false, -1, 20, -1, 24, 26, -1, -1, -1);
		 point[26] = new Point(285, 765, false, 20, 21, 22, 25, 27, -1, -1, -1);
		 point[27] = new Point(405, 765, false, -1, 22, -1, 26, 28, -1, -1, -1);
		 point[28] = new Point(525, 765, false, 22, 23, -1, 27, -1, -1, -1, -1);
		 
		 for(int i = 0;i < 16; i++)
		 {
			 doglabel[i] = new JLabel();
		 }
		 
		 dog[0] = new Dog(point[4], 4);
		 dog[1] = new Dog(point[5], 5);
		 dog[2] = new Dog(point[6], 6);
		 dog[3] = new Dog(point[7], 7);
		 dog[4] = new Dog(point[8], 8);
		 dog[5] = new Dog(point[9], 9);
		 dog[6] = new Dog(point[13], 13);
		 dog[7] = new Dog(point[14], 14);
		 dog[8] = new Dog(point[18], 18);
		 dog[9] = new Dog(point[19], 19);
		 dog[10] = new Dog(point[23], 23);
		 dog[11] = new Dog(point[24], 24);
		 dog[12] = new Dog(point[25], 25);
		 dog[13] = new Dog(point[26], 26);
		 dog[14] = new Dog(point[27], 27);
		 dog[15] = new Dog(point[28], 28);
		 
		tiger = new Tiger(point[16], 16);
		CanHuiqi=false;
		LRNOfDog=LRNOfTiger=3;
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame();
		frame.setBounds(0, 0,background.getIconWidth() ,background.getIconHeight() );
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.getContentPane().setLayout(null);
		frame.setTitle("捕虎棋");
		
		lblNewLabel.setBounds(frame.getX(), frame.getY(), background.getIconWidth(), background.getIconHeight());
		lblNewLabel.setIcon(background);
		frame.getContentPane().add(lblNewLabel,-1);
		frame.setLocation((int)(screensize.getWidth() - frame.getWidth()) / 2 , (int) (screensize.getHeight() - frame.getHeight()) / 2);
		
		lblNewLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getXOnScreen() - frame.getX();
				int y = e.getYOnScreen() - frame.getY() - 4;
				for(int i = 0;i < 16; i++)
				{
					if(!dog[i].getdie()&&dog[i].getturn()&&dog[i].isonplace(x, y, point))
					{
						lblNewLabel.setxy(point[dog[i].getaddress()].getx(), point[dog[i].getaddress()].gety());
						lblNewLabel.repaint();
					}
				}
				if(tiger.getturn()&&tiger.isonplace(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY(), point))
				{
					lblNewLabel.setxy(point[tiger.getaddress()].getx(), point[tiger.getaddress()].gety());
					lblNewLabel.repaint();
				}
				lblNewLabel.getGraphics().dispose();
			}
		});
		
		doglabel[0].setIcon(dogpicture);
		doglabel[0].setBounds(45, 285, 60, 60);
		lblNewLabel.add(doglabel[0]);
		doglabel[0].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[0], 0);
				if(dog[0].getturn())
				{
					doglabel[0].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[0].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=0;
					doglabel[0].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[0]);
					time1.suspend();
					time2.resume();
					Win();
				}		
				else doglabel[0].setLocation(point[dog[0].getaddress()].getx(),point[dog[0].getaddress()].gety());
			}
		});
		
		doglabel[1].setIcon(dogpicture);
		doglabel[1].setBounds(165, 285, 60,60);
		lblNewLabel.add(doglabel[1]);
		doglabel[1].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[1], 0);
				if(dog[1].getturn())
				{
					doglabel[1].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[1].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=1;
					doglabel[1].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[1]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[1].setLocation(point[dog[1].getaddress()].getx(),point[dog[1].getaddress()].gety());
			}
		});
		
		doglabel[2].setIcon(dogpicture);
		doglabel[2].setBounds(285, 285, 60,60);
		lblNewLabel.add(doglabel[2]);
		doglabel[2].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[2], 0);
				if(dog[2].getturn())
				{
					doglabel[2].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[2].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=2;
					doglabel[2].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[2]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[2].setLocation(point[dog[2].getaddress()].getx(),point[dog[2].getaddress()].gety());
			}
		});
		
		doglabel[3].setIcon(dogpicture);
		doglabel[3].setBounds(405, 285, 60,60);
		lblNewLabel.add(doglabel[3]);
		doglabel[3].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[3], 0);
				if(dog[3].getturn())
				{
					doglabel[3].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[3].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=3;
					doglabel[3].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[3]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[3].setLocation(point[dog[3].getaddress()].getx(),point[dog[3].getaddress()].gety());
			}
		});
		
		doglabel[4].setIcon(dogpicture);
		doglabel[4].setBounds(525, 285, 60,60);
		lblNewLabel.add(doglabel[4]);
		doglabel[4].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[4], 0);
				if(dog[4].getturn())
				{
					doglabel[4].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[4].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=4;
					doglabel[4].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[4]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[4].setLocation(point[dog[4].getaddress()].getx(),point[dog[4].getaddress()].gety());
			}
		});
		
		doglabel[5].setIcon(dogpicture);
		doglabel[5].setBounds(point[9].getx(), point[9].gety(), 60,60);
		lblNewLabel.add(doglabel[5]);
		doglabel[5].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[5], 0);
				if(dog[5].getturn())
				{
					doglabel[5].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[5].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[5].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=5;
					doglabel[5].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[5]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[5].setLocation(point[dog[5].getaddress()].getx(),point[dog[5].getaddress()].gety());
			}
		});
		
		doglabel[6].setIcon(dogpicture);
		doglabel[6].setBounds(point[13].getx(), point[13].gety(), 60,60);
		lblNewLabel.add(doglabel[6]);
		doglabel[6].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[6], 0);
				if(dog[6].getturn())
				{
					doglabel[6].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[6].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[6].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=6;
					doglabel[6].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[6]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[6].setLocation(point[dog[6].getaddress()].getx(),point[dog[6].getaddress()].gety());
			}
		});
		
		doglabel[7].setIcon(dogpicture);
		doglabel[7].setBounds(point[14].getx(), point[14].gety(), 60,60);
		lblNewLabel.add(doglabel[7]);
		doglabel[7].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[7], 0);
				if(dog[7].getturn())
				{
					doglabel[7].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[7].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[7].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=7;
					doglabel[7].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[7]);	
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[7].setLocation(point[dog[7].getaddress()].getx(),point[dog[7].getaddress()].gety());
			}
		});
		
		doglabel[8].setIcon(dogpicture);
		doglabel[8].setBounds(point[18].getx(), point[18].gety(), 60,60);
		lblNewLabel.add(doglabel[8]);
		doglabel[8].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[8], 0);
				if(dog[8].getturn())
				{
					doglabel[8].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[8].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[8].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=8;
					doglabel[8].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[8]);
					time1.suspend(); 
					time2.resume();
					Win();
				}
				else doglabel[8].setLocation(point[dog[8].getaddress()].getx(),point[dog[8].getaddress()].gety());
			}
		});
		
		doglabel[9].setIcon(dogpicture);
		doglabel[9].setBounds(point[19].getx(), point[19].gety(), 60,60);
		lblNewLabel.add(doglabel[9]);
		doglabel[9].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[9], 0);
				if(dog[9].getturn())
				{
					doglabel[9].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[9].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[9].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=9;
					doglabel[9].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[9]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[9].setLocation(point[dog[9].getaddress()].getx(),point[dog[9].getaddress()].gety());
			}
		});
		
		doglabel[10].setIcon(dogpicture);
		doglabel[10].setBounds(point[23].getx(), point[23].gety(), 60,60);
		lblNewLabel.add(doglabel[10]);
		doglabel[10].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[15], 0);
				if(dog[10].getturn())
				{
					doglabel[10].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[10].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[10].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=10;
					doglabel[10].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[10]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[10].setLocation(point[dog[10].getaddress()].getx(),point[dog[10].getaddress()].gety());
			}
		});
		
		doglabel[11].setIcon(dogpicture);
		doglabel[11].setBounds(point[24].getx(), point[24].gety(), 60,60);
		lblNewLabel.add(doglabel[11]);
		doglabel[11].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[11], 0);
				if(dog[11].getturn())
				{
					doglabel[11].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[11].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[11].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=11;
					doglabel[11].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[11]);	
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[11].setLocation(point[dog[11].getaddress()].getx(),point[dog[11].getaddress()].gety());
			}
		});
		
		doglabel[12].setIcon(dogpicture);
		doglabel[12].setBounds(point[25].getx(), point[25].gety(), 60,60);
		lblNewLabel.add(doglabel[12]);
		doglabel[12].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[12], 0);
				if(dog[12].getturn())
				{
					doglabel[12].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[12].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[12].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=12;
					doglabel[12].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[12]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[12].setLocation(point[dog[12].getaddress()].getx(),point[dog[12].getaddress()].gety());
			}
		});
		
		doglabel[13].setIcon(dogpicture);
		doglabel[13].setBounds(point[26].getx(), point[26].gety(), 60,60);
		lblNewLabel.add(doglabel[13]);
		doglabel[13].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[13], 0);
				if(dog[13].getturn())
				{
					doglabel[13].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[13].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[13].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=13;
					doglabel[13].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[13]);
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[13].setLocation(point[dog[13].getaddress()].getx(),point[dog[13].getaddress()].gety());
			}
		});
		
		doglabel[14].setIcon(dogpicture);
		doglabel[14].setBounds(point[27].getx(), point[27].gety(), 60,60);
		lblNewLabel.add(doglabel[14]);
		doglabel[14].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[14], 0);
				if(dog[14].getturn())
				{
					doglabel[14].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[14].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[14].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=14;
					doglabel[14].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[14]);	
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[14].setLocation(point[dog[14].getaddress()].getx(),point[dog[14].getaddress()].gety());
			}
		});
		
		doglabel[15].setIcon(dogpicture);
		doglabel[15].setBounds(point[28].getx(), point[28].gety(), 60,60);
		lblNewLabel.add(doglabel[15]);
		doglabel[15].addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(doglabel[15], 0);
				if(dog[15].getturn())
				{
					doglabel[15].setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		doglabel[15].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = dog[15].walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					LstMoveDog=15;
					doglabel[15].setBounds(point[iswalk].getx(), point[iswalk].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[15]);	
					time1.suspend();
					time2.resume();
					Win();
				}
				else doglabel[15].setLocation(point[dog[15].getaddress()].getx(),point[dog[15].getaddress()].gety());
			}
		});
		
		tiger1.setIcon(tigerpicture);
		tiger1.setBounds(point[16].getx(), point[16].gety(), 60,60);
		lblNewLabel.add(tiger1);
		tiger1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				lblNewLabel.add(tiger1, 0);
				if(tiger.getturn())
				{
					tiger1.setLocation(e.getXOnScreen() - frame.getX()- 30, e.getYOnScreen() - frame.getY() - 44);
				}
			}
		});
		tiger1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int iswalk = tiger.walk(e.getXOnScreen() - frame.getX(), e.getYOnScreen() - frame.getY() - 4, point);
				if (iswalk != -1) {
					Music ms1 = new Music("src/1212.wav", 500);
					ms1.start();
					CanHuiqi=true;
					tiger1.setLocation(point[iswalk].getx(), point[iswalk].gety());
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					s = tiger.eat(point,dog);
					if(s.size() != 0)
					{ 
						for (Integer it : s) {  
						       doglabel[it].setVisible(false);
						       doglabel[it].setEnabled(false);
						}  
					}
					time1.resume();
					time2.suspend();
					Win();
					
				}
				else tiger1.setLocation(point[tiger.getaddress()].getx(),point[tiger.getaddress()].gety());
			}
		});
		
		help.setBounds(background.getIconWidth() - 100, background.getIconHeight() - 130, 88, 60);
		help.setFont(new java.awt.Font("楷体", 1, 20));
		help.setText("规则");
		lblNewLabel.add(help);
		help.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				time1.suspend();
				time2.suspend();
				// 设置按钮显示效果
				 UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.ITALIC, 15)));
				 // 设置文本显示效果
				 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("幼圆", Font.ITALIC, 18)));
				int i = open.showConfirmDialog(frame, "1.本棋供两人玩，四周摆猎犬，中间摆虎。\n"
				 		+ "2.由猎犬先走，双方每次只走一步。猎犬不能吃虎,只能围逼虎至陷阱致死或当猎犬仅剩四只\n时把虎围至任何角落无法走动致死。猎犬可在陷阱走动。\n"
							+ "3.当两只猎犬在一条直线上，中间空位时，老虎走入中间，可以吃掉两边一对猎犬（但如猎\n犬走动形成这种局面时，虎不能吃掉两边猎犬）。老虎吃到只剩两只猎犬时算胜。",
							"捕虎棋",JOptionPane.CLOSED_OPTION);	
				if(i==open.OK_OPTION||i==open.CLOSED_OPTION)
				{
					if(tiger.turn)
						time2.resume();
					else time1.resume();
				}
			}
		});
		
		DRetractM.setLabelFor(DRetractM);
		DRetractM.setFont(new java.awt.Font("楷体", 1, 23));
		DRetractM.setBounds(20, 70, 160, 30);
		lblNewLabel.add(DRetractM);

		TRetractM.setFont(new java.awt.Font("楷体", 1, 23));
		TRetractM.setBounds(20, 130, 160, 30);
		lblNewLabel.add(TRetractM);

		DRetractN.setBounds(185, 70, 30, 30);
		DRetractN.setFont(new java.awt.Font("楷体", 1, 23));
		DRetractN.setText(String.valueOf(LRNOfDog));
		lblNewLabel.add(DRetractN);

		TRetractN.setBounds(185, 130, 30, 30);
		TRetractN.setFont(new java.awt.Font("楷体", 1, 23));
		TRetractN.setText(String.valueOf(LRNOfTiger));
		lblNewLabel.add(TRetractN);
		
		msPause.setBounds(background.getIconWidth() - 220, background.getIconHeight() - 208, 200, 60);
		msPause.setFont(new java.awt.Font("楷体", 1, 20));
		msPause.setText("背景音乐：开");
		lblNewLabel.add(msPause);
		msPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(mspause == 1)
				{
					ms.suspend1();
					mspause = 0;
					msPause.setText("背景音乐：关");
				}
				else {
					ms.resume1();
					mspause = 1;
					msPause.setText("背景音乐：开");
				}
			}
		});
		
		Thread thread = new Thread(ac);
		frame.add(ac, 0);
		ac.setVisible(false);
		ac.setBounds(10, 10, 575, 830);
		ac.setloc(120, 250, 350);
		ac1.setBounds(239, 347, 152, 174);
		ac1.setIcon(pausee);
		ac1.setVisible(false);
		frame.add(ac1, 0);
		thread.start();
		thread.suspend();
		Pause.setBounds(background.getIconWidth() - 220, background.getIconHeight() - 130, 88, 60);
		Pause.setFont(new java.awt.Font("楷体", 1, 20));
		Pause.setText("暂停");
		lblNewLabel.add(Pause,0);
		Pause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(pause == 1)
				{
					if(tiger.turn==true)
					{
						turn=1;
					}else {
						turn=2;
					}
					ac.setVisible(true);
					ac1.setVisible(true);
					thread.resume();
					pause = 0;
					time1.suspend();
					time2.suspend();
					restart.setEnabled(false);
					retract.setEnabled(false);
					for(int i=0;i<16;i++)
					{
						doglabel[i].setEnabled(false);
						dog[i].turn=false;
					}
					tiger1.setEnabled(false);
					tiger.turn=false;
					Pause.setText("开始");
				}
				else {
					ac.setVisible(false);
					ac1.setVisible(false);
					restart.setEnabled(true);
					retract.setEnabled(true);
					if(turn==1)
					{
						for(int i=0;i<16;i++)
						{
							doglabel[i].setEnabled(true);
						}
						tiger1.setEnabled(true);
						tiger.turn=true;
						
					}
					else {
						for(int i=0;i<16;i++)
						{
							doglabel[i].setEnabled(true);
							dog[i].turn=true;
						}
						tiger1.setEnabled(true);
					}
					thread.suspend();
					pause = 1;
					if(tiger.getturn())
						time2.resume();
					else time1.resume();
					Pause.setText("暂停");
				}
			}
		});
		
		restart.setText("重新开始");
		restart.setFont(new java.awt.Font("楷体", 1, 28));
		restart.setBounds(background.getIconWidth() - 220, background.getIconHeight() - 600, 200, 123);
		lblNewLabel.add(restart);
		restart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CanHuiqi=false;
				for(int i = 0;i < 29;i++)
					point[i].changeEmpty(true);
				tiger.reset(16, tiger1, point);
				dog[0].reset(4, doglabel[0], point); 
				dog[1].reset(5, doglabel[1], point); 
				dog[2].reset(6, doglabel[2], point); 
				dog[3].reset(7, doglabel[3], point); 
				dog[4].reset(8, doglabel[4], point); 
				dog[5].reset(9, doglabel[5], point); 
				dog[6].reset(13, doglabel[6], point); 
				dog[7].reset(14, doglabel[7], point); 
				dog[8].reset(18, doglabel[8], point); 
				dog[9].reset(19, doglabel[9], point); 
				dog[10].reset(23, doglabel[10], point); 
				dog[11].reset(24, doglabel[11], point); 
				dog[12].reset(25, doglabel[12], point); 
				dog[13].reset(26, doglabel[13], point); 
				dog[14].reset(27, doglabel[14], point); 
				dog[15].reset(28, doglabel[15], point); 
				time1.time = 300;
				time2.time = 300;
				time1.l.setText("狗的剩余时间:300秒");
				if(time1.isAlive())
					time1.resume();
				else {
					time1 = new TimeCount(dogtime,ttime,lblNewLabel,background.getIconWidth() - 250, background.getIconHeight() - 800,1);
					time1.start();
				}
				time2.l.setText("虎的剩余时间:300秒");
				if(time2.isAlive())
					time2.suspend();
				else {
					time2 = new TimeCount(tigertime,dtime,lblNewLabel,background.getIconWidth() - 250, background.getIconHeight() - 700,2);
					time2.start();
					time2.suspend();
				}
				LRNOfTiger=LRNOfDog=3;
				DRetractN.setText(String.valueOf(LRNOfDog));
				TRetractN.setText(String.valueOf(LRNOfTiger));
				lblNewLabel.setxy(point[dog[0].getaddress()].getx(), point[dog[0].getaddress()].gety());
				lblNewLabel.repaint();
			}
		});
		
		
		lblNewLabel.add(retract);
		retract.setText("悔棋");
		retract.setFont(new java.awt.Font("仿宋", 1, 28));
		retract.setBounds(background.getIconWidth() - 220, background.getIconHeight() - 400, 200, 123);	
		retract.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (CanHuiqi)
				if(tiger.getturn())
				{
					if(LRNOfDog>0)
					{
					dog[LstMoveDog].huiqi(point);
					doglabel[LstMoveDog].setBounds(point[dog[LstMoveDog].getaddress()].getx(), point[dog[LstMoveDog].getaddress()].gety(), 60, 60);
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(doglabel[LstMoveDog]);
					CanHuiqi=false;
					time2.suspend();
					time1.resume();
					LRNOfDog--;
					DRetractN.setText(String.valueOf(LRNOfDog));
					}
					else
					{
						UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.ITALIC, 15)));
						 // 设置文本显示效果
						 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("幼圆", Font.ITALIC, 18)));
						 JOptionPane.showMessageDialog(frame, "悔棋次数耗尽！","Error",JOptionPane.ERROR_MESSAGE);
					}
					
				}
				else
				{
					if(LRNOfTiger>0)
					{
					tiger.huiqi(point);
					tiger1.setBounds(point[tiger.bufferT].getx(),point[tiger.bufferT].gety(),60,60);
					if(s.size()!=0)
					{
						for (Integer it : s) {
							dog[it].changedie();
							point[dog[it].getaddress()].changeEmpty();
						    doglabel[it].setVisible(true);
						    doglabel[it].setEnabled(true);
						}  
					}
					for(int i = 0;i < 16;i++)
						dog[i].changeturn();
					tiger.changeturn();
					lblNewLabel.add(tiger1);
					CanHuiqi=false;
					time1.suspend();
					time2.resume();
					LRNOfTiger--;
					TRetractN.setText(String.valueOf(LRNOfTiger));
					}
					else
					{
						UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.ITALIC, 15)));
						 // 设置文本显示效果
						 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("幼圆", Font.ITALIC, 18)));
						 JOptionPane.showMessageDialog(frame, "悔棋次数耗尽！","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				else
				{
					UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.ITALIC, 15)));
					 // 设置文本显示效果
					 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("幼圆", Font.ITALIC, 18)));
					 JOptionPane.showMessageDialog(frame, "不能悔棋！","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}