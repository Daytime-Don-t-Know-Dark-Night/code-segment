package boluo;

import java.awt.*;

public class Bg {

	Image bg = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/bg.jpg");
	Image bg1 = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/bg1.jpg");
	Image peo = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/peo.png");

	void paintSelf(Graphics g) {
		g.drawImage(bg1, 0, 0, null);
		g.drawImage(bg, 0, 200, null);
		g.drawImage(peo, 310, 50, null);
	}
}
