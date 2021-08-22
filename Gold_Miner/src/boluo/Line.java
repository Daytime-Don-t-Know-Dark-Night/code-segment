package boluo;

import java.awt.*;

public class Line {

	// 起点坐标
	int x = 380;
	int y = 180;

	// 终点坐标
	int endx = 500;
	int endy = 500;

	// endx = x + length * cosA
	// endy = y + length * sinA

	// 角度 ∈ (0, π)
	// 设 n ∈ (0, 1)
	// 则 nπ ∈ (0, π)

	// endx = x + length * cos(nπ)
	// endy = y + length * sin(nπ)

	// 线长
	double length = 100;

	// 线长最小值
	double min_length = 100;

	// 线长最大值
	double max_length = 750;

	double n = 0;

	// 方向
	int dir = 1;

	// 状态	0:摇摆	1:抓取	2:收回	3:抓取返回
	int status;

	// 抓钩图片
	Image hook = Toolkit.getDefaultToolkit().getImage("Gold_Miner/imgs/hook.png");

	// 碰撞检测, 判断endx endy 是否在x+width, y+height中
	GameWin frame;

	Line(GameWin frame) {
		this.frame = frame;
	}

	// 碰撞检测, 检测物体是否被抓取
	void logic() {
		for (Object obj : this.frame.objectList) {
			if (endx > obj.x && endx < obj.x + obj.width
					&& endy > obj.y && endy < obj.y + obj.height) {
				status = 3;
				obj.flag = true;
			}
		}
	}

	// 绘制方法
	void lines(Graphics g) {
		endx = (int) (x + length * Math.cos(n * Math.PI));
		endy = (int) (y + length * Math.sin(n * Math.PI));
		g.setColor(Color.red);
		g.drawLine(x - 1, y, endx - 1, endy);
		g.drawLine(x, y, endx, endy);
		g.drawLine(x + 1, y, endx + 1, endy);
		g.drawImage(hook, endx - 36, endy - 2, null);
	}

	void paintSelf(Graphics g) {
		logic();
		switch (status) {
		case 0:
			if (n < 0.1) {
				dir = 1;
			} else if (n > 0.9) {
				dir = -1;
			}

			n = n + 0.005 * dir;
			lines(g);

			break;

		case 1:        // 抓取
			if (length < max_length) {

				length = length + 10;
				lines(g);
			} else {
				status = 2;
			}

			break;
		case 2:        // 收回
			if (length > min_length) {

				length = length - 10;
				lines(g);
			} else {
				// 返回之后回到最开始摇摆状态
				status = 0;
			}

			break;
		case 3:
			int m = 1;
			if (length > min_length) {

				length = length - 10;
				lines(g);
				for (Object obj : this.frame.objectList) {

					// 只移动抓取到的金块
					if (obj.flag) {
						m = obj.m;
						obj.x = endx - obj.getWidth() / 2;
						obj.y = endy;

						// 抓取成功, 移除金块
						if (length <= min_length) {
							obj.x = -150;
							obj.y = -150;
							obj.flag = false;
							Bg.waterFlag = false;
							// 加分
							Bg.count += obj.count;
							status = 0;
						}

						// 判断是否使用了药水
						if (Bg.waterFlag) {
							if (obj.type == 1) {
								m = 1;
							}
							if (obj.type == 2) {
								obj.x = -150;
								obj.y = -150;
								obj.flag = false;
								Bg.waterFlag = false;
								status = 2;
							}

						}
					}

				}

			}
			try {
				Thread.sleep(m);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}


	}
}
