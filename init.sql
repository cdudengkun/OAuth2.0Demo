CREATE TABLE `oauth_access_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessToken` varchar(300) DEFAULT NULL,
	`expireTime` bigint(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
	`oauthCodeId` bigint(20)  DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_clientAppInfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
	`trustIp` varchar(16) DEFAULT NULL,
	`clientId` varchar(16) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

CREATE TABLE `oauth_oauthCode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `oauthCode` varchar(300) DEFAULT NULL,
	`expireTime` bigint(20) DEFAULT NULL,
	`userId` bigint(20) DEFAULT NULL,
	`redirectUrl` varchar(300) DEFAULT NULL,
	`clientId` varchar(300) DEFAULT NULL,
	`scope` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(300) DEFAULT NULL,
  `avator` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
#资源服务器保存的用户信息
INSERT INTO `user_info` VALUES ('1', 'cjack', 'http://www.xxx.com');
#信任的客户端信息，类似与企鹅的接入流程需要自己先去注册一个应用，企鹅审核通过之后才会允许你去认证或者获取用户信息
INSERT INTO `oauth_clientAppInfo` VALUES ('1', 'test', '192.168.0.103', '12345ABCDE', '2019-03-31 20:07:39');