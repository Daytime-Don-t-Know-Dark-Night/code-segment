package boluo;

import java.awt.*;

public class Bg {

	// 总分
	static int count = 0;
	Image bg = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/bg.jpg");
	Image bg1 = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/bg1.jpg");
	Image peo = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/peo.png");

	void paintSelf(Graphics g) {
		g.drawImage(bg1, 0, 0, null);
		g.drawImage(bg, 0, 200, null);
		g.drawImage(peo, 310, 50, null);

		// 绘制积分
		drawWord(g, 30, Color.BLACK, "积分: ", 30, 150);
	}

	public static void drawWord(Graphics g, int size, Color color, String s, int x, int y) {
		g.setColor(color);
		g.setFont(new Font("仿宋", Font.BOLD, size));
		g.drawString(s + count, x, y);
	}
}
