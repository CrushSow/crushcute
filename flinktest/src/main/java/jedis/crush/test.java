package jedis.crush;

import redis.clients.jedis.Jedis;


import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

/**
 * PACKAGE_NAMW   jedis.crush
 * DATE      10
 * Author     Crush
 */
public class test {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("hadoop", 6379);
        System.out.println(jedis.ping());
        Set<String> keys = jedis.keys("*");
        for (Iterator iterator = keys.iterator(); ((Iterator) iterator).hasNext(); ) {
            Object next = iterator.next();
            System.out.println((String) next);
        }
        System.out.println(jedis.exists("k2"));
        System.out.println(jedis.ttl("k1"));
       // new InputStreamReader()
    }
}
