import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.*;

public class Tank {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	private int x, y;//位置
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	private boolean live = true;
	
	private static Random r = new Random(); 


	TankClient tc;
	
	private boolean good;
	
	private boolean bL = false, bU = false, bR = false, bD =false;//记录按键状态
	enum Direction{L,LU,U,RU,R,RD,D,LD, STOP}//枚举类型
	
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;//pt 炮筒方向
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
		
	}
	
	public Tank(int x, int y ,boolean good, Direction dir, TankClient tc ){
		this(x,y,good);
		this.tc = tc;
		this.dir = dir;
	}
	
	public void draw(Graphics g){
		if(!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
			
		}
		Color c = g.getColor();//g 是前景色
		if(good){
					g.setColor(Color.RED);
		}else{
			g.setColor(Color.BLUE);
		}

		g.fillOval(x, y, WIDTH, HEIGHT);//x,y为左顶角，后面两个函数为宽和高
		g.setColor(c);//还回去
		
		switch(ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
			break;
		}
		
		move();
	}
	
	void move(){
		switch(dir){
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if(this.dir != Direction.STOP){
			this.ptDir = this.dir;
		}
		
		if (x < 0) x = 0;
		if (y < 20) y = 20;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
		
		if(!good){
			Direction[] dirs = Direction.values();
			int rn = r.nextInt(dirs.length);
			dir = dirs[rn];
		}
		
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();//获得按键的code
		switch(key){ 
		case KeyEvent.VK_A:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();//获得按键的code
		switch(key){
//		case KeyEvent.VK_CONTROL:
//			fire();
//			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();	
	}	
	void locateDirection(){//定位方向
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public Missile fire(){
		int x = this.x + Tank.WIDTH/2 -Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 -Missile.HEIGHT/2;
		
		Missile m = new Missile(x,y,ptDir,tc);
		
		tc.missiles.add(m);
		return m;
	}
	
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH, HEIGHT);
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
