# 水仙花数
# 数字每个位上数字的立方和正好等于它本身: 1^3 + 5^3 + 3^3 = 153

for num in range(100, 1000):
    low = num % 10
    mid = num // 10 % 10
    high = num // 100
    if num == low ** 3 + mid ** 3 + high ** 3:
        print(num)

# 百钱买百鸡
# 公鸡5元一只, 母鸡3元一只, 小鸡一元3只
for x in range(0, 20):
    for y in range(0, 33):
        z = 100 - x - y
        if 5 * x + 3 * y + z / 3 == 100:
            print("公鸡: %d, 母鸡: %d, 小鸡: %d" % (x, y, z))