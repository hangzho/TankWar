import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {
	
	public static final int GAME_WIDTH = 800;//常量 容易维护
	public static final int GAME_HEIGHT = 600;
	
	Tank myTank = new Tank(50,50,true,Tank.Direction.STOP, this);
	
	Wall w1 = new Wall(100, 200, 20, 150, this);
	Wall w2 = new Wall(300, 100, 300, 20, this);
	//int x = 50, y = 50;//控制位置
	
	Image offScreenImage = null;
	Explode e;
	List<Explode> explodes = new ArrayList<Explode>();
	Missile m;
	List<Missile> missiles = new ArrayList<Missile>();
	Tank t;
	List<Tank> tanks = new ArrayList<Tank>();
	

	public void paint(Graphics g) {//初始化时，paint会被自动调用
		g.drawString("Missiles counts:" + missiles.size(), 10, 50);
		g.drawString("Explodes counts:" + explodes.size(), 10, 70);
		g.drawString("Tanks counts:" + tanks.size(), 10, 90);
		for(int i = 0 ; i< missiles.size();i++){
			m = missiles.get(i);
				m.hitTanks(tanks);
				m.hitTank(myTank);
				m.hitWall(w1);
				m.hitWall(w2);
	
				m.draw(g);
					}
		for(int i = 0 ; i<explodes.size(); i++){
			e = explodes.get(i);
			e.draw(g);
		}
		
		for(int i = 0; i<tanks.size();i++){
			t = tanks.get(i);
			t.collideWall(w1);
			t.collideWall(w2);
			t.collideTank(tanks);
			t.draw(g);
		}
		myTank.draw(g);
		w1.draw(g);
		w2.draw(g);
		
		
	}
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	public void launchFrame(){
		for(int i = 0; i<10; i++){
			tanks.add(new Tank(90+40*(i+1),250,false,Tank.Direction.D, this));//画敌人坦克
		}
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {//重写了父类中的函数
				System.exit(0);
			}
			});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		new Thread(new PaintThread()).start();//启动线程重画
	}
	
	public static void main(String[] args) {
		TankClient tc= new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable{//线程 由内部类完成

		public void run() {
			while(true){ 
				repaint();//父类中的，内部调用paint方法
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	private class KeyMonitor extends KeyAdapter{

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);

		}

	}
}






