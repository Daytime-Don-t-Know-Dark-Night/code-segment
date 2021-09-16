import re

"""
验证输入用户名和QQ号是否有效并给出对应的提示信息
要求: 用户名必须由字母, 数字或下划线构成且长度在6-20字符之间, QQ号是5-12位的数字且首位不能为0
"""


def main():
    username = input("请输入用户名: ")
    qq = input("请输入QQ号: ")
    m1 = re.match(r"^[0-9a-zA-Z_]{6,20}$", username)
    if not m1:
        print("请输入有效的用户名!")
    m2 = re.match(r"^[1-9]\d{4,11}$", qq)
    if not m2:
        print("请输入有效的QQ号")
    if m1 and m2:
        print("输入信息有效!")


# 从一段文字中提取出国内手机号码
def main2():
    print("todo")


# 替换字符串中的不良内容
def main3():
    sentence = "你丫是傻叉吗? 我操你大爷的. Fuck you."
    purified = re.sub("[操肏艹]|fuck|shit|傻[比屄逼叉缺吊屌]|煞笔", "*", sentence, flags=re.IGNORECASE)
    print(purified)


# 拆分长字符串
def main4():
    poem = '窗前明月光, 疑是地上霜. 举头望明月, 低头思故乡. '
    sentence_list = re.split(r'[,.]', poem)
    while " " in sentence_list:
        sentence_list.remove(" ")
    print(sentence_list)


if __name__ == '__main__':
    main4()
