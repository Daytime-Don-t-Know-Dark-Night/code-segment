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
