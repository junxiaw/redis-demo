# redis-demo
#### 启动本机redis
```$xslt
junxiawangdeMacBook-Pro:~ junxiawang$ cd /usr/local/redis
junxiawangdeMacBook-Pro:redis junxiawang$ ./bin/redis-server ./etc/redis.conf 
```
#### 右击@Test的方法直接run即可
#### 登录redis 验证存储信息
```$xslt
junxiawangdeMacBook-Pro:redis junxiawang$ ./bin/redis-cli
```
#### 查看所有key
```
127.0.0.1:6379> keys *
1) "info_map"
2) "name"
```
#### 获取key为name的值
```
127.0.0.1:6379> get name
"junxia"
```
#### 获取存入的map信息
```$xslt
127.0.0.1:6379> hmget info_map 10001
1) "\xac\xed\x00\x05t\x00\bBANTEN23"
```
```$xslt
127.0.0.1:6379> hmget info_map 10001 10002
1) "\xac\xed\x00\x05t\x00\bBANTEN23"
2) "\xac\xed\x00\x05t\x00\tTANGERANG"
```