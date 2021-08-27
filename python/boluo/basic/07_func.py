# 输入m, n, 计算C(m,n)
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
