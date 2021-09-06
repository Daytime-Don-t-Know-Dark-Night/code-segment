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


# 生成式和生成器 ********************************************************************************************
# 使用列表的生成式语法来创建列表
f = [x for x in range(1, 10)]
print(f)

f = [x + y for x in "ABCDE" for y in "1234567"]
print(f)

# 用列表的生成表达式语法创建列表容器
# 用这种语法创建列表之后元素已经准备就绪, 所以需要耗费较多的内存空间
f = [x ** 2 for x in range(1, 1000)]
print(f)

# 注意下面的代码创建的不是一个列表, 而是一个生成器对象
# 通过生成器可以获取到数据但它不占用额外的空间存储数据
# 每次需要数据的时候就通过内部的运算得到数据(需要花费额外的时间)

f = (x ** 2 for x in range(1, 1000))
print(f)
for val in f:
    print(val)

# 另外一种定义生成器的方式, 通过yield关键字将一个普通函数改造成生成器函数
# 生成菲波那切数列的生成器


def fib(n):
    a, b = 0, 1
    for _ in range(n):
        a, b = b, a+b
        yield a


def main():
    for val in fib(20):
        print(val)


if __name__ == '__main__':
    main()


# 使用元组 *****************************************************************************************
# Python中的元组与列表类似也是一种容器数据类型, 不同的是元组中的元素不能修改,

t = ("丁超", 20, "杭州")
print(t)

# 获取元组中的数据
print(t[0])
print(t[3])

for member in t:
    print(member)

# 重新给元组赋值
# t[0] = "boluo"
t = ("菠萝", 20, "上海")
print(t)

# 将元组转换成列表
person = list(t)
print(person)

# 列表中可以修改元素
person[0] = "李小龙"
person[1] = 25
print(person)

# 将列表转换成元组
fruits_list = ["apple", "banana", "orange"]
fruits_tuple = tuple(fruits_list)
print(fruits_tuple)

# 使用集合 ***************************************************************************************************
# Python中集合不允许有重复元素, 而且可以进行交集, 并集, 差集等元素
# 创建集合的字面量语法
set1 = {1, 2, 3, 3, 3, 2}
print(set1)
print("length = ", len(set1))

# 创建集合的构造器语法
set2 = set(range(1, 10))
set3 = set((1, 2, 3, 3, 2, 1))
print(set2, set3)

# 创建集合的推导式语法
set4 = {num for num in range(1, 100) if num % 3 == 0 or num % 5 == 0}
print(set4)


# 向集合添加元素和从集合删除元素
set1.add(4)
set1.add(5)
set2.update([11, 12])
set2.discard(5)

if 4 in set2:
    set2.remove(4)
print(set1, set2)
print(set3.pop())
print(set3)


print(set1)
print(set2)
print(set3)

# 集合的交集, 并集, 差集, 对称差运算
print(set1 & set2)
print(set1 | set2)
print(set1 - set2)
print(set1 ^ set2)

# 判断子集和超集
print(set2 <= set1)
print(set3 <= set1)
print(set1 >= set2)
print(set1 >= set3)


# 使用字典 ***************************************************************************************
# 创建字典
scores = {"丁超": 20, "菠萝": 30, "李小龙": 40}
print(scores)

# 创建字典的构造器语法
items1 = dict(one=1, two=2, three=3, four=4)
# 通过zip函数将两个序列压成字典
items2 = dict(zip(["a", "b", "c"], "123"))
print(items2)
# 创建字典的推导式语法
items3 = {num: num ** 2 for num in range(1, 10)}
print(scores["丁超"])
print(scores["菠萝"])

# 遍历字典
for key in scores:
    print(f'{key}:{scores[key]}')

# 更新字典中的元素
scores["丁超"] = 21
scores["菠萝"] = 31
scores.update(bar=20, foo=30)
print(scores)

if "武则天" in scores:
    print(scores["武则天"])
print(scores.get("武则天"))

# get方法可以设置默认值
print(scores.get("武则天", 60))

# 删除字典中的元素
print(scores.popitem())
print(scores.pop("菠萝", 21))

# 清空字典
scores.clear()
