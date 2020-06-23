package buhuqi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.*;

public class Music extends Thread{
	InputStream in = null;
	String s = null;
	boolean flag = true;
	int time = 0;
	@SuppressWarnings("restriction")
	AudioStream audioStream = null;
	public Music(String s,int t)
	{
		this.s = s;
		time = t;
	}
	@SuppressWarnings("restriction")
	public void run(){
		while(flag) {
		// 创建音乐文件输入流对象
		try {
			in = new FileInputStream(s);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 创建音频流对象
		try {
			audioStream = new  AudioStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 使用音频播放器播放声音
		AudioPlayer.player.start(audioStream); 
		 try {
		        sleep(time);
		        if(time < 240000)
		        	flag = false;
		        try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 } catch (InterruptedException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		 }
		}
	}
	@SuppressWarnings({ "deprecation", "restriction" })
	public void suspend1()
	{
		super.suspend();
		AudioPlayer.player.stop(audioStream);
	}
	@SuppressWarnings({ "deprecation", "restriction" })
	public void resume1()
	{
		super.resume();
		AudioPlayer.player.start(audioStream);
	}
}