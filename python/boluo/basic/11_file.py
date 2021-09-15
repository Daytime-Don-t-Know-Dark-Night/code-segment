import time
import json
import requests
from math import sqrt


def main():
    f = None
    try:
        f = open("./res/将进酒.txt", "r", encoding="utf-8")
        print(f.read())

    except FileNotFoundError:
        print("无法打开指定的文件!")
    except LookupError:
        print("指定了未知的编码!")
    except UnicodeDecodeError:
        print("读取文件时解码错误!")
    finally:
        if f:
            f.close()


# 除了使用文件对象的read方法读取文件之外, 还可以使用for-in循环逐行读取或者用readlines方法将文件按行读取到一个列表中
def main2():
    # 一次性读取整个文件内容
    with open("./res/将进酒.txt", "r", encoding="utf-8") as f:
        print(f.read())

    # 通过for-in循环逐行读取
    with open("./res/将进酒.txt", mode="r") as f:
        for line in f:
            print(line, end="")
            time.sleep(0.5)
    print()

    # 读取文件按行读取到列表中
    with open("./res/将进酒.txt") as f:
        lines = f.readlines()
    print(lines)


# **********************************************************************************************************************
# 将文本信息写入文件: 在使用open函数时指定文件名并将文件模式设置为w, 追加设为a, 如果要写入的文件不存在会自动创建
# 将1-9999之间的素数分别写入三个文件中
def is_prime(n):
    """判断素数"""
    assert n > 0
    for factor in range(2, int(sqrt(n)) + 1):
        if n % factor == 0:
            return False
    return True if n != 1 else False


def main3():
    filenames = ("./res/a.txt", "./res/b.txt", "./res/c.txt")
    fs_list = []
    try:
        for filename in filenames:
            fs_list.append(open(filename, "w", encoding="utf-8"))
        for number in range(1, 10000):
            if is_prime(number):
                if number < 100:
                    fs_list[0].write(str(number) + "\n")
                elif number < 1000:
                    fs_list[1].write(str(number) + "\n")
                else:
                    fs_list[2].write(str(number) + "\n")
    except IOError as ex:
        print(ex)
        print("写入文件时发生错误!")
    finally:
        for fs in fs_list:
            fs.close()
    print("操作完成!")


# **********************************************************************************************************************
# 读取二进制文件
def main4():
    try:
        with open("./res/ball.png", "rb") as fs1:
            data = fs1.read()
            print(type(data))  # <class 'bytes'>
        # with open("./res/ball-game.png", "wb") as fs2:
        #     fs2.write(data)
    except FileNotFoundError as e:
        print("指定的文件无法打开!")
    except IOError as e:
        print("读写文件时出现错误!")
    print("结束!")


# **********************************************************************************************************************
# 读取json文件
# json模块四个比较重要的函数:

# dump(): 将python对象按照json格式序列化到文件中
# dumps(): 将python对象处理成json格式的字符串
# load(): 将文件中的json数据反序列化成对象
# loads(): 将字符串的内容反序列化成python对象

# 序列化: 是指将数据结构或对象状态转换为可以存储或者传输的形式, 这样在需要的时候能够恢复到原先的状态

def main5():
    mydict = {
        'name': '骆昊',
        'age': 38,
        'qq': 957658,
        'friends': ['王大锤', '白元芳'],
        'cars': [
            {'brand': 'BYD', 'max_speed': 180},
            {'brand': 'Audi', 'max_speed': 280},
            {'brand': 'Benz', 'max_speed': 320}
        ]
    }
    try:
        with open("./res/data.json", "w", encoding="utf-8") as fs:
            json.dump(mydict, fs)
    except IOError as e:
        print(e)
    print("数据保存完成!")


def main6():
    resp = requests.get("http://api.tianapi.com/guonei?key=8cf8175d0968f753a4f5d402215c2e67&num=10")
    data_model = json.loads(resp.text)
    for news in data_model["newslist"]:
        print(news["title"])


if __name__ == '__main__':
    main6()
