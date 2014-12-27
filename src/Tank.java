import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	int x, y;

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();//g 是前景色
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);//x,y为左顶角，后面两个函数为宽和高
		g.setColor(c);//还回去
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();//获得按键的code
		switch(key){
		case KeyEvent.VK_LEFT:
			x -= 5;
			break;
		case KeyEvent.VK_UP:
			y -= 5;
			break;
		case KeyEvent.VK_RIGHT:
			x += 5;
			break;
		case KeyEvent.VK_DOWN:
			y += 5;
			break;
		}
	}
}
