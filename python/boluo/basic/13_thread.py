from multiprocessing import Process
from os import getpid
from random import randint
from time import time, sleep


# 多进程
def download_task(filename):
    print("开始下载%s..." % filename)
    time_to_download = randint(5, 10)
    sleep(time_to_download)
    print("%s下载完成, 耗费了%d秒" % (filename, time_to_download))


def main():
    start = time()
    download_task("Java")
    download_task("Python")
    end = time()
    print("共耗费了%.2f秒" % (end - start))


def download_task2(filename):
    print("启动下载进程, 进程号[%d]" % getpid())
    print("开始下载%s..." % filename)
    time_to_download = randint(5, 10)
    sleep(time_to_download)
    print("%s下载完成, 耗费了%d秒" % (filename, time_to_download))


def main1():
    start = time()
    p1 = Process(target=download_task2, args=("Java",))
    p1.start()
    p2 = Process(target=download_task2, args=("Python",))
    p2.start()

    p1.join()
    p2.join()
    end = time()
    print("总共耗费了%.2f秒" % (end - start))


if __name__ == '__main__':
    main1()
