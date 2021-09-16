from time import sleep
from threading import Thread, Lock


class Account(object):

    # 运行上面的程序, 100个线程分别向账户中转入1元钱, 结果远远小于100元钱, 之所以出现这个情况是因为没有对银行账户这个"临界资源"
    # 加以保护, 多个线程同时向账户中存钱时, 会一起执行到 new_balance = self._balance + money 这行代码, 多个线程得到的账户
    # 余额都是初始状态下的0, 所以都是0上面做了+1的操作, 因此得到了错误的结果.
    # 在这种状态下, 锁就可以派上用场了, 我们可以通过锁来保护临界资源, 只有获得锁的线程才能访问临界资源
    # 而其他没有得到锁的线程只能被阻塞起来, 直到获得锁的线程释放了锁, 其他线程才有机会获得锁, 进而访问被保护的临界资源

    def __init__(self):
        self._balance = 0
        self._lock = Lock()
