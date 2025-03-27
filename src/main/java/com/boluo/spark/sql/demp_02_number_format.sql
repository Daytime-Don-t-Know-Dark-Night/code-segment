use dingchao_db;

-- demo 02
CREATE TABLE demo_02 (
    hashkey varchar(5000),
    deal_number varchar(5000),
    comment varchar(5000),
    created_timestamp timestamp
);

-- mock data
insert into demo_02 values (md5('001'), 'DEAL001', '-1.922E-4', null);
insert into demo_02 values (md5('002'), 'DEAL002', '4.1E-5', null);
insert into demo_02 values (md5('003'), 'DEAL003', '2.0E-6', null);
insert into demo_02 values (md5('004'), 'DEAL004', '2.3334989', null);
insert into demo_02 values (md5('005'), 'DEAL005', '-2.333498', null);
insert into demo_02 values (md5('006'), 'DEAL006', '2.3000000', null);
insert into demo_02 values (md5('007'), 'DEAL007', '-2.300000', null);
insert into demo_02 values (md5('008'), 'DEAL008', 'AUCNEA354', null);
insert into demo_02 values (md5('009'), 'DEAL009', '428ACGA95', null);
insert into demo_02 values (md5('010'), 'DEAL010', '19.57123487722316968197351464349534681', null);

truncate table demo_02;
drop table demo_02;

select * from demo_02 order by deal_number;


-- 1. number format: remains unchanged
-- 2. string format: null
-- 3. kexuejishu: convert to number
select hashkey, deal_number, comment,
        case when comment rlike '^-?\\d+(\\.\\d+)?$' then comment
        else format_number(cast(comment as decimal(37,18)), '####################.####################')
        end mid_rate,
        created_timestamp
from demo_02;
