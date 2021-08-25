import random

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
        print("%d*%d=%d" % (i, j, i * j), end="\t")
    print()
