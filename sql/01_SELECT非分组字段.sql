-- MySQL中支持直接SELECT非分组字段
select a, b, c, d, e
from table
group by a, b

-- ClickHouse不支持直接SELECT非分组字段, 执行同样的SQL会报错
-- Column `stage` is not under aggregate function and not in GROUP BY: While processing ...

-- 解决方案
select a, b, c, d, e
from (
    select a, b
    from table
    group by a, b
) x
left join (
    select a, b, c, d, e
    from table
) y
on x.a = y.a and x.b = y.b
