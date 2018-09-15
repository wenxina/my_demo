package cn.hl.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author wang
 * 测试redis数据库
 */
public class TestRedis {
    private Jedis jedis;

    @Before
    public void before() {
        //连接建立
        jedis = new Jedis("127.0.0.1");
        //redis密码 备注:若为Windows则需要修改redis.windows-service.conf此文件redis.windows.conf不会生效
        //因为windows自启动默认加载redis.windows-service.conf
        //在文件# requirepass foobared下添加 requirepass 你的密码即可
        jedis.auth("123456");
        System.out.println("Redis 连接成功!");
    }

    /**
     * jedis操作数据
     */
    @Test
    public void set() {
        jedis.del("test");//先删除数据，再进行测试

        jedis.set("test", "hello");
        System.out.println("取出数据------》》" + jedis.get("test"));

        jedis.append("test", " world");
        System.out.println("拼接数据------》》" + jedis.get("test"));

        jedis.del("test");
        System.out.println("删除数据------》》" + jedis.get("test"));

        jedis.mset("name", "狗蛋", "age", "12", "email", "1571339199@qq.com");
        System.out.println("取出数据------》》" + jedis.get("name") + " " + jedis.get("age") + " " + jedis.get("email"));
        jedis.del("name","age","email");
        System.out.println("取出数据------》》" + jedis.get("name") + " " + jedis.get("age") + " " + jedis.get("email"));
    }

    /**
     * jedis操作map
     */
    @Test
    public void map() {
        jedis.del("user");
        Map<String, String> map = new HashMap<>();
        map.put("name", "狗蛋");
        map.put("age", "12");
        map.put("email", "1571339199@qq.com");
        jedis.hmset("user", map);
        List<String> list = jedis.hmget("user", "name", "age", "email");
        System.out.println(list);

        //删除map中的某个键值
        jedis.hdel("user", "age");
        System.out.println("age:" + jedis.hmget("user", "age")); //因为删除了，所以返回的是null
        System.out.println("user的键中存放的值的个数:" + jedis.hlen("user")); //返回key为user的键中存放的值的个数2
        System.out.println("是否存在key为user的记录:" + jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println("user对象中的所有key:" + jedis.hkeys("user"));//返回user对象中的所有key
        System.out.println("user对象中的所有value:" + jedis.hvals("user"));//返回map对象中的所有value

        //拿到key，再通过迭代器得到值
        Iterator<String> iterator = jedis.hkeys("user").iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }
        jedis.del("user");
        System.out.println("删除后是否存在key为user的记录:" + jedis.exists("user"));//是否存在key为user的记录
    }

    /**
     * jedis操作List
     */
    @Test
    public void List(){
        //清空javaFramwork所所有内容
        jedis.del("javaFramwork");
        //存放数据
        jedis.lpush("javaFramework","spring");
        jedis.lpush("javaFramework","springMVC");
        jedis.lpush("javaFramework","mybatis");
        //取出所有数据,jedis.lrange是按范围取出
        //第一个是key，第二个是起始位置，第三个是结束位置
        System.out.println("长度:"+jedis.llen("javaFramework"));
        //jedis.llen获取长度，-1表示取得所有
        System.out.println("javaFramework:"+jedis.lrange("javaFramework",0,-1));

        jedis.del("javaFramework");
        System.out.println("删除后长度:"+jedis.llen("javaFramework"));
        System.out.println(jedis.lrange("javaFramework",0,-1));
        jedis.del("javaFramwork");//测试完删除数据
    }

    /**
     * jedis操作Set
     */
    @Test
    public void Set(){
        jedis.del("user");//先删除数据，再进行测试
        //添加
        jedis.sadd("user","狗");
        jedis.sadd("user","蛋");
        jedis.sadd("user","梦");
        jedis.sadd("user","秋");
        jedis.sadd("user","家");
        jedis.sadd("user","明");
        //移除user集合中的元素are
        jedis.srem("user","明");
        System.out.println("user中的value:"+jedis.smembers("user"));//获取所有加入user的value
        System.out.println("明是否是user中的元素:"+jedis.sismember("user","明"));//判断chx是否是user集合中的元素
        System.out.println("秋是否是user中的元素:"+jedis.sismember("user","秋"));
        System.out.println("集合中的一个随机元素:"+jedis.srandmember("user"));//返回集合中的一个随机元素
        System.out.println("user中元素的个数:"+jedis.scard("user"));
        jedis.del("user");//测试完删除数据
    }

    /**
     * 排序
     */
    @Test
    public void test(){
        jedis.del("number");//先删除数据，再进行测试
        jedis.rpush("number","4");//将一个或多个值插入到列表的尾部(最右边)
        jedis.rpush("number","5");
        jedis.rpush("number","3");

        jedis.lpush("number","9");//将一个或多个值插入到列表头部
        jedis.lpush("number","1");
        jedis.lpush("number","2");
        System.out.println(jedis.lrange("number",0,jedis.llen("number")));
        System.out.println("排序:"+jedis.sort("number"));
        System.out.println(jedis.lrange("number",0,-1));//不改变原来的排序
        jedis.del("number");//测试完删除数据
    }
}
