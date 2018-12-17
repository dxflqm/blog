CREATE DATABASE IF NOT EXISTS blog_db
  DEFAULT CHARSET utf8
  COLLATE utf8_general_ci;

USE blog_db;


#文章表
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article` (
  `article_id` varchar(32) NOT NULL COMMENT '文章ID',
  `article_title` varchar(64) NOT NULL COMMENT '文章标题',
  `article_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '类别，0:原创；1:转载',
  `reprint_href` text COMMENT '转载链接',
  `article_introduce` text NOT NULL COMMENT '文章介绍',
  `article_content` text COMMENT '文章内容',
  `category_id` varchar(32) NOT NULL COMMENT '类别ID',
  `tag_id` varchar(32) NOT NULL COMMENT '标签ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `delete_flag` tinyint(3) NOT NULL DEFAULT '0' COMMENT '有效表示符，0:有效，1:无效',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态，0:草稿，1:发布，2:关闭',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';

#文章访问表
DROP TABLE IF EXISTS `tb_article_visit`;
CREATE TABLE `tb_article_visit` (
  `article_id` varchar(32) NOT NULL COMMENT '文章ID',
  `visit_count` bigint(8) NOT NULL DEFAULT '0' COMMENT '文章访问量',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章访问量表';


#类别表
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `category_id` varchar(32) NOT NULL COMMENT '类别ID',
  `category_name` varchar(32) NOT NULL COMMENT '类别名称',
  `category_count` int(4) NOT NULL DEFAULT '0' COMMENT '类别数量',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `sort` int(4) NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `delete_flag` tinyint(3) NOT NULL COMMENT '有效表示符，0:有效，1:无效',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='类别表';


#评论表
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `article_id` varchar(32) NOT NULL COMMENT '文章ID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `comment` text NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `delete_flag` tinyint(3) NOT NULL DEFAULT '0' COMMENT '有效表示符，0:有效，1:无效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

#日志
DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (
  `log_id` varchar(32) NOT NULL COMMENT '日志ID',
  `visit_ip` varchar(32) DEFAULT NULL COMMENT '访问IP',
  `ip_address` varchar(32) DEFAULT NULL COMMENT '访问IP所在地址',
  `path` varchar(64) DEFAULT NULL COMMENT '访问路径',
  `user_agent` text DEFAULT NULL COMMENT '用户代理',
  `device` varchar(32) DEFAULT NULL COMMENT '设备信息',
  `browser` varchar(32) DEFAULT NULL COMMENT '浏览器信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

#标签
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag` (
  `tag_id` varchar(32) NOT NULL COMMENT '标签ID',
  `tag_name` varchar(32) NOT NULL COMMENT '标签名称',
  `sort` int(4) NOT NULL COMMENT '排序',
  `remark` varchar(32) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `delete_flag` tinyint(3) NOT NULL DEFAULT '0' COMMENT '有效表示符，0:有效，1:无效',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

#用户
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `user_uuid` varchar(32) NOT NULL COMMENT '用户uuid，外部ID',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
  `user_logo` varchar(32) DEFAULT NULL COMMENT '用户logo',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `delete_flag` tinyint(3) NOT NULL DEFAULT '0' COMMENT '有效表示符，0:有效，1:无效',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';