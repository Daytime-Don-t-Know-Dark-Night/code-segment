# 布局管理器: Placer(开发者提供空间的大小和摆放位置)
# Packer(自动将控件填充到合适的位置)
# Grid(基于网格坐标来摆放控件)

# 如果引用不到换一下解释器, 有可能下载到了别的解释器中
import pygame

print(pygame.ver)


def main():
    # 初始化导入的pygame中的模块
    pygame.init()
    # 初始化用于显示的窗口并设置窗口尺寸
    screen = pygame.display.set_mode((800, 600))
    # 设置当前窗口的标题
    pygame.display.set_caption("大球吃小球")
    # 设置窗口的背景色
    screen.fill((255, 255, 255))
    # 通过指定的文件名加载图像
    ball_image = pygame.image.load("./res/ball.png")
    # 在窗口上渲染图像
    screen.blit(ball_image, (50, 50))

    # 绘制一个圆(参数: 屏幕, 颜色, 圆心位置, 半径, 0表示填充圆)
    # pygame.draw.circle(screen, (255, 0, 0), (100, 100), 30, 0)

    # 刷新当前窗口
    pygame.display.flip()

    running = True

    # 开启一个事件循环处理发生的事件
    while running:
        # 从消息队列中获取事件并对事件进行处理
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False


if __name__ == "__main__":
    main()
