from multiprocessing import Process
from os import getpid
from threading import Thread
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
    # 通过Process类创建进程对象, 通过target参数传入一个函数来表示进程启动后要执行的代码, args是一个元组, 代表传递给函数的参数
    # Process对象的start方法用来启动进程, join方法表示等待进程执行结束
    start = time()
    p1 = Process(target=download_task2, args=("Java",))
    p1.start()
    p2 = Process(target=download_task2, args=("Python",))
    p2.start()

    p1.join()
    p2.join()
    end = time()
    print("总共耗费了%.2f秒" % (end - start))


counter = 0


def sub_task(string):
    global counter
    while counter < 10:
        print(string, end="", flush=True)
        counter += 1
        sleep(0.01)


def main2():
    # 启动两个进程, 一个输出Ping, 一个输出Pong, 两个进程输出的Ping和Pong加起来一共10个
    Process(target=sub_task, args=("Ping",)).start()
    Process(target=sub_task, args=("Pong",)).start()


# **********************************************************************************************************************
# Python多线程
def download(filename):
    print("开始下载%s..." % filename)
    time_to_download = randint(5, 10)
    sleep(time_to_download)
    print("%s下载完成!耗费了%d秒" % (filename, time_to_download))


def main3():
    start = time()
    t1 = Thread(target=download, args=("Java",))
    t1.start()
    t2 = Thread(target=download, args=("Python",))
    t2.start()
    t1.join()
    t2.join()
    end = time()
    print("总共耗费了%.3f秒" % (end - start))


class DownloadTask(Thread):

    # 我们可以直接使用threading模块的Thread类来创建线程, 也可以通过继承Thead类的方式来创建自定义的线程类, 再创建线程对象并启动线程
    def __init__(self, filename):
        super().__init__()
        self._filename = filename

    def run(self):
        print("开始下载%s..." % self._filename)
        time_to_download = randint(5, 10)
        sleep(time_to_download)
        print("%s下载完成, 耗费了%d秒" % (self._filename, time_to_download))


def main4():
    start = time()
    t1 = DownloadTask("Java")
    t1.start()
    t2 = DownloadTask("Python")
    t2.start()
    t1.join()
    t2.join()
    end = time()
    print("总共耗费了%.2f秒" % (end - start))


class Account(object):
    # 因为多个线程可以共享进程的内存空间, 因此要是实现多个线程间的通信相对简单, 大家能想到的最直接的办法就是设置一个全局变量,
    # 多个线程共享这个全局变量即可, 但是当多个线程共享同一个变量(资源)的时候, 很有可能产生不可控的结果从而导致程序失效甚至崩溃
    # 如果一个资源被多个线程竞争使用, 那么我们通常称之为"临界资源", 对"临界资源"的访问需要加上保护, 否则资源会处于"混乱"的状态
    # 下面的例子演示了100个线程向一个银行账户转账(1元钱)的场景, 在这个例子中, 银行账户就是一个临界资源, 在没有保护的情况下有可能会得到错误的结果
    def __init__(self):
        self._balance = 0

    def deposit(self, money):
        # 计算存款后的余额
        new_balance = self._balance + money
        # 模拟受理存款业务需要0.01秒的时间
        sleep(0.01)
        # 修改账户余额
        self._balance = new_balance

    @property
    def balance(self):
        return self._balance


class AddMoneyThread(Thread):

    def __init__(self, account, money):
        super().__init__()
        self._account = account
        self._money = money

    def run(self):
        self._account.deposit(self._money)


def main5():
    account = Account()
    threads = []
    # 创建100个存款的线程向同一个账户中存钱
    for _ in range(100):
        t = AddMoneyThread(account, 1)
        threads.append(t)
        t.start()

    # 等所有存款的线程都执行完毕
    for t in threads:
        t.join()
    print("账户余额为: %d元" % account.balance)


if __name__ == '__main__':
    main5()
