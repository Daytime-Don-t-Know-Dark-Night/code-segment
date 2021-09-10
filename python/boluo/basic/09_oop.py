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

def main():
    person = Person("dingc", 10)
    person.play()
    person.age = 20
    person.play()

if __name__ == "__main__":
    main()

