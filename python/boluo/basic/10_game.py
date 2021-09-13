# 布局管理器: Placer(开发者提供空间的大小和摆放位置)
# Packer(自动将控件填充到合适的位置)
# Grid(基于网格坐标来摆放控件)

import pygame

print(pygame.ver)

def main():
    # 初始化导入的pygame中的模块
    pygame.init()
    # 初始化用于显示的窗口并设置窗口尺寸
    screen = pygame.display.set_mode((800, 600))

