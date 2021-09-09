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
    def watch_movie(self):
        if self.age < 18:
            print("%s只能观看<<熊出没>>" % self.name)
        else:
            print("%s正在观看岛国电影" % self.name)


# 当我们定义好一个类之后, 可以通过下面的方式来创建对象并给对象发送消息
def main():
    # 创建学生对象并指定姓名和年龄
    stu1 = Student("dingc", 20)
    stu1.study("Python程序设计")
    stu1.watch_movie()

    stu2 = Student("qidai", 15)
    stu2.study("思想品德")
    stu2.watch_movie()


if __name__ == '__main__':
    main()


# 在Python中, 属性和方法的访问权限只有两种, 公开的和私有的, 如果希望属性是私有的, 在给属性命名时可以用两个下划线作为开头

