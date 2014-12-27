import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;

public class TankClient extends Frame {

	int x = 50, y = 50;//控制位置
	
	public void paint(Graphics g) {//初始化时，paint会被自动调用
		Color c = g.getColor();//g 是前景色
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);//x,y为左顶角，后面两个函数为宽和高
		g.setColor(c);//还回去
		
		y += 5;
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
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
