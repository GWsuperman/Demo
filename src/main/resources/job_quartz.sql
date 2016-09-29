prompt PL/SQL Developer import file
prompt Created on 2016年9月29日 by Administrator
set feedback off
set define off
prompt Dropping JOB_QUARTZ...
drop table JOB_QUARTZ cascade constraints;
prompt Creating JOB_QUARTZ...
create table JOB_QUARTZ
(
  JOB_ID    VARCHAR2(25),
  JOB_NAME  VARCHAR2(100),
  JOB_GROUP VARCHAR2(100),
  JOB_TIME  VARCHAR2(100),
  JOB_STAT  CHAR(1) default 1
)
tablespace SYSTEM
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 8K
    minextents 1
    maxextents unlimited
  );
comment on column JOB_QUARTZ.JOB_STAT
  is '任务状态    0：暂停      1：启动';

prompt Disabling triggers for JOB_QUARTZ...
alter table JOB_QUARTZ disable all triggers;
prompt Loading JOB_QUARTZ...
insert into JOB_QUARTZ (JOB_ID, JOB_NAME, JOB_GROUP, JOB_TIME, JOB_STAT)
values ('2', '2', '2', '0/10 * * * * ?', '0');
insert into JOB_QUARTZ (JOB_ID, JOB_NAME, JOB_GROUP, JOB_TIME, JOB_STAT)
values ('3', '3', '3', '0/5 * * * * ?', '0');
insert into JOB_QUARTZ (JOB_ID, JOB_NAME, JOB_GROUP, JOB_TIME, JOB_STAT)
values ('1', '1', '1', '0/5 * * * * ?', '0');
commit;
prompt 3 records loaded
prompt Enabling triggers for JOB_QUARTZ...
alter table JOB_QUARTZ enable all triggers;
set feedback on
set define on
prompt Done.
