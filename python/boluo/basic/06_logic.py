# 水仙花数
# 数字每个位上数字的立方和正好等于它本身: 1^3 + 5^3 + 3^3 = 153

for num in range(100, 1000):
    low = num % 10
    mid = num // 10 % 10
    high = num // 100
    if num == low ** 3 + mid ** 3 + high ** 3:
        print(num)


