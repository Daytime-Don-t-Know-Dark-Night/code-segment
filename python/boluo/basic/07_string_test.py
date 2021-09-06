# 练习1: 在屏幕上显示跑马灯文字
import random
import os
import time


def main():
    content = "金风玉露一相逢, 便胜却人间无数. "
    while True:
        # 清理屏幕上的输出
        os.system("cls")
        print(content)

        # 休眠200毫秒
        time.sleep(0.2)
        content = content[1:] + content[0]


if __name__ == "__main__":
    main()


# 练习2: 设计一个函数产生指定长度的验证码, 验证码由大小写字母和数字构成

def generate_code(code_len=4):
    """
    生成指定长度的验证码
    :param code_len: 验证码的长度
    :
    """
