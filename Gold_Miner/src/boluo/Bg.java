package boluo;

import java.awt.*;

public class Bg {

	// 总分
	static int count = 0;

	// 存放药水的数量
	static int waterNum = 3;

	// 药水状态, 默认是false, true表示正在使用
	static boolean waterFlag = false;

	// 载入图片
	Image bg = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/bg.jpg");
	Image bg1 = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/bg1.jpg");
	Image peo = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/peo.png");

	// 药水, 点击右键使石块爆破, 金块加速拉取
	Image water = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/water.png");

	void paintSelf(Graphics g) {
		g.drawImage(bg1, 0, 0, null);
		g.drawImage(bg, 0, 200, null);
		g.drawImage(peo, 310, 50, null);

		// 绘制积分
		drawWord(g, 30, Color.BLACK, "积分: " + count, 30, 150);

		// 药水组件
		g.drawImage(water, 450, 40, null);

		// 药水数量
		drawWord(g, 30, Color.BLACK, "*" + waterNum, 510, 70);
	}

	public static void drawWord(Graphics g, int size, Color color, String str, int x, int y) {
		g.setColor(color);
		g.setFont(new Font("仿宋", Font.BOLD, size));
		g.drawString(str, x, y);
	}
}
