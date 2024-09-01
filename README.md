# 代码片段

[![build](https://github.com/Anduin2017/HowToCook/actions/workflows/build.yml/badge.svg)](https://github.com/Anduin2017/HowToCook/actions/workflows/build.yml)
[![License](https://img.shields.io/github/license/Anduin2017/HowToCook)](./LICENSE)
[![GitHub contributors](https://img.shields.io/github/contributors/Anduin2017/HowToCook)](https://github.com/Anduin2017/HowToCook/graphs/contributors)
[![npm](https://img.shields.io/npm/v/how-to-cook)](https://www.npmjs.com/package/how-to-cook)

## java

- [算法](maven/java8/algorithm)
    - [二分查找](maven/java8/algorithm/BinSearch.java)
    - [冒泡排序](maven/java8/algorithm/BubbleSort.java)
    - [插入排序](maven/java8/algorithm/InsertSort.java)
    - [选择排序](maven/java8/algorithm/SelectSort.java)
    - [交换两数](maven/java8/algorithm/Swap.java)
- [命令行](maven/java8/command)
- [并发](maven/java8/concurrent)
    - [并发调用函数](maven/java8/concurrent/AsynchronousTest.java)
- [日期](maven/java8/date)
    - [时间循环](maven/java8/date/LocalDateTimeTest.java)
- [枚举](maven/java8/enums)
- [异常](maven/java8/exception)
- [泛型](maven/java8/generic)
- [guava](maven/java8/guava)
    - [布隆过滤器](maven/java8/guava/Bloom.java)
    - [Guava缓存](maven/java8/guava/GuavaCacheService.java)
- [hadoop](maven/java8/hadoop)
    - [批量重命名文件](maven/java8/hadoop/HdfsBatchVideo.java)
    - [HDFS操作](maven/java8/hadoop/HdfsTest.java)
- [io流](maven/java8/io)
- [jackson](maven/java8/jackson)
    - [加载json文件](maven/java8/jackson/LoadJson.java)
    - [node转DataSet](maven/java8/jackson/NodeToDataSet.java)
- [jdbc](maven/java8/jdbc)
- [kafka](maven/java8/kafka)
    - [基础消费](maven/java8/kafka/basic)
    - [异步发送消息](maven/java8/kafka/asyn)
- [Optional](maven/java8/optional)
- [正则表达式](maven/java8/pattern)
- [队列](maven/java8/queue)
    - [Kafka](maven/java8/queue/kafka)
- [反射](maven/java8/reflex)
    - [反射获取对象中的属性](maven/java8/reflex/FieldTest.java)
- [Spark](maven/java8/spark)
    - [缓存](maven/java8/spark/CacheTest.java)
    - [连接HDFS](maven/java8/spark/ConnectHDFS.java)
    - [数据倾斜](maven/java8/spark/DataSkew.java)
    - [List转DataSet](maven/java8/spark/ListToDataset.java)
    - [Stage划分](maven/java8/spark/StageTest.java)
    - [UDAF](maven/java8/spark/UDAFunc.java)
    - [UDF](maven/java8/spark/UDFunc.java)
- [Stream](./java/stream)
- [字符串相关](maven/string)
    - [查找最后某个字符](maven/string/CharMatcherTest.java)
    - [切分保留分隔符](maven/string/SplitTest.java)
- [线程](maven/java8/thread)
    - [死锁](maven/java8/thread/DeadLock.java)
- [事务](maven/java8/transaction)
- [特殊问题](maven/java8/item)
    - [按顺序返回字符串可被切分的所有情况](maven/java8/item/StringSplitter.java)
    - [分隔SQL中多余的括号](maven/java8/item/SeparateParentheses.java)
    - [比较两个Json是否相同](maven/java8/item/CompareJsonNode.java)

## scala

- [基础组件](maven/scala2/basic)
    - [数组](maven/scala2/basic/ArrayTest.scala)
    - [循环相关](maven/scala2/basic/Circulation.scala)
    - [隐式转换](maven/scala2/basic/Implicit.scala)
    - [Map及其操作](maven/scala2/basic/MapTest.scala)
    - [模式匹配](maven/scala2/basic/MatchTest.scala)
    - [字符串相关](maven/scala2/basic/StringTest.scala)
- [日期](maven/scala2/datetime)
    - [时间循环](maven/scala2/datetime/TimeCycle.scala)
- [io流](maven/scala2/io)
- [正则表达式](maven/scala2/pattern)
    - [正则分组](maven/scala2/pattern/GroupReplace.scala)
    - [字符串切割保留切割符](maven/scala2/pattern/SplitSQL.scala)
- [spark-core](maven/scala2/spark/core)
    - [输入](maven/scala2/spark/core/Input.scala)
    - [输出](maven/scala2/spark/core/Output.scala)
    - [累加器](maven/scala2/spark/core/AccumulatorTest.scala)
    - [连接Elasticsearch数据库](maven/scala2/spark/ConnectElasticsearch.scala)
    - [连接Mongo数据库](maven/scala2/spark/ConnectMongo.scala)
    - [连接Oracle数据库](maven/scala2/spark/ConnectOracle.scala)
    - [谓词下推](maven/scala2/spark/PushDownPredicate.scala)
    - [RDD算子](maven/scala2/spark/core/RDD.scala)
    - [Stage划分](maven/scala2/spark/core/Stage.scala)
    - [UDF](maven/scala2/spark/core/UDFunc.scala)
- [spark-sql](maven/scala2/spark/sql)
- [spark-streaming](maven/scala2/spark/streaming)
    - [单词计数](maven/scala2/spark/streaming/WordCount.scala)
    - [创建DStream](maven/scala2/spark/streaming/CreateDStream.scala)
    - [关闭DStream](maven/scala2/spark/streaming/StopDStream.scala)
    - [DStream有状态操作](maven/scala2/spark/streaming/DStreamStatus.scala)
    - [DStream无状态操作](maven/scala2/spark/streaming/DStreamNoStatus.scala)
- [structured-streaming](maven/scala2/spark/structured/streaming)
    - [单词计数](maven/scala2/spark/structured/streaming/WordCount.scala)
    - [StructuredStreaming消费kafka数据](maven/scala2/spark/structured/streaming/ConsumerKafka.scala)
- [SQL题](maven/scala2/sql)
- [xml](maven/scala2/xml)
- [特殊问题](maven/scala2/item)
    - [比较两个Map是否相等](maven/scala2/item/CompareMap.scala)
    - [SQL转Dataset算子](maven/scala2/item/SqlToDataset.scala)

## python

- [小项目](./python/demo)
    - [获取NBA球员信息](./python/demo/01_nba.py)
