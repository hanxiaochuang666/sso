/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/5/24 15:17:54                           */
/*==============================================================*/


drop table if exists user;

drop table if exists user_info;

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   Id                   int not null comment '����Id',
   LoginName            varchar(50) not null comment '��¼�˺�',
   Password             varchar(50) not null comment '����',
   Mobile               varchar(50) comment '�ֻ���',
   Email                varchar(50) comment '����',
   WeiboId              varchar(50) comment '΢��Id',
   WechatId             varchar(50) comment '΢��Id',
   QQOpenId             varchar(50) comment 'QQID',
   Status               int comment '״̬��0 ������ -1������',
   RegisterTime         datetime not null comment 'ע��ʱ��',
   LastLoginTIme        datetime not null comment '����¼ʱ��',
   LastLoginIP          varchar(50) comment '���IP',
   Count                datetime not null comment '��¼����',
   primary key (Id)
);

alter table user comment 'ǰ���û���';

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   Id                   int not null comment '����Id',
   UserId               int comment '�û�Id',
   NickName             varchar(50) comment '�ǳƣ��������ã�',
   HeadPhoto            varchar(50) comment 'ͷ��',
   Gender               varchar(50) comment '�Ա�0��Ů 1���У�',
   RealName             varchar(50) comment '��ʵ����',
   ProvinceId           varchar(50) comment 'ʡ��Id',
   CityId               varchar(50) comment '����Id',
   TownId               varchar(50) comment '����Id',
   Education            datetime comment 'ѧ����0���� 1��ר�� 2������ 3��˶ʿ 4����ʿ�����ϣ�',
   QQ                   datetime comment 'QQ��',
   primary key (Id)
);

alter table user_info comment '�û���Ϣ��';

alter table user_info add constraint FK_Reference_6 foreign key (UserId)
      references user (Id) on delete restrict on update restrict;

