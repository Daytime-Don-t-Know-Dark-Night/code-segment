# 使用@property包装器来包装getter和setter方法, 使得对属性的访问安全方便

class Person(object):

    def __init__(self, name, age):
        self._name = name
        self._age = age

    # 访问器 - getter方法
    @property
    def name(self):
        return self._name

    @property
    def age(self):
        return self._age

    # 修改器 - setter
    @age.setter
    def age(self, age):
        self._age = age

    def play(self):
        if self._age < 16:
            print("%s飞行棋" % self._name)
        else:
            print("%s斗地主" % self._name)


# def main():
#     person = Person("dingc", 10)
#     person.play()
#     person.age = 20
#     person.play()
#
#
# if __name__ == "__main__":
#     main()


# Python是一门动态语言, 允许在程序运行时给对象绑定新的属性或方法, 或者对已绑定的属性和方法进行解绑
# 如果我们需要限定自定义类型的对象只能绑定某些属性, 可以通过在类中定义 __slots__ 变量来进行限定
# __slots__只对当前类的对象生效, 对子类不起作用

class Person(object):
    # 限定Person对象只能绑定_name, _age, _gender属性
    __slots__ = ("_name", "_age", "_gender")

    def __init__(self, name, age):
        self._name = name
        self._age = age

    @property
    def name(self):
        return self._name

    @property
    def age(self):
        return self._age

    @age.setter
    def age(self, age):
        self._age = age

    def play(self):
        if self._age < 16:
            print("%s飞行棋" % self._name)
        else:
            print("%s斗地主" % self._name)


# 上面我们在类中定义的方法都是对象方法, 也就是说这些方法都是发送给对象的消息
# 如果我们写一个"三角形"类, 通过传入三条边长来构造三角形, 并提供周长和面积的方法, 但是传入的三条边长未必能够构造出三角形对象
# 因此我们可以先写一个方法来验证三条边长是否可以构成三角形, 所以这个方法是属于三角形类而不是三角形对象的, 我们可以使用静态方法

from math import sqrt


class Triangle(object):

    def __init__(self, a, b, c):
        self._a = a
        self._b = b
        self._c = c

    @staticmethod
    def is_valid(a, b, c):
        return a + b > c and b + c > a and a + c > b

    def perimeter(self):
        return self._a + self._b + self._c

    def area(self):
        half = self.perimeter() / 2
        return sqrt(half * (half - self._a)) * (half - self._b) * (half - self._c)


def main():
    a, b, c = 3, 4, 5
    # 静态方法和类方法都是通过给类发消息来调用的
    if Triangle.is_valid(a, b, c):
        t = Triangle(a, b, c)
        print(t.perimeter())

        # 也可以通过给类发消息来调用对象方法但是要传入对象作为参数
        print(t.area())
        print(Triangle.area(t))

    else:
        print("无法构成三角形")


if __name__ == '__main__':
    main()
