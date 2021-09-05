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

# 列表 **********************************************************************************************************
list1 = [1, 3, 5, 7, 100]
print(list1)

list2 = ["hello"] * 3
print(list2)

print(len(list1))
print(list1[0])
print(list1[1])
print(list1[4])
print(list1[-1])  # 100
print(list1[-3])  # 5

# 修改list
list1[2] = 300
print(list1)

# 通过下标遍历列表
for index in range(len(list1)):
    print(list1[index])

# 增强for循环
for elem in list1:
    print(elem)

# 通过enumerate函数处理列表之后, 可以同事获得元素索引和值
for index, elem in enumerate(list1):
    print(index, elem)

# 向列表中添加元素以及从列表中移除元素*************************************************************************

list1 = [1, 3, 5, 7, 100]
list1.append(200)
list1.insert(1, 400)

# 合并两个列表
# list1.append([1000,2000])
list1 += [1000, 2000]
print(list1)
print(len(list1))

# 先通过成员运算判断元素是否在列表中, 如果存在就删除该元素
if 3 in list1:
    list1.remove(3)

if 1234 in list1:
    list1.remove(1234)

# 从指定的位置删除元素
print(list1)
list1.pop(0)
list1.pop(len(list1) - 1)
print(list1)

# 清空列表
list1.clear()

# 和字符串一样, 列表也可以做切片操作, 通过切片操作我们可以实现 **********************************************
fruits = ["grape", "apple", "strawberry", "waxberry"]
fruits += ["pitaya", "pear", "mango"]

# 列表切片
fruits2 = fruits[1:4]
print(fruits2)

# 可以通过完整切片操作来复制列表
fruits3 = fruits[:]
print(fruits3)
fruits4 = fruits[-3:-1]
print(fruits4)

fruits5 = fruits[::-1]
print(fruits5)

# 对列表的排序操作
list1 = ["orange", "apple", "zoo", "internationalization", "blueberry"]
list2 = sorted(list1)
# sorted函数返回列表排序后的拷贝不会修改传入的列表1
# 函数的设计就应该像sorted函数一样, 尽可能不产生副作用
list3 = sorted(list1, reverse=True)
list4 = sorted(list1, key=len)

print(list1)
print(list2)
print(list3)
print(list4)

# 给列表对象发出排序消息直接在列表对象上排序
list1.sort(reverse=True)
print(list1)






