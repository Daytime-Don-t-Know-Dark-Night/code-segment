package boluo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWin extends JFrame {

	// 存储金块, 石块
	List<Object> objectList = new ArrayList<>();

	Bg bg = new Bg();
	Line line = new Line(this);
	// Gold gold = new Gold();

	{
		for (int i = 0; i < 3; i++) {
			objectList.add(new Gold());
		}
		for (int i = 0; i < 3; i++) {
			objectList.add(new Rock());
		}
	}

	// 利用双缓存技术解决窗口闪动问题, 这是一块新的画布
	Image offScreenImage;

	// 初始化窗口信息
	void launch() {
		this.setVisible(true);
		this.setSize(768, 1000);
		this.setLocationRelativeTo(null);
		this.setTitle("Gold Miner");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getButton() == 1) {    // 点击左键
					line.status = 1;
				}
			}
		});

		// 实现绳子左右摇摆
		while (true) {
			repaint();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paint(Graphics g) {

		offScreenImage = this.createImage(768, 1000);
		Graphics gImage = offScreenImage.getGraphics();

		// 先将游戏中的元素绘制到新的画布上
		bg.paintSelf(gImage);
		line.paintSelf(gImage);

		for (Object obj : objectList) {
			obj.paintSelf(gImage);
		}

		// 再把新的画布绘制到显示的画布中
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public static void main(String[] args) {
		GameWin gameWin = new GameWin();
		gameWin.launch();
	}
}
