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
		for (int i = 0; i < 11; i++) {

			// 是否可以放置
			boolean is_place = true;

			double random = Math.random();
			Gold gold;        // 存放当前生成的金块

			if (random < 0.3) {
				gold = new GoldMini();
			} else if (random < 0.7) {
				gold = new Gold();
			} else {
				gold = new GoldPlus();
			}

			// 生成金块后先判断 是否与其他的金块重叠
			for (Object obj : objectList) {
				if (gold.getRec().intersects(obj.getRec())) {
					// 重叠, 不可放置, 需要重新生成
					is_place = false;
				}
			}

			if (is_place) {
				objectList.add(gold);
			} else {
				is_place = true;
				i--;
			}
		}
		for (int i = 0; i < 5; i++) {

			// 是否可以放置
			boolean is_place = true;
			Rock rock = new Rock();

			// 生成石块后先判断 是否与其他的石块重叠
			for (Object obj : objectList) {
				if (rock.getRec().intersects(obj.getRec())) {
					// 重叠, 不可放置, 需要重新生成
					is_place = false;
				}
			}

			if (is_place) {
				objectList.add(rock);
			} else {
				is_place = true;
				i--;
			}
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

				// 左右摇摆, 点击左键
				if (e.getButton() == 1 && line.status == 0) {
					line.status = 1;
				}

				// 抓取返回, 点击右键
				if (e.getButton() == 3 && line.status == 3) {

					Bg.waterFlag = true;
					// 药水数量-1
					Bg.waterNum--;
				}

			}
		});

		// 实现绳子左右摇摆
		while (true) {
			repaint();

			// 判断当前积分是否满足过关积分
			if (Bg.count > bg.goal) {
				System.out.println("过关!");
			}

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
		// 先绘制物体, 在绘制红线, 保证红线显示在物体的上层
		for (Object obj : objectList) {
			obj.paintSelf(gImage);
		}
		line.paintSelf(gImage);

		// 再把新的画布绘制到显示的画布中
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public static void main(String[] args) {
		GameWin gameWin = new GameWin();
		gameWin.launch();
	}
}
