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
print(str[2])

# 字符串切片
