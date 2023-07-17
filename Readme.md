# 海量数据短链
## 环境配置

> 4c8g，CentOS_7，虚拟机

```shell
vim /etc/sysconfig/network-scripts/ifcfg-ens32
```

```shell
TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
BOOTPROTO="static"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens32"
UUID="22d6a4b9-11a3-45b7-8373-f0a43eec3ffc"
DEVICE="ens32"
ONBOOT="yes"
IPADDR="192.168.80.136"
PREFIX="24"
NETMASK="255.255.255.0"
GATEWAY="192.168.83.2"
DNS1="192.168.83.2"
```

### Docker

[https://docs.docker.com/engine/install/centos/](https://docs.docker.com/engine/install/centos/)

```shell
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine

sudo yum install -y yum-utils
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

sudo yum install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo systemctl start docker
sudo systemctl enable docker
```

### MySQL

```shell
docker run \
 -p 3306:3306 \
 -e MYSQL_ROOT_PASSWORD=shortLink \
 -v /home/data/mysql/data:/var/lib/mysql:rw \
 -v /etc/localtime:/etc/localtime:ro \
 --name mysql \
 --restart=always \
 -d mysql:8.0
```

配置连接数

```sql
show variables like '%max_connections%';
set GLOBAL max_connections=5000;
set GLOBAL mysqlx_max_connections=5000;
```

### Redis

```shell
docker run -d --name redis -p 6379:6379 -v /mydata/redis/data:/data redis:6.2.4 --requirepass shortLink
```

### Nacos

所需 sql

```sql
/******************************************/
/*https://github.com/alibaba/nacos/blob/master/distribution/conf/nacos-mysql.sql */
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info   */
/******************************************/


CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) DEFAULT NULL,
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) DEFAULT NULL,
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) DEFAULT NULL,
  `c_use` varchar(64) DEFAULT NULL,
  `effect` varchar(64) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `c_schema` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_aggr   */
/******************************************/
CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) NOT NULL COMMENT 'datum_id',
  `content` longtext NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) DEFAULT NULL,
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';


/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_beta   */
/******************************************/
CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_info_tag   */
/******************************************/
CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COMMENT 'source user',
  `src_ip` varchar(50) DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = config_tags_relation   */
/******************************************/
CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = group_capacity   */
/******************************************/
CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = his_config_info   */
/******************************************/
CREATE TABLE `his_config_info` (
  `id` bigint(64) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) NOT NULL,
  `group_id` varchar(128) NOT NULL,
  `app_name` varchar(128) DEFAULT NULL COMMENT 'app_name',
  `content` longtext NOT NULL,
  `md5` varchar(32) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text,
  `src_ip` varchar(50) DEFAULT NULL,
  `op_type` char(10) DEFAULT NULL,
  `tenant_id` varchar(128) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';


/******************************************/
/*   数据库全名 = nacos_config   */
/*   表名称 = tenant_capacity   */
/******************************************/
CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';


CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) default '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) default '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

CREATE TABLE `users` (
	`username` varchar(50) NOT NULL PRIMARY KEY,
	`password` varchar(500) NOT NULL,
	`enabled` boolean NOT NULL
);

CREATE TABLE `roles` (
	`username` varchar(50) NOT NULL,
	`role` varchar(50) NOT NULL,
	UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions` (
    `role` varchar(50) NOT NULL,
    `resource` varchar(255) NOT NULL,
    `action` varchar(8) NOT NULL,
    UNIQUE INDEX `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
);

INSERT INTO users (username, password, enabled) VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', TRUE);

INSERT INTO roles (username, role) VALUES ('nacos', 'ROLE_ADMIN');
```

开启认证

```shell
docker run -d \
-e NACOS_AUTH_ENABLE=true \
-e MODE=standalone \
-e JVM_XMS=128m \
-e JVM_XMX=128m \
-e JVM_XMN=128m \
-p 8848:8848 \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=172.31.179.44 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=shortLink \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e MYSQL_SERVICE_DB_PARAM='characterEncoding=utf8&connectTimeout=10000&socketTimeout=30000&autoReconnect=true&useSSL=false' \
--restart=always \
--privileged=true \
-v /home/data/nacos/logs:/home/nacos/logs \
--name nacos \
nacos/nacos-server:2.0.2
```

### RabbitMQ

```shell
docker run -d --name rabbitmq \
-e RABBITMQ_DEFAULT_USER=admin \
-e RABBITMQ_DEFAULT_PASS=shortLink \
-p 15672:15672 \
-p 5672:5672 \
--restart=always \
rabbitmq:3.8.15-management
```

⽹络安全组开放端⼝

- 4369 erlang 发现⼝
- 5672 client 端通信⼝
- 15672 管理界⾯ ui 端⼝
- 25672 server 间内部通信⼝

### 业务 sql

```shell
CREATE TABLE `account` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `account_no` bigint DEFAULT NULL,
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `pwd` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '盐，用于个人敏感信息处理',
  `mail` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `auth` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '认证级别，DEFAULT，REALNAME，ENTERPRISE，访问次数不一样',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_phone` (`phone`) USING BTREE,
  UNIQUE KEY `uk_account` (`account_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
```

```sql
CREATE TABLE `traffic` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `day_limit` int DEFAULT NULL COMMENT '每天限制多少条，短链',
  `day_used` int DEFAULT NULL COMMENT '当天用了多少条，短链',
  `total_limit` int DEFAULT NULL COMMENT '总次数，活码才用',
  `account_no` bigint DEFAULT NULL COMMENT '账号',
  `out_trade_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单号',
  `level` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '产品层级：FIRST青铜、SECOND黄金、THIRD钻石',
  `expired_date` date DEFAULT NULL COMMENT '过期日期',
  `plugin_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '插件类型',
  `product_id` bigint DEFAULT NULL COMMENT '商品主键',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trade_no` (`out_trade_no`,`account_no`) USING BTREE,
  KEY `idx_account_no` (`account_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
```

```sql
CREATE TABLE `traffic_task` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `account_no` bigint DEFAULT NULL,
  `traffic_id` bigint DEFAULT NULL,
  `use_times` int DEFAULT NULL,
  `lock_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '锁定状态锁定LOCK  完成FINISH-取消CANCEL',
  `message_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '唯一标识',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_msg_id` (`message_id`) USING BTREE,
  KEY `idx_release` (`account_no`,`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
```

```sql
CREATE TABLE `link_group` (
  `id` bigint unsigned NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组名',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;



CREATE TABLE `short_link` (
  `id` bigint unsigned NOT NULL,
  `group_id` bigint DEFAULT NULL COMMENT '组',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链标题',
  `original_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始url地址',
  `domain` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '短链域名',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '短链压缩码',
  `sign` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '长链的md5码，方便查找',
  `expired` datetime DEFAULT NULL COMMENT '过期时间，长久就是-1',
  `account_no` bigint DEFAULT NULL COMMENT '账号唯一编号',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del` int unsigned NOT NULL COMMENT '0是默认，1是删除',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态，lock是锁定不可用，active是可用',
  `link_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '链接产品层级：FIRST 免费青铜、SECOND黄金、THIRD钻石',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
```

## 业务模块

### 发送短信

- (http)池化 + (线程)异步
- 图像验证码 + 防刷

### 分库分表算法：哈希取模

> 库ID = 短链码hash值 % 库数ᰁ
> 表ID = 短链码hash值 / 库数 % 表数


### 分布式 ID

- 雪花算法，指定 `worker.id`
- shardingjdbc-Snowflake ⾥⾯解决时间回拨问题，如果发生时钟回拨，就 sleep 到上次生成 id 的时间之后再次生成

### 短链生成算法

MurMurHash 得到 10 进制数字，转成 62 进制（数字 + 大小写字母）

> 10 进制：1813342104
> 62 进制：1YIB7i

- 短链大多是 6~8 位，每位又有 62 个可能，即 6^62 种可能，568 亿个短链
- 再拼接库表位，能表示更多数据
- 头拼接库，尾拼接表，即 8^62，根本用不完

### 自定义分库分表算法

生成短链时携带库表基因，查询时根据基因路由

```java
public class CustomDBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        //获取短链码第一位，即库位
        String codePrefix = shardingValue.getValue().substring(0, 1);
        for (String targetName : availableTargetNames) {
            //数据源名称最后一位，ds
            String targetNameSuffix = targetName.substring(targetName.length() - 1);
            //返回对应数据源名称，ds0
            if (codePrefix.equals(targetNameSuffix)) {
                return targetName;
            }
        }

        //抛异常
        throw new BizException(BizCodeEnum.DB_ROUTE_NOT_FOUND);

    }
}
```

```java
public class CustomTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        //获取逻辑表
        String targetName = availableTargetNames.iterator().next();
        //短链码  A23Ad1
        String value = shardingValue.getValue();
        //获取短链码最后一位
        String codeSuffix =  value.substring(value.length()-1);
        //拼接Actual table
        return targetName+"_"+codeSuffix;
    }
}
```

工具类，短链生成时添加基因

```java
public class ShardingDBConfig {

    /**
     * 存储数据库位置编号
     */
    private static final List<String> DB_PREFIX_LIST = new ArrayList<>();


    //配置启用那些库的前缀
    static {
        DB_PREFIX_LIST.add("0");
        DB_PREFIX_LIST.add("1");
        DB_PREFIX_LIST.add("a");
    }


    /**
     * 获取随机的前缀
     * @return
     */
    public static String getRandomDBPrefix(){
        return DB_PREFIX_LIST.get(ThreadLocalRandom.current().nextInt(DB_PREFIX_LIST.size()));
    }

}

```

```java
public class ShardingTableConfig {

    /**
     * 存储数据表位置编号
     */
    private static final List<String> TABLE_SUFFIX_LIST = new ArrayList<>();


    //配置启用那些表的后缀
    static {
        TABLE_SUFFIX_LIST.add("0");
        TABLE_SUFFIX_LIST.add("a");
    }


    /**
     * 获取随机的后缀
     * @return
     */
    public static String getRandomTableSuffix(){
        return TABLE_SUFFIX_LIST.get(ThreadLocalRandom.current().nextInt(TABLE_SUFFIX_LIST.size()));
    }



}
```

### 数据库扩容

> 假如前期分三个库，⼀个库两个表，项⽬⽕爆，数据激增，进⾏扩容，增加了新的数据库表位，会导致旧的库表⽐新的库表数据多，且容易出现超载情况。如何解决
> 给数据源配置权重
>
> - 数据扩容避免数据迁移
> - 配置权重解决数据倾斜不均匀的问题
> - 避免一次建很多库和表，节省资源


### 多维度查询

- 普通用户(匿名用户，不用注册，就只是访问短链)通过短链码可以路由到对应库表
- 平台用户(商家)如何查询对应的所有的短链，商家的短链分布在不同的库表，如何查询？

> 数据冗余，写 2 份

![](https://cdn.nlark.com/yuque/0/2023/jpeg/21889008/1682657570489-ebadbf4d-33c4-4c0f-bce2-1f149c0adf02.jpeg)


### 双写事务如何保证

- seata：强一致性，AT 模式全局锁，性能不高，不适合高并发业务
- mq：生产者发送消息到 mq，消费者订阅消息，消费幂等处理，broker 做高可用，失败重试，多次失败告警


### 短链生成在生产者还是消费者

生产者：

- ⽤户 A ⽣成短链码 AABBCC，查询数据库不存在，发送 MQ，插⼊数据库成功
- ⽤户 B ⽣成短链码 AABBCC，查询数据库不存在，发送 MQ，插⼊数据库失败

> 由于是双写，写入数据库是在消费者，就需要保证生产者生成短链和消费者插入数据库是原子的，不同的进程加锁不太好加

消费者：

- ⽤户 A ⽣成短链码 AABBCC ，C 端先插⼊，B 端还没插⼊
- ⽤户 B 也⽣成短链码 AABBCC ，B 端先插⼊，C 端还没插⼊
- ⽤户 A ⽣成短链码 AABBCC ，B 端插⼊失败，数据不一致
- ⽤户 B ⽣成短链码 AABBCC ，C 端插⼊失败，数据不一致

> 保证 2 个消费者的插入是加锁的，加的是同一个锁，即可重入分布式锁


### 消费者如何加锁

由于是 2 个消费者，如果要保证短链不重复，就需要操作时加锁，多个消费者如何使用同一个把锁，使用分布式可重入锁，即同一个 key，通过条件判断能否重入

- C 端
  1. 加锁，key：短链码，value：账户；设置过期时间
  2. 业务操作
     1. 查询短链是否存在
     2. 如果存在标识 +1
     3. 保存到数据库
  3. 解锁，不做删除，通过过期时间来实现解锁
- B 端
  1. 加锁，key：短链码，value：账户；设置过期时间
  2. 业务操作
     1. 查询短链是否存在
     2. 如果存在标识 +1
     3. 保存到数据库
  3. 解锁，不做删除，通过过期时间来实现解锁

> 加锁：
>
> - 通过 code 判断是否有这个 key
>   - 没有，设置 value：account，设置过期时间
>   - 有，判断 value 是否和当前 account 一致
>     - 一致：加锁成功(重入)
>     - 不一致：加锁失败
> - 加锁需要原子性
> - 只有生成需要加锁，修改和删除不用加锁
>
> 解锁：
>
> - 设置过期时间
> - 不能直接删除，防止另外一端未处理完，又来一个重复的短链
> - 设置的过期时间需要保证双端的逻辑可以处理完，防止消息积压，比如 2~3 天


### 消费者生成如何保证双写短链码一致

消费者生成短链码，B 端和 C 端是不同的消费者，如何保证生成的短链码一致？
MurmurHash 对同个 url 产⽣后的值是⼀样的，但是随机拼接了库表位，最终⽣成的短链码就可能不⼀致的情况，如何解决？

- 改为固定库表位
- 对 url 的 `hashCode()` 进行取模得到对应的库表位

### 同一个 URL 如何生成多个短链码

商家广告投放，不同渠道需要生成不同的短链码，同一个 URL 如何生成多个短链码

- url 前面拼接时间戳或者唯一标识
- 用户访问短链的时候再移除前面的标识
- 如果冲突，就进行数字 +1

> 1469558440337604610&http://baidu.com



### 下单防重（幂等）

- 下单前获取唯一标识（token），下单时携带唯一标识。服务端删除 token 来判断是否重复订单
  - 删除成功：不重复
  - 删除失败：重复请求
- 根据 ip + method + account + userAgent  生成一个 key，设置过期时间，请求时判断 key 是否存在
  - 存在：重复请求
  - 不存在：非重复请求


### 下单流程

![](https://cdn.nlark.com/yuque/0/2023/jpeg/21889008/1683286077475-d8d8bfe8-641b-49e1-a989-755d0711da4b.jpeg)

1. 用户下单
2. 订单服务
   1. 生成订单
   2. 保存数据库
   3. 发送延迟消息（关闭订单）
   4. 创建三方支付信息
3. 返回客户端支付信息
4. 客户端支付成功
   1. 三方回调后台接口，修改订单状态及后续操作
   2. 未收到三方回调（网络波动等），收到 MQ 延迟消息，查询订单信息
      1. 已支付，不做处理
      2. 未支付，查询三方支付结果
         1. 已支付，修改订单状态及后续操作
         2. 未支付，取消订单

### 二维码哪里生成

- 服务器生成
  - 图片生成占用大量服务器资源，占用服务器带宽
  - 返回的是二进制流，无法一并返回其他参数
- 客户端生成
  - 降低服务器压力
  - 可携带其他参数，比如订单号
  - 需要考虑浏览器兼容问题


### 超时关闭订单

使用 RabbitMQ 的延迟队列 + 死信队列

- 延迟队列：声明队列时额外添加参数

```java
        Map<String,Object> args = new HashMap<>(3);
    	// 死信交换机
        args.put("x-dead-letter-exchange",orderEventExchange);
    	// 死信队列
        args.put("x-dead-letter-routing-key",orderCloseRoutingKey);
    	// 消息存活时间
        args.put("x-message-ttl",ttl);
        return new Queue(orderCloseDelayQueue,true,false,false,args);
```

- 死信队列：和声明普通队列一样。死信队列是相当于其他队列而言的，其他队列的消息被拒绝或者过期后重新投递到另外一个队列，这个队列即死信队列


### 支付回调如何处理

- 幂等处理，微信可能多次回调，使用回调过来的订单号作为 key，设置 3 天的过期时间，处理回调前先判断能不能将 key 设置到缓存中 `sexnx`
- 业务处理
  - 更新订单状态
  - 调用账户服务，发放流量包
- 如何调用账户服务
  - rpc 远程调用，同步调用，处理完所有的业务逻辑才返回给微信返回收到回调，处理过慢，微信可能多次通知
    - 分布式事务如何保证？
- 使用 mq 双写
  - 收到回调生成 mq 消息，投递到交换机就给微信返回返回收到回调
  - 订单 mq 收到消息，更新订单状态
  - 账户 mq 收到消息，发放流量包
  - 分布式事务如何保证？
    - 靠 mq 的 ack 机制，失败重试，失败次数达上限，路由到异常处理队列


### 微信支付 QPS 有限制，如何解决

[https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pages/ico-guide/chapter1_5.shtml](https://pay.weixin.qq.com/wiki/doc/apiv3/wxpay/pages/ico-guide/chapter1_5.shtml)

> 统一下单 qps，单个商户限制 600

- 配置多个商户信息，使用负载均衡
- 使用账户取模，下单、查询、退款，路由到同一个商户上
- 做好频率和监控


### 免费流量包如何发放

> 新用户注册，会有一定数量的免费流量包，如何进行发放

- 直接远程调用商品服务，查询流量包详情，数据库新增流量包记录
  - 同步调用，性能不高
  - 扩展不强，增加业务时需要修改原编码
- 注册成功发送 mq 消息
  - 分布式事务如何保证？
    - 账户注册失败，mq 消费成功，没关系，只是多了一个流量包记录(无对应账户)，只是多了一条无用的记录，不影响业务
    - 账户注册成功，mq 消费失败，靠 mq 的 ack 来保证，失败重试路由等
  - 接口性能高，易扩展，监听者模式，解耦


### 流量包发放幂等

> 流量包的发放是靠 mq 的消费者来处理，可能出现消息多次投递，如何保证流量包不发放多次？

消息投递

- 使用 redis 进行了限制，当时不能保证一定不重复投递

消息消费

- 使用数据库的唯一索引兜底
- 数据库是根据账户来进行分表，即同一个账户的流量包会落在同一张表，只需保证分表(物理表)的唯一索引，而不是整个逻辑表的唯一索引

```sql
UNIQUE KEY `uk_trade_no`
(`out_trade_no`,`account_no`) USING BTREE;
```

- 使用订单号和账户做唯一索引
  - 付费流量包有订单号，每个订单不同
  - 免费流量包无订单号，索引尽量不要给 null 值，使用一个固定的值来填充订单号

### 过期流量包如何处理

分布式定时任务删除过期流量包，直接将过期时间


### 流量包每天使用次数如何维护

> 付费流量包：每天一定短链创建次数
> 免费流量包：每天少量短链创建次数

如何维护更新每天的流量包次数？有几千万的用户

- 直接使用 update sql 更新，数据量大时，直接卡着，后续的 sql 无法执行
- 几千万的用户都是活跃用户？全部更新，不活跃的也更新，浪费服务器资源

借鉴 redis 的 key 过期策略

> 定期 + 惰性删除。
>
> - 随机抽取一批带有 ttl 的 key 查看是否过期
> - 下次再次访问时再判断 key 是否过期

扣减流量包时更新维护流量包每天的额度

### 流量包如何扣减

- 查询用户所有可用流量包
- 遍历所有可用流量包，通过修改日期判断当天有没有更新过
  - 没更新过
    - 批量更新待重置流量包数据
  - 更新过
    - 当天额度是否用完
- 扣减流量包
- 设置当天剩余额度到缓存，供短链服务来预扣减，过期时间就是当天晚上12点

### 创建短链流程

![](https://cdn.nlark.com/yuque/0/2023/jpeg/21889008/1683720299163-178be831-1d7f-404f-a255-1d83c2a1cb34.jpeg)

- 流量包额度预扣减，是否有足够的流量包额度（账户服务维护的缓存，redis）
- 发送 mq 消息
- 监听消息，生成短链，加锁
  - 查询短链是否重复
  - 远程调用账户服务，扣减流量包（db）
  - 保存到数据库（db）
  - 短链重复，时间戳+1，自旋
  - 不手动处理解锁，ttl，自动解锁

> 缓存是账户服务维护，短链服务器使用同一个 key 来进行预扣减，远程调用账户服务器真正扣款时会更新缓存的值。
> 缓存没有值，说明当天的额度还没更新，用户有免费的额度，可用直接给前端返回创建短链成功；或者用户购买了新的流量包，又有了新的额度也是可用直接返回成功。


### 短链创建和流量包扣减事务如何保证

> 短链服务和流量包服务是 2 个不同的服务，如何保证事务

使用本地消息表，最终一致性
![](https://cdn.nlark.com/yuque/0/2023/jpeg/21889008/1683796553001-8b918cfb-b72f-4606-85d5-f813b8574fab.jpeg)

- 流量包服务扣减库存后，保存一个 task 任务，记录扣减的流量包（同一个事务）
- 发送延迟队列来检查任务
- 消费者监听到消息，rpc 远程调用，查询短链是否创建成功
  - 成功：删除 task 任务
  - 失败：回滚流量包，删除 task 任务
- 还需要有定时任务来兜底，防止 mq 投递/消费失败
- 保证最终一致性

