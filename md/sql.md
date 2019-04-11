# 数据库sql相关
2019-04-01

## 一 数据库的创建
```sql
create database restaurants default character set utf8 collate utf8_general_ci;
```

## 二 表结构的创建
### 1 菜单表
```sql
create table menu
(
   menuId               int not null auto_increment comment '菜单id<br />自增长主键id',
   parentId             int comment '父id',
   menuName             varchar(50) comment '菜单名称',
   showOrder            int comment '显示顺序',
   url                  varchar(200) comment 'URL',
   isVisible            tinyint default 0 comment '菜单是否出现<br />0：不出现 为内部url 1：出现 默认0',
   primary key (menuId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 2 角色表
```sql
create table role
(
   roleId               int not null auto_increment comment '角色id<br />自增长主键id',
   roleName             varchar(50) comment '角色名称',
   remark               varchar(1000) comment '备注',
   primary key (roleId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 3 角色菜单关系表
```sql
create table role_menu_purview
(
   roleId               int not null comment '角色id',
   menuId               int not null comment '菜单id',
   primary key (roleId, menuId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### 4 系统用户表
```sql
create table sys_user
(
   userId               int not null auto_increment comment '主键id<br />自增长主键id',
   email                varchar(64) comment '成员邮箱<br />要求唯一，登录时用',
   password             varchar(50) comment '密码',
   realName             varchar(50) comment '成员姓名',
   roleId               int comment '成员角色',
   createTime           datetime comment '创建时间',
   updateTime           datetime comment '更新时间',
   status               smallint comment '状态',
   primary key (userId),
   unique key AK_UQ_Email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## 三 初始化数据

### 1 角色表
```sql
INSERT INTO role(roleId, roleName, remark) VALUES (1, '超级系统管理员', '超级系统管理员，负责系统基础数据管理');
```

### 2 系统用户表
```sql
INSERT INTO sys_user(userId, email, password, realName, roleId, createTime, updateTime, status) VALUES (1, 'admin@netro.com', '40f7a60a6ae705c96b7398c986270967', '刘小洋', 1, now(), now(), 1);
```

### 3 菜单表
```sql
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (1, 0, '系统管理', 0, '#', 1);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (2, 1, '角色管理', 0, '/sysadmin/role/list.do', 1);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (3, 2, '新增角色请求', 0, '/sysadmin/role/insert.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (4, 2, '修改角色请求', 1, '/sysadmin/role/update.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (5, 2, '删除角色请求', 2, '/sysadmin/role/delete.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (6, 2, '根据角色id获取角色对象请求', 3, '/sysadmin/role/get.do', 1);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (7, 1, '菜单管理', 1, '/sysadmin/menu/listAll.do', 1);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (8, 7, '异步请求用户可见菜单列表', 0, '/sysadmin/menu/listVisible.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (9, 7, '异步请求所有菜单列表', 1, '/sysadmin/menu/doListAll.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (10, 7, '新增菜单请求', 2, '/sysadmin/menu/insert.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (11, 7, '修改菜单请求', 3, '/sysadmin/menu/update.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (12, 7, '删除菜单请求', 4, '/sysadmin/menu/delete.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (13, 7, '异步请求根据角色id获取菜单权限列表', 5, '/sysadmin/menu/listPurview.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (14, 1, '角色授权管理', 2, '/sysadmin/role/listRole4Purview.do', 1);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (15, 14, '授权角色菜单权限请求', 0, '/sysadmin/role/auth.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (16, 0, '成员管理', 1, '#', 1);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (17, 16, '成员列表', 0, '/sysadmin/user/list.do', 1);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (18, 17, '进入用户新增页面请求', 0, '/sysadmin/user/insert.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (19, 17, '新增用户请求', 1, '/sysadmin/user/doInsert.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (20, 17, '进入用户修改页面请求', 2, '/sysadmin/user/update.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (21, 17, '修改用户请求', 3, '/sysadmin/user/doUpdate.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (22, 17, '删除用户请求', 4, '/sysadmin/user/delete.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (23, 17, '修改用户密码请求', 5, '/sysadmin/user/updatePassword.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (24, 0, '进入用户主页请求', 2, '/sysadmin/user/index.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (25, 24, '进入用户修改自己的密码页面请求', 0, '/sysadmin/user/updateMyPwd.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (26, 24, '修改用户自己的密码请求', 1, '/sysadmin/user/doUpdateMyPwd.do', 0);
INSERT INTO menu(menuId, parentId, menuName, showOrder, url, isVisible) VALUES (27, 24, '用户登出请求', 2, '/sysadmin/user/logout.do', 0);
```

### 4 角色权限表
```sql
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 1);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 2);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 3);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 4);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 5);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 6);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 7);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 8);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 9);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 10);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 11);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 12);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 13);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 14);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 15);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 16);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 17);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 18);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 19);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 20);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 21);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 22);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 23);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 24);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 25);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 26);
INSERT INTO role_menu_purview(roleId, menuId) VALUES (1, 27);
```
