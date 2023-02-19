#!/bin/bash

# 计算两个参数的和

function sum()
{
    s=0;
    s=s+$[$1+$2]
    echo $s
}

read -p "input your param1: " P1
read -p "input your param2: " P2

sum $P1 $P2

