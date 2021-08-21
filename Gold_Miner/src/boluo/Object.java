package boluo;

import java.awt.*;

public class Object {
	// 坐标
	int x;
	int y;

	// 宽高
	int width;
	int height;

	// 图片
	Image img;

	// 标记, 是否能移动
	boolean flag;

	// 绘制方法
	void paintSelf(Graphics g) {
		g.drawImage(img, x, y, null);
	}

}
