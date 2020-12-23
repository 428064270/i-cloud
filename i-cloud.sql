/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.12 : Database - i_cloud
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`i_cloud` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

/*Table structure for table `t_view_rbac_resource` */

DROP TABLE IF EXISTS `t_view_rbac_resource`;

CREATE TABLE `t_view_rbac_resource` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '编号、主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `type` int(11) NOT NULL COMMENT '类型[0:菜单,1:接口]',
  `path` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '路径',
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '0' COMMENT '父编号',
  `display` int(11) NOT NULL DEFAULT '0' COMMENT '是否显示[0:显示,1隐藏]',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图标',
  `component_path` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '组件路径',
  `option_values_json` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '父层级',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_view_rbac_resource` */

insert  into `t_view_rbac_resource`(`id`,`name`,`type`,`path`,`parent_id`,`display`,`order`,`icon`,`component_path`,`option_values_json`) values 
('1','权限管理',0,'/system','0',0,1,'lock',NULL,'[\"0\"]'),
('2','角色管理',0,'/role','1',0,1,'peoples','@/views/system/role','[\"0\",\"1\"]'),
('3','资源管理',0,'/resource','1',0,2,'table','@/views/system/resource','[\"0\",\"1\"]'),
('fb3e4de3-3915-46bd-8134-e229b4f3476d','角色删除',1,'/api/i-cloud-rbac/role/delete','2',0,0,'','','[\"0\",\"1\",\"2\"]'),
('89c76092-e168-44d3-a6fb-917751159640','用户管理',0,'/user','1',0,3,'user','@/views/system/user','[\"0\",\"1\"]'),
('0a6f2e5f-3120-4576-b238-15edca5bc854','用户管理',0,'/user','e96f8f6f-75d7-4c5b-b42c-c1e649bd00a9',0,1,'user','@/view/system/user','[\"0\",\"e96f8f6f-75d7-4c5b-b42c-c1e649bd00a9\"]'),
('6dd7c59c-5425-4943-8c52-4de6c89d0eee','角色新增',1,'/api/i-cloud-rbac/role/insert','2',0,0,'','','[\"0\",\"1\",\"2\"]'),
('9df8b0ea-781e-4c46-86ae-7ba0e812fcf8','角色列表',1,'/api/i-cloud-rbac/role/list','2',0,0,'','','[\"0\",\"1\",\"2\"]'),
('8342cbe2-afa2-49ee-a23c-017058bc0478','角色编辑',1,'/api/i-cloud-rbac/role/update','2',0,0,'','','[\"0\",\"1\",\"2\"]'),
('0868f473-0d8f-4305-bc35-85016eeae5f7','资源新增',1,'/api/i-cloud-rbac/resource/insert','3',0,0,'','','[\"0\",\"1\",\"3\"]'),
('0db252f6-0a0c-4a3d-bbbb-8e1fa235bd68','资源编辑',1,'/api/i-cloud-rbac/resource/update','3',0,0,'','','[\"0\",\"1\",\"3\"]'),
('b33fa3ef-a889-4d61-8776-9019c3b760ec','资源列表',1,'/api/i-cloud-rbac/resource/list','3',0,0,'','','[\"0\",\"1\",\"3\"]'),
('1db94798-b7d5-49ca-808d-8aa89e02de58','资源删除',1,'/api/i-cloud-rbac/resource/delete','3',0,0,'','','[\"0\",\"1\",\"3\"]'),
('0b1d733f-3665-45d8-b5ce-628c6c73acae','用户新增',1,'/api/i-cloud-rbac/user/insert','89c76092-e168-44d3-a6fb-917751159640',0,0,'','','[\"0\",\"1\",\"89c76092-e168-44d3-a6fb-917751159640\"]'),
('ffbce1f5-49ab-449c-ac9f-341962b16625','用户编辑',1,'/api/i-cloud-rbac/user/update','89c76092-e168-44d3-a6fb-917751159640',0,0,'','','[\"0\",\"1\",\"89c76092-e168-44d3-a6fb-917751159640\"]'),
('cf2d5ac9-bf4b-470d-b6db-4aa29183b1f4','用户删除',1,'/api/i-cloud-rbac/user/delete','89c76092-e168-44d3-a6fb-917751159640',0,0,'','','[\"0\",\"1\",\"89c76092-e168-44d3-a6fb-917751159640\"]'),
('49ea974d-6ec9-47e9-8f42-72202d6824f6','用户列表',1,'/api/i-cloud-rbac/user/list','89c76092-e168-44d3-a6fb-917751159640',0,0,'','','[\"0\",\"1\",\"89c76092-e168-44d3-a6fb-917751159640\"]');

/*Table structure for table `t_view_rbac_role` */

DROP TABLE IF EXISTS `t_view_rbac_role`;

CREATE TABLE `t_view_rbac_role` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色名称',
  `option_values_json` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '存在权限',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_view_rbac_role` */

