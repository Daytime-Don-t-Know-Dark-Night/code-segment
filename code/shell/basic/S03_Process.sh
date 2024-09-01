#!/bin/bash

# 需求: 输入一个数字, 如果是1, 输出 dingc, 如果是2, 输出 boluo, 输入其它, 什么也不输出

# if 使用
if [ $1 -eq 1 ]
then
    echo "dingc"
elif [ $1 -eq 2 ]
then
    echo "boluo"
fi


# case 使用: 其它情况输出 other
case $1 in
    1)
        echo "dingc"
        ;;
    2)
        echo "boluo"
        ;;
    *)
        echo "other"
        ;;
esac


# for 循环1 使用: 从1加到100
sum=0
for((i=1;i<=100;i++))
    do
        sum=$[$s+$i]

    done

echo $sum


# for 循环2 使用:
for i in $*
    do
        echo "输入的参数为: $i"
    done



# While 循环
s=0
i=1
while [ $i -le 100 ]
    do
        s=$[$s + $i]
        i=$[$i + 1]
    done
echo $s



