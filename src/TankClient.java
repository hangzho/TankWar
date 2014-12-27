import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;

public class TankClient extends Frame {


	public void paint(Graphics g) {//初始化时，paint会被自动调用
		Color c = g.getColor();//g 是前景色
		g.setColor(Color.RED);
		g.fillOval(50, 50, 30, 30);//x,y为左顶角，后面两个函数为宽和高
		g.setColor(c);//还回去
		
	}

	public void launchFrame(){
		this.setLocation(400, 300);
		this.setSize(800, 600);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {//重写了父类中的函数
				System.exit(0);
			}
			});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		TankClient tc= new TankClient();
		tc.launchFrame();
	}

}
