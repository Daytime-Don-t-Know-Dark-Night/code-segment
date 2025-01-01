from ctypes import windll
import win32api
import win32con
import time
import random

if __name__ == '__main__':

    # 位置在 py 启动类的正上方
    while True:
        width = random.randint(400, 600)
        height = random.randint(300, 500)
        # print(time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time())) + ", Current Circle: width: {}, height: {}".format(width, height))
        windll.user32.SetCursorPos(1300, 9)

        win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 700, 9)
        win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 700, 9)
        # win32api.mouse_event(win32con.MOUSEEVENTF_RIGHTDOWN, 500, 9)
        # win32api.mouse_event(win32con.MOUSEEVENTF_RIGHTUP, 500, 9)

        print("sleep 10 seconds...")
        time.sleep(60)

        print("continue ...")