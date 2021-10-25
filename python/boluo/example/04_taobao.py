from ctypes import windll
import win32api
import win32con
import time


def main():
    width = windll.user32.GetSystemMetrics(0)
    height = windll.user32.GetSystemMetrics(1)
    print(width, height)

    # 将鼠标移动到'去浏览'位置, 点击进入主会场
    windll.user32.SetCursorPos(1972, 660)
    time.sleep(3)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 900, 300)
    # time.sleep(0.01)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 900, 300)
    print("浏览主会场")
    # 浏览商品15s
    time.sleep(27)

    # 将鼠标移动到'返回'位置, 返回做任务界面, 重复上述步骤
    windll.user32.SetCursorPos(1910, 1020)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 900, 300)
    # time.sleep(0.01)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 900, 300)
    print("点击返回按钮")


def throwDice():
    windll.user32.SetCursorPos(1800, 760)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 900, 300)
    time.sleep(0.05)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 900, 300)
    print("扔骰子...")


if __name__ == '__main__':
    for i in range(100):
        main()
        time.sleep(10)

        # 扔骰子
        # throwDice()
        # time.sleep(5)