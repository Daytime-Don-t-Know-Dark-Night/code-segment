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


# 可变参数
def add(*args):
    total = 0
    for val in args:
        total += val
    return total


# 在调用add函数时可以传入0个或多个参数
print(add())
print(add(1))
print(add(1, 2))
print(add(1, 2, 3))
print(add(1, 3, 5, 7, 9))



# 需要说明的是: 如果我们导入的模块除了定义函数之外还有可以执行代码, 那么Python解释器在导入这个模块时就会执行这些代码, 事实上我们可能并不希望如此, 
# 因此如果我们在模块中编写了执行代码, 最好是将这些执行代码放入如下条件中, 这样的话除非直接运行该模块, if下的这些代码是不会执行的, 因为只有直接执行的模块名字才是__main__

def foo():
    pass

def bar():
    print("bar")
    pass


# __name__是Python中一个隐含的变量, 它代表了模块的名字
# 只有被Python解释器直接执行的模块的名字才是__main__

# 在别的模块导入module3时, 不会执行模块中if条件成立时的代码, 因为模块的名字是module3而不是__main__
if __name__ == '__main__':
    print("call foo()")
    foo()
    print("call bar()")
    bar()



# 变量的作用域

def foo():
    b = "hello"

    def bar():
        c = True
        print(a)
        print(b)
        print(c)

if __name__ == '__main__':
    a = 100
    foo()