# 代码片段

[![build](https://github.com/Anduin2017/HowToCook/actions/workflows/build.yml/badge.svg)](https://github.com/Anduin2017/HowToCook/actions/workflows/build.yml)
[![License](https://img.shields.io/github/license/Anduin2017/HowToCook)](./LICENSE)
[![GitHub contributors](https://img.shields.io/github/contributors/Anduin2017/HowToCook)](https://github.com/Anduin2017/HowToCook/graphs/contributors)
[![npm](https://img.shields.io/npm/v/how-to-cook)](https://www.npmjs.com/package/how-to-cook)

## java

- [算法](./java/algorithm)
    - [二分查找](./java/algorithm/BinSearch.java)
    - [冒泡排序](./java/algorithm/BubbleSort.java)
    - [插入排序](./java/algorithm/InsertSort.java)
    - [选择排序](./java/algorithm/SelectSort.java)
    - [交换两数](./java/algorithm/Swap.java)
- [命令行](./java/command)
- [并发](./java/concurrent)
    - [并发调用函数](./java/concurrent/AsynchronousTest.java)
- [日期](./java/date)
    - [时间循环](./java/date/LocalDateTimeTest.java)
- [枚举](./java/enums)
- [异常](./java/exception)
- [泛型](./java/generic)
- [guava](./java/guava)
    - [布隆过滤器](./java/guava/Bloom.java)
    - [Guava缓存](./java/guava/GuavaCacheService.java)
- [hadoop](./java/hadoop)
    - [批量重命名文件](./java/hadoop/HdfsBatchVideo.java)
    - [HDFS操作](./java/hadoop/HdfsTest.java)
- [io流](./java/io)
- [jackson](./java/jackson)
    - [加载json文件](./java/jackson/LoadJson.java)
    - [node转DataSet](./java/jackson/NodeToDataSet.java)
- [jdbc](./java/jdbc)
- [kafka](./java/kafka)
    - [基础消费](./java/kafka/basic)
- [Optional](./java/optional)
- [正则表达式](./java/pattern)
- [队列](./java/queue)
    - [Kafka](./java/queue/kafka)
- [反射](./java/reflex)
    - [反射获取对象中的属性](./java/reflex/FieldTest.java)
- [Spark](./java/spark)
    - [缓存](./java/spark/CacheTest.java)
    - [连接HDFS](./java/spark/ConnectHDFS.java)
    - [数据倾斜](./java/spark/DataSkew.java)
    - [List转DataSet](./java/spark/ListToDataset.java)
    - [Stage划分](./java/spark/StageTest.java)
    - [UDAF](./java/spark/UDAFunc.java)
    - [UDF](./java/spark/UDFunc.java)
- [Stream](./java/stream)
- [字符串相关](./java/string)
    - [查找最后某个字符](./java/string/CharMatcherTest.java)
    - [切分保留分隔符](./java/string/SplitTest.java)
- [线程](./java/thread)
    - [死锁](./java/thread/DeadLock.java)
- [事务](./java/transaction)
- [特殊问题](./java/item)
    - [按顺序返回字符串可被切分的所有情况](./java/item/StringSplitter.java)
    - [分隔SQL中多余的括号](./java/item/SeparateParentheses.java)
    - [比较两个Json是否相同](./java/item/CompareJsonNode.java)

## scala

- [基础组件](./scala/basic)
    - [数组](./scala/basic/ArrayTest.scala)
    - [循环相关](./scala/basic/Circulation.scala)
    - [隐式转换](./scala/basic/Implicit.scala)
    - [Map及其操作](./scala/basic/MapTest.scala)
    - [模式匹配](./scala/basic/MatchTest.scala)
    - [字符串相关](./scala/basic/StringTest.scala)
- [日期](./scala/datetime)
    - [时间循环](./scala/datetime/TimeCycle.scala)
- [io流](./scala/io)
- [正则表达式](./scala/pattern)
    - [正则分组](./scala/pattern/GroupReplace.scala)
    - [字符串切割保留切割符](./scala/pattern/SplitSQL.scala)
- [spark-core](./scala/spark/core)
    - [输入](./scala/spark/core/Input.scala)
    - [输出](./scala/spark/core/Output.scala)
    - [累加器](./scala/spark/core/AccumulatorTest.scala)
    - [连接Elasticsearch数据库](./scala/spark/ConnectElasticsearch.scala)
    - [连接Mongo数据库](./scala/spark/ConnectMongo.scala)
    - [连接Oracle数据库](./scala/spark/ConnectOracle.scala)
    - [谓词下推](./scala/spark/PushDownPredicate.scala)
    - [RDD算子](./scala/spark/core/RDD.scala)
    - [Stage划分](./scala/spark/core/Stage.scala)
    - [UDF](./scala/spark/core/UDFunc.scala)
- [spark-sql](./scala/spark/sql)
- [spark-streaming](./scala/spark/streaming)
    - [单词计数](./scala/spark/streaming/WordCount.scala)
    - [创建DStream](./scala/spark/streaming/CreateDStream.scala)
    - [关闭DStream](./scala/spark/streaming/StopDStream.scala)
    - [DStream有状态操作](./scala/spark/streaming/DStreamStatus.scala)
    - [DStream无状态操作](./scala/spark/streaming/DStreamNoStatus.scala)
- [structured-streaming](./scala/spark/structured/streaming)
    - [单词计数](./scala/spark/structured/streaming/WordCount.scala)
    - [StructuredStreaming消费kafka数据](./scala/spark/structured/streaming/ConsumerKafka.scala)
- [SQL题](./scala/sql)
- [xml](./scala/xml)
- [特殊问题](./scala/item)
    - [比较两个Map是否相等](./scala/item/CompareMap.scala)
    - [SQL转Dataset算子](./scala/item/SqlToDataset.scala)

## python

- [小项目](./python/demo)
    - [获取NBA球员信息](./python/demo/01_nba.py)
