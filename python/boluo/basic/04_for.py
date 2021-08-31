import random
from math import sqrt

# for-in循环

# 1-100求和
sum = 0
for x in range(101):
    sum += x
print(sum)

# 可以产生0-100的整数, 取不到101
range(101)

# 1-100的整数
range(1, 100)

# 1-100的奇数, 2是步长
range(1, 100, 2)

# 100-1的偶数, -2是步长
range(100, 0, -2)

sum1 = 0
for x in range(1, 100):
    if x % 2 == 0:
        sum1 += x
print(sum1)

# 猜数字
answer = random.randint(1, 100)
counter = 0
while True:
    counter += 1
    number = int(input("请输入: "))
    if number < answer:
        print("大一点")
    elif number > answer:
        print("小一点")
    else:
        print("恭喜你, 答对了")
        break
    print("你一共猜了%d次" % counter)
    if counter > 7:
        print("总次数大于7次")

# 输出乘法口诀表
for i in range(1, 10):
    for j in range(1, i + 1):
        print("%d*%d=%d" % (i, j, i * j), "\t")
    print()

# 判断一个正整数是不是素数(只能被1和自身整除)
num = int(input("请输入一个正整数: "))
end = int(sqrt(num))
is_prime = True

for x in range(2, end + 1):
    if num % x == 0:
        is_prime = False
        break

if is_prime and num != 1:
    print("%d是素数" % num)
else:
    print("%d不是素数" % num)

# 计算两个数的最大公约数和最小公倍数
# 两个数的最大公约数是两个数的公共因子中最大的那个数, 最小公倍数是能够同时被两个数整除的最小的那个数
x = int(input("x = "))
y = int(input("y = "))
# 如果x大于y就交换x和y的值
if x > y:
    x, y = y, x

# 从两个数中较大的数开始做递减的循环
for factor in range(x, 0, -1):
    if x % factor == 0 and y % factor == 0:
        print("%d和%d的最大公约数是%d" % (x, y, factor))
        print("%d和%d的最小公倍数是%d" % (x, y, x * y // factor))
        break

