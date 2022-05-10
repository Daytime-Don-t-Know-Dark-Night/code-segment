### SQL调优

首先第一点是理解几个重要的Spark SQL的参数配置, 将这些参数调整合适的变量
```sql
spark.sql.files.maxPartitionBytes
默认128MB, 单个分区读取的最大文件大小


```

第二点是利用SparkSQL的执行特点, SparkSQL是懒加载的, 只有遇到行动算子的时候才会执行, 


其次在SparkSQL中的调优, 一个很重要的原则就是避免shuffle
将开窗函数中分组相同的函数放在一起, 他们就会被分配到同一个stage中


广播join

广播join适用于小表join大表的时候, 因为我们知道join是一个会产生shuffle的操作, 
优化操作就是广播小的表, 将小RDD的数据广播到每一个executor中, 然后在各executor的task中对各partition
的大RDD和小RDD进行join, 就不会再产生shuffle

