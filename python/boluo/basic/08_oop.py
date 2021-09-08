# 面向对象编程

# 定义类
class Student(object):

    # __init__是一个特殊方法用于在创建对象时进行初始化操作
    # 通过这个方法我们可以为学生对象绑定name和age两个属性

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def study(self, course_name):
        print("%s正在学习%s" % (self.name, course_name))

    # PEP 8要求标识符的名字用全小写多个单词用下划线连接

