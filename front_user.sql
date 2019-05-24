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
   Id                   int not null comment '自增Id',
   LoginName            varchar(50) not null comment '登录账号',
   Password             varchar(50) not null comment '密码',
   Mobile               varchar(50) comment '手机号',
   Email                varchar(50) comment '邮箱',
   WeiboId              varchar(50) comment '微博Id',
   WechatId             varchar(50) comment '微信Id',
   QQOpenId             varchar(50) comment 'QQID',
   Status               int comment '状态：0 ：正常 -1：冻结',
   RegisterTime         datetime not null comment '注册时间',
   LastLoginTIme        datetime not null comment '最后登录时间',
   LastLoginIP          varchar(50) comment '最后IP',
   Count                datetime not null comment '登录次数',
   primary key (Id)
);

alter table user comment '前端用户表';

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   Id                   int not null comment '自增Id',
   UserId               int comment '用户Id',
   NickName             varchar(50) comment '昵称（优先设置）',
   HeadPhoto            varchar(50) comment '头像',
   Gender               varchar(50) comment '性别（0：女 1：男）',
   RealName             varchar(50) comment '真实姓名',
   ProvinceId           varchar(50) comment '省份Id',
   CityId               varchar(50) comment '城市Id',
   TownId               varchar(50) comment '乡镇Id',
   Education            datetime comment '学历（0：无 1：专科 2：本科 3：硕士 4：博士及以上）',
   QQ                   datetime comment 'QQ号',
   primary key (Id)
);

alter table user_info comment '用户信息表';

alter table user_info add constraint FK_Reference_6 foreign key (UserId)
      references user (Id) on delete restrict on update restrict;