insert  into `t_view_rbac_role`(`id`,`name`,`option_values_json`) values 
('b15e2535-4905-4cf6-81d8-22c5ff640840','系统管理员','[[\"1\",\"2\",\"75bc48c1-43f3-444b-8502-5b20cd660cce\"],[\"1\",\"3\"],[\"e96f8f6f-75d7-4c5b-b42c-c1e649bd00a9\",\"0a6f2e5f-3120-4576-b238-15edca5bc854\"]]'),
('acdbd8b2-d8ea-4407-ab27-dc568c03d093','测试角色','[[\"1\"],[\"1\",\"2\"],[\"1\",\"3\"],[\"1\",\"89c76092-e168-44d3-a6fb-917751159640\"]]'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','安全管理员','[[\"1\"],[\"1\",\"2\"],[\"1\",\"2\",\"fb3e4de3-3915-46bd-8134-e229b4f3476d\"],[\"1\",\"2\",\"6dd7c59c-5425-4943-8c52-4de6c89d0eee\"],[\"1\",\"2\",\"9df8b0ea-781e-4c46-86ae-7ba0e812fcf8\"],[\"1\",\"2\",\"8342cbe2-afa2-49ee-a23c-017058bc0478\"],[\"1\",\"3\"],[\"1\",\"3\",\"0868f473-0d8f-4305-bc35-85016eeae5f7\"],[\"1\",\"3\",\"0db252f6-0a0c-4a3d-bbbb-8e1fa235bd68\"],[\"1\",\"3\",\"b33fa3ef-a889-4d61-8776-9019c3b760ec\"],[\"1\",\"3\",\"1db94798-b7d5-49ca-808d-8aa89e02de58\"],[\"1\",\"89c76092-e168-44d3-a6fb-917751159640\"],[\"1\",\"89c76092-e168-44d3-a6fb-917751159640\",\"0b1d733f-3665-45d8-b5ce-628c6c73acae\"],[\"1\",\"89c76092-e168-44d3-a6fb-917751159640\",\"ffbce1f5-49ab-449c-ac9f-341962b16625\"],[\"1\",\"89c76092-e168-44d3-a6fb-917751159640\",\"cf2d5ac9-bf4b-470d-b6db-4aa29183b1f4\"],[\"1\",\"89c76092-e168-44d3-a6fb-917751159640\",\"49ea974d-6ec9-47e9-8f42-72202d6824f6\"]]');

/*Table structure for table `t_view_rbac_role_resource` */

DROP TABLE IF EXISTS `t_view_rbac_role_resource`;

CREATE TABLE `t_view_rbac_role_resource` (
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色编号',
  `resource_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '资源编号'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_view_rbac_role_resource` */

insert  into `t_view_rbac_role_resource`(`role_id`,`resource_id`) values 
('b15e2535-4905-4cf6-81d8-22c5ff640840','6dd7c59c-5425-4943-8c52-4de6c89d0eee'),
('b15e2535-4905-4cf6-81d8-22c5ff640840','9df8b0ea-781e-4c46-86ae-7ba0e812fcf8'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','49ea974d-6ec9-47e9-8f42-72202d6824f6'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','ffbce1f5-49ab-449c-ac9f-341962b16625'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','cf2d5ac9-bf4b-470d-b6db-4aa29183b1f4'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','0b1d733f-3665-45d8-b5ce-628c6c73acae'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','89c76092-e168-44d3-a6fb-917751159640'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','1db94798-b7d5-49ca-808d-8aa89e02de58'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','b33fa3ef-a889-4d61-8776-9019c3b760ec'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','0db252f6-0a0c-4a3d-bbbb-8e1fa235bd68'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','0868f473-0d8f-4305-bc35-85016eeae5f7'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','3'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','8342cbe2-afa2-49ee-a23c-017058bc0478'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','9df8b0ea-781e-4c46-86ae-7ba0e812fcf8'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','6dd7c59c-5425-4943-8c52-4de6c89d0eee'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','2'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','fb3e4de3-3915-46bd-8134-e229b4f3476d'),
('d5acbb15-76de-4766-875a-5eb36a3e0782','1');

/*Table structure for table `t_view_rbac_user` */

DROP TABLE IF EXISTS `t_view_rbac_user`;

CREATE TABLE `t_view_rbac_user` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '登录名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名',
  `option_values_json` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_view_rbac_user` */

insert  into `t_view_rbac_user`(`id`,`username`,`password`,`name`,`option_values_json`) values 
('1','zhaotianyu','$2a$10$xXFoauday7avGjH13XYk4uUY35F2dqSSiiKEnbD53O9x/l3dtOozS','赵田雨','[\"d5acbb15-76de-4766-875a-5eb36a3e0782\"]');

/*Table structure for table `t_view_rbac_user_role` */

DROP TABLE IF EXISTS `t_view_rbac_user_role`;

CREATE TABLE `t_view_rbac_user_role` (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户编号',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '角色编号'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `t_view_rbac_user_role` */

insert  into `t_view_rbac_user_role`(`user_id`,`role_id`) values 
('1','d5acbb15-76de-4766-875a-5eb36a3e0782');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
