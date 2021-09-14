def avg(x, y):
    return (x + y) / 2


def hello(name):
    print(name, "are you OK?")


def f(func_name, a, b):
    return func_name(a, b)


ret = f(avg, 1, 2)
print(ret)

# 1.函数的名字可以做参数
# 2.如果一个函数的名字被当做参数使用, 那么这个函数就被叫做回调函数


# 例: 订一个早上7点的闹钟
def playMusic():
    print("播放音乐")

# while True:
    # 获取现在的时间
    # if 现在是早上7点
    # playMusic()


import threading
timer = threading.Timer(3, playMusic)
timer.start()


# 学生按着学号中的年级排序
lst = ["2021020003", "2021010001", "2021011002", "2021021004", "2021031005"]

def byClass(xuehao):
    return xuehao[4:6]

ret = sorted(lst, key=byClass)
print(ret)
