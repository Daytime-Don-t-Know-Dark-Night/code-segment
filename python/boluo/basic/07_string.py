# 字符串
s1 = 'str1'
s2 = "str2"

# 以三个双引号或单引号开头的字符串可以换行
s3 = """
hello, 
world!
"""

s1 = '\'hello, world!\''
s2 = '\n\\hello, world!\\\n'
print(s1, s2, end='')


# 不希望转义, 在字符串最前面加r
s1 = r'\'hello, world!\''
s2 = r'\n\\hello, world!\\\n'
print(s1, s2, end='')


s1 = 'hello' * 3
print(s1)

s2 = 'world'
s1 += s2

print(s1)
print('ll' in s1)
print('good' in s1)

str2 = 'abc123456'
print(str2[2])  # c

# 字符串切片
print(str2[2:5])  # c12
print(str2[2:])  # c123456
print(str2[2::2])  # c246
print(str2[::2])  # ac246
print(str2[::-1])  # 654321cba
print(str2[-3:-1])  # 45


# 处理字符串
str1 = 'hello, world!'
print(len(str1))

# 获得字符串首字母大写的拷贝
print(str1.capitalize())  # Hello, world!

# 获得字符串每个单词首字母大写的拷贝
print(str1.title())

# 获得字符串变大写后的拷贝
print(str1.upper())

# 从字符串中查找子串所在位置
print(str1.find('or'))
print(str1.find('shit'))

# 与find类似但找不到子串时会引发异常
# print(str1.index('or'))
# print(str1.index('shit'))

# 检查字符串是否以指定的字符串开头或结尾
print(str1.startswith('He'))
print(str1.startswith('hel'))
print(str1.endswith('!'))

# 将字符串以指定的宽度居中并在两侧填充指定的字符
print(str1.center(50, '*'))
print(str1.rjust(50, '*'))

# 检查字符串是否由数字或字母构成
print(str1.isdigit())
print(str1.isalpha())
print(str1.isalnum())

# 对字符串两侧空格过滤
print(str1.strip())


# 格式化输出字符串
a, b = 5, 10
print('%d * %d = %d' % (a, b, a * b))
print('{0} * {1} = {2}'.format(a, b, a * b))
print(f'{a} * {b} = {a * b}')


# 列表
list1 = [1, 3, 5, 7, 100]
print(list1)

