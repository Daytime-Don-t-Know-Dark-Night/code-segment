### Spark中Job-Stage-Task之间的关系

```mermaid
graph LR

A(Application) --> J1(Job1)
A(Application) --> J2(Job2)
A(Application) --> J3(Job3)

J1 --> S1(Stage1)
J1 --> S2(Stage2)
J1 --> S3(Stage3)

S1 --> T1(Task1)
S1 --> T2(Task2)
S1 --> T3(Task3)

```



