import java.awt.Frame;
import java.awt.event.*;

public class TankClient extends Frame {

	public void launchFrame(){
		this.setLocation(400, 300);
		this.setSize(800, 600);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {//重写了父类中的函数
				System.exit(0);
			}});
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		TankClient tc= new TankClient();
		tc.launchFrame();
	}

}
