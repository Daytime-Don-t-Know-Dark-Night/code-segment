### Spark应用执行流程

![Spark](./image/Spark运行架构.png)

- 提交一个Spark作业之后, 作业会启动一个对应的Driver进程, Driver进程要做的第一件事情, 
  就是向资源管理器申请资源, 申请的资源就是Executor进程
  
- 在申请到作业所需的资源之后, Driver进程就会开始调度和执行我们编写的作业代码, 
  Driver进程会将我们编写的Spark作业代码拆分为多个stage, 每个stage执行一部分代码, 
  并为每个stage创建一批task, 然后将这些task分配到各个executor进程中执行, 
  task是最小的计算单元, 负责执行一模一样的计算逻辑(代码片段), 只是每个task处理的数据不同. 
  一个stage中的所有task执行完毕之后, 会在本地磁盘写入中间计算结果, 如此往复直至完成
  




















