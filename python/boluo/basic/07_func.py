# 输入m, n, 计算C(m,n)
from random import randint
m = int(input("m: "))
n = int(input("n: "))
fm = 1
for num in range(1, m + 1):
    fm *= num

fn = 1
for num in range(1, n + 1):
    fn *= num

fmn = 1
for num in range(1, m - n + 1):
    fmn *= num

print(fm // fn // fmn)


# home branches test

def fac(num):
    """求阶乘"""
    res = 1
    for n in range(1, num+1):
        res *= n
    return res


m = int(input("m = "))
n = int(input("n = "))

print(fac(m) // fac(n) // fac(m-n))


# python中的函数可以有默认值, 也支持使用可变参数


def roll_dice(n=2):
    """摇色子"""
    total = 0
    for _ in range(n):
        total += randint(1, 6)
    return total


def add(a=0, b=0, c=0):
    return a+b+c


# 如果没有指定参数, 则使用默认参数
print(roll_dice())
print(roll_dice(3))

print(add())
print(add(1))
print(add(1, 2))
print(add(1, 2, 3))
# 传递参数时可以不按照设定的顺序进行传递
print(add(c=50, a=100, b=200))

