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


# if __name__ == "__main__":
#    main()


# 练习2: 设计一个函数产生指定长度的验证码, 验证码由大小写字母和数字构成

def generate_code(code_len=4):
    """
    生成指定长度的验证码
    :param code_len: 验证码的长度
    :return 由大小写英文字母和数字构成的随机验证码
    """

    all_chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
    last_pos = len(all_chars) - 1
    code = ""
    for _ in range(code_len):
        index = random.randint(0, last_pos)
        code += all_chars[index]

    return code


print(generate_code(4))


# 练习3: 设计一个函数返回给定文件名的后缀名
def get_suffix(filename, has_dot=False):
    """
    获取文件名的后缀名

    :param filename: 文件名
    :param has_dot: 返回的后缀名是否需要带点
    :return 文件的后缀名
    """

    pos = filename.rfind(".")
    if 0 < pos < len(filename) - 1:
        index = pos if has_dot else pos + 1
        return filename[index:]
    else:
        return ""


print(get_suffix("1.txt", False))


# 练习4: 设计一个函数返回传入的列表中最大和第二大的元素的值
def max2(x):
    m1, m2 = (x[0], x[1]) if x[0] > x[1] else (x[1], x[0])
    for index in range(2, len(x)):
        if x[index] > m1:
            m2 = m1
            m1 = x[index]
        elif x[index] > m2:
            m2 = x[index]

    return m1, m2
