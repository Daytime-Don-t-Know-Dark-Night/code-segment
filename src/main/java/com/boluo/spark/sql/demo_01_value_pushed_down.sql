use dingchao_db;

-- create table
CREATE TABLE demo_01 (
    kafka_topic varchar(5000),
    kafka_partition bigint(20),
    kafka_offset bigint(20),
    kafka_raw_msg varchar(5000),
    created_timestamp timestamp
);

-- truncate table
truncate table demo_01;

-- mock data
insert into demo_01 values (null, 0, 1, 'start,AU,2025-03-24',null);
insert into demo_01 values (null, 0, 2, 'A,B,C',null);
insert into demo_01 values (null, 0, 3, 'A,B,D',null);
insert into demo_01 values (null, 0, 4, 'completed',null);
insert into demo_01 values (null, 0, 5, 'start,AU,2025-03-25',null);
insert into demo_01 values (null, 0, 6, 'A,B,E',null);
insert into demo_01 values (null, 0, 7, 'A,B,F',null);
insert into demo_01 values (null, 0, 8, 'completed',null);
insert into demo_01 values (null, 0, 9, 'start,AU,2025-03-26',null);
insert into demo_01 values (null, 0, 10, 'A,B,G',null);
insert into demo_01 values (null, 0, 11, 'A,B,H',null);
insert into demo_01 values (null, 0, 12, 'completed',null);

-- check
select * from demo_01 order by kafka_offset;



--
with tmp1 as (
    select kafka_offset,
           kafka_raw_msg,
           if(kafka_raw_msg like 'start%', split(kafka_raw_msg, ',')[1], null) branch,
           if(kafka_raw_msg like 'start%', split(kafka_raw_msg, ',')[2], null) eod_date
    from demo_01
),
tmp2 as (
    select kafka_offset, kafka_raw_msg, branch, eod_date, sum(if(eod_date is not null, 1, 0)) over (order by cast(kafka_offset as bigint)) tmp_count
    from tmp1
),
tmp3 as (
    select kafka_offset,
        kafka_raw_msg,
        first_value(branch) over (partition by tmp_count order by cast(kafka_offset as bigint)) branch,
        first_value(eod_date) over (partition by tmp_count order by cast(kafka_offset as bigint)) eod_date
    from tmp2
)
select *
from tmp3
where kafka_raw_msg not like 'start%' and kafka_raw_msg not like 'completed%';